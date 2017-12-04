/**
 * @author pradekum
 *
 * 
 */
package com.bigdata.bookenizer.controller;

import com.bigdata.bookenizer.controller.ajax.AjaxBook;
import com.bigdata.bookenizer.model.User;
import com.bigdata.bookenizer.model.entity.*;
import com.bigdata.bookenizer.services.*;
import com.bigdata.bookenizer.utils.kafka.MessageStorage;
import com.bigdata.bookenizer.utils.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Controller
public class TestControler {
	@Autowired
	private UsersEntityDao usersEntityDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UsertagsEntityDao usertagsEntityDao;

	@Autowired
	private BooksEntityDao booksEntityDao;

	@Autowired
	private AuthorsEntityDao authorsEntityDao;

	@Autowired
	private RecomendationsEntityDao recomendationsEntityDao;

	@Autowired
	private Producer producer;

	@Autowired
	private MessageStorage storage;

	@RequestMapping("/login")
	public String getLogin(@RequestParam(value = "error", required = false) String error,
						   @RequestParam(value = "logout", required = false) String logout,  Model model) {
		model.addAttribute("error", error != null);
		return "login";
	}



	// Регистрация
	@RequestMapping(value = "/registration_sumbit", method = RequestMethod.POST)
	public ModelAndView showProfilePage(@RequestParam String login ,@RequestParam String firstName ,@RequestParam String lastName ,
										@RequestParam String email , @RequestParam Integer age ,
										@RequestParam String country, @RequestParam String password  ) {

		// Сначала надо проверить, вдруг уже есть такой юзер (с таким логином)
		// Но у нас уникальность логина вроде нстроена, потом реализую тогда
		// UserDetails ud = (new UserService()).loadUserByUsername(login);
		// if (ud.)

		// Создаем нового пользователя:
		UsersEntity user = null;
		try {
			user = new UsersEntity(login, password, firstName, lastName, email, age, 1L);
			System.out.println(user.getLogin() + ":" + user.getPassword());

			usersEntityDao.save(user);

			// И отправляем логиниться
			ModelAndView model = new ModelAndView("login");
			return model;
		}
		catch (Exception ex) {
			// Если ошибка, отправляем на страницу регистрации
			ModelAndView model = new ModelAndView("registration");
			return model;
		}



	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String showRegistrationPage() {
		return "registration";
	}



	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView welcome(Model model) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView modelAndView = new ModelAndView("user_page");
		modelAndView.addObject("user", user);
		// Подгружаем через хибернейт все книги пользователя
		modelAndView.addObject("books", getAllBooksForUserId(user.getId()));
		return modelAndView;
	}


	@RequestMapping(value = "/recomendations", method = RequestMethod.GET)
	public ModelAndView recomendation(Model model) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// System.out.println("Пользователь " + user.getUsername());

		ModelAndView modelAndView = new ModelAndView();
		// Подгружаем через хибернейт все рекомендованные книги
		// modelAndView.addObject("books", getAllRecomendationsForUserId(user.getId()));
		modelAndView.addObject("books", getAllRecomendationsForUserId(user.getId()));

		modelAndView.setViewName("recomendations");
		return modelAndView;
	}






	private List<UsersEntity> getUsers() {
		UsersEntity user = null;
		ArrayList<UsersEntity> list = new ArrayList<>();
		for (long i = 1; i <=5; i++){
			user = usersEntityDao.findOne(i);
			if (user != null){
				list.add(user);
			}

		}

		return list;
	}


	@RequestMapping("/user_id")
	public void getCurrentUser()
	{
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long id = user.getId();
		System.out.println(id);

		getAllBooksForUserId(id);
		getAllRecomendationsForUserId(id);


	}


	// Получение всех книг пользователя
	private ArrayList<BooksEntity> getAllBooksForUserId(Long userId){
		// userId = 17L;

		// Затем забираем вообще все ссылки на книги:
		Iterable<UsertagsEntity> usertags = usertagsEntityDao.findAll();


		// Обходим их и берем только ссылки этого пользователя
		ArrayList<Long> idsUserBooks = new ArrayList<>();
		Iterator<UsertagsEntity> iter = usertags.iterator();

		while (iter.hasNext()) {
			UsertagsEntity usertag = iter.next();
			// System.out.print(usertag.getIduser() + " : ");
			// System.out.println(usertag.getIdbook());


			if (usertag.getIduser().equals(userId)) {
				idsUserBooks.add(usertag.getIdbook());
				// System.out.println(usertag.getIdbook());
				// System.out.println(usertag.getIduser());
			}
		}

		// Получаем все книги данного пользователя:
		ArrayList<BooksEntity> books = new ArrayList<>();
		for (Long id : idsUserBooks) {
			BooksEntity book = booksEntityDao.findOne(id);
			if ((book != null) && (!books.contains(book))){
				AuthorsEntity author = authorsEntityDao.findOne(book.getIdauthor());
				book.setAuthor(author.getFio());
				books.add(book);
				// System.out.println(book.getName());
			}

		}

		return books;

	}



	// Получение всех рекмендаций пользователя
	private ArrayList<BooksEntity> getAllRecomendationsForUserId(Long userId){

		// Затем забираем вообще все ссылки на рекомендованные книги:
		Iterable<RecomendationsEntity> recomendations = recomendationsEntityDao.findAll();

		// Обходим их и берем только ссылки этого пользователя
		ArrayList<Long> idsUserBooks = new ArrayList<>();
		Iterator<RecomendationsEntity> iter = recomendations.iterator();

		while (iter.hasNext()) {
			RecomendationsEntity recomendation = iter.next();


			if (recomendation.getIduser().equals(userId)) {
				idsUserBooks.add(recomendation.getIdbook());
				// System.out.println(recomendation.getIdbook());
				// System.out.println(recomendation.getIduser());
			}
		}

		// Получаем все книги данного пользователя и добавляем авторов:
		ArrayList<BooksEntity> books = new ArrayList<>();
		for (Long id : idsUserBooks) {
			BooksEntity book = booksEntityDao.findOne(id);
			if ((book != null) && (!books.contains(book))){
				AuthorsEntity author = authorsEntityDao.findOne(book.getIdauthor());
				book.setAuthor(author.getFio());
				books.add(book);
				// System.out.println(book.getName());
			}

		}

		return books;

	}


	// Добавление ноой книги пользователем у себя на страницу профиля
	@ResponseBody
	@RequestMapping(value = "/sendBook", method = RequestMethod.POST)
	public void sendBook(HttpSession session,
						 @RequestParam(value = "id") Long id,
						 @RequestParam(value = "name") String name,
						 @RequestParam(value = "author") String author)
	{
		System.out.println("Пользователь " + id + " добавил книгу: " + name + " : " + author);

		// 1 Находим автора с таким же именем:

		Long idAu = getIdForAuthorName(author);



		if (idAu == null){
			// то не нашли автора, соответственно, и книг его нет в базе

			// и нужно создать его id
			idAu = getAuthorsNextId();

			// нужно создать автора:
			AuthorsEntity authorsEntity =  new AuthorsEntity(idAu, author);
			// Сохраняем в базу
			authorsEntityDao.save(authorsEntity);


		}

		// К этому моменту у нас должен быть известен id автора


		// Пробуем найти такую книгу: // Пока закомментировал, с базой проблемка была !!!! 2017-12-04
		Long idBook = null; // getIdForBookNameAndIdAuthor(name, idAu);

		// Если не нашли, надо создать:

		if (idBook == null){
			idBook = getBooksNextId();

			BooksEntity book = new BooksEntity(idBook, name.trim(), idAu, author.trim());
			// Сохраняем в базу
			booksEntityDao.save(book);



		}

		// и надо создать usertags на книгу

		// создаем ссылку
		UsertagsEntity linkToBook = new UsertagsEntity(id, idBook);

		// Сохраняем в базу
		usertagsEntityDao.save(linkToBook);





		// Когда отправили кнугу в базу, теперь надо закинуть в кафку айди этого юзера:
		producer.send("spring", "id", id.toString());
		producer.send("spring", "id", "1"); // Вот тут попытка, просто левые данные для проверки
		//producer.send("spring", "id", "2"); // Просто так, а то данных нет в бд
		//producer.send("spring", "id", "5");

	}


	// Получение макс id автора
	private Long getAuthorsNextId(){
		// Забираем всех авторов:
		Iterable<AuthorsEntity> authors = authorsEntityDao.findAll();


		// Обходим их и берем максимальный id

		Long idAu = 0L;
		Iterator<AuthorsEntity> iter = authors.iterator();

		while (iter.hasNext()) {
			AuthorsEntity au = iter.next();
			if (au.getId() > idAu) {
				// то забираем его айди
				idAu = au.getId();
			}
		}

		return idAu + 1;
	}

	// Получение макс id книги
	private Long getBooksNextId(){
		// Забираем все книги:
		Iterable<BooksEntity> books = booksEntityDao.findAll();


		// Обходим их и берем максимальный id

		Long idAu = 0L;
		Iterator<BooksEntity> iter = books.iterator();

		while (iter.hasNext()) {
			BooksEntity book = iter.next();
			if (book.getId() > idAu) {
				// то забираем его айди
				idAu = book.getId();
			}
		}

		return idAu + 1;
	}


	// Получение id автора по его имени
	private Long getIdForAuthorName(String author){
		// 1 Находим автора с таким же именем:

		// Забираем всех авторов:
		Iterable<AuthorsEntity> authors = authorsEntityDao.findAll();

		// Проверяем, есть ли такой автор
		// Обходим их и берем только ссылки этого пользователя
		author = author.trim();
		Long idAu = null;
		Iterator<AuthorsEntity> iter = authors.iterator();

		while (iter.hasNext()) {
			AuthorsEntity au = iter.next();
			if (au.getFio().contains(author)) {
				// то забираем его айди
				idAu = au.getId();
				break;
			}
		}

		return idAu;
	}

	// Получение id книги по ее имени и id автора
	private Long getIdForBookNameAndIdAuthor(String bookName, Long idAu){

		// Забираем все книги:
		Iterable<BooksEntity> books = booksEntityDao.findAll();

		// Проверяем, есть ли такая же книга
		// Обходим их и берем только ссылки совпадающей
		bookName = bookName.trim();
		Long idBook = null;
		Iterator<BooksEntity> iter = books.iterator();

		while (iter.hasNext()) {
			BooksEntity book = iter.next();
			if (book.getName().contains(bookName) & (book.getIdauthor() == idAu )) {
				// то забираем его айди
				idBook = book.getId();
				break;
			}
		}

		return idBook;
	}



}