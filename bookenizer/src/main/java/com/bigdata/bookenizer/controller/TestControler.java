/**
 * @author pradekum
 *
 * 
 */
package com.bigdata.bookenizer.controller;

import com.bigdata.bookenizer.model.User;
import com.bigdata.bookenizer.model.entity.*;
import com.bigdata.bookenizer.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

}