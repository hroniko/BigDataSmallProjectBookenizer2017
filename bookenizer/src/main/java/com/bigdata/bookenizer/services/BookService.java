package com.bigdata.bookenizer.services;

import com.bigdata.bookenizer.model.User;
import com.bigdata.bookenizer.model.entity.BooksEntity;
import com.bigdata.bookenizer.model.entity.UsersEntity;
import com.bigdata.bookenizer.utils.hibernate.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BooksEntityDao booksEntityDao;

    @Autowired
    private UsersEntityDao usersEntityDao;



    // Получение всех книг пользователя
    public void getAllBooksForUserId(Long userId){

        // Сначала берем нежного пользователя
        //UsersEntity user = usersEntityDao.findOne(userId);





        /*
        List list = new ArrayList<BooksEntity>();

        Session session = hsf.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("FROM UsersEntity u, UsertagsEntity ut, BooksEntity b WHERE u.id = :paramName AND u.id = ut.iduser AND ut.idbook = b.id");
        query.setParameter("paramName", userId);
        int result = query.executeUpdate();

        list = (List<BooksEntity>) query.list();
        session.getTransaction().commit();

        session.close();

        System.out.println(((BooksEntity)list.get(1)).getName());
        */

/*

        Iterable<BooksEntity> books = booksEntityDao.findAll();




        Iterator<BooksEntity> iter = books.iterator();



        UsersEntity user = null;
        while (iter.hasNext()){
            user = iter.next();
            if(user.getLogin().equalsIgnoreCase(username)){
                User ut = new User(user.getId(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getLogin(),
                        user.getPassword(),
                        user.getMail(),
                        user.getAge().toString(),
                        user.getIdcountry().toString()
                );
                return Optional.of(ut);
            }

        }
*/
    }

}
