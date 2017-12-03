package com.bigdata.bookenizer.services;

import com.bigdata.bookenizer.model.Book;
import com.bigdata.bookenizer.model.User;
import com.bigdata.bookenizer.model.entity.UsersEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@Component
public class BookDao {


    ArrayList<Book> listBooks;



    public BookDao() {
        listBooks = new ArrayList<>();


    }
/*
    public Optional<> findByUsername(String username) {

        for ( User user: listBooks) {
            if(user.getUsername().equalsIgnoreCase(username))
                return Optional.of(user);
        }
        return Optional.empty();
    }

    public Optional<User> findByUsername(Iterable<UsersEntity> users, String username) {

        Iterator<UsersEntity> iter = users.iterator();
        UsersEntity user = null;
        while (iter.hasNext()){
            user = iter.next();
            if(user.getLogin().equalsIgnoreCase(username)){
                User ut = new User(user.getFirstname(),
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

        // return Optional.empty();
        return findByUsername(username); // просто чтобы встроенного юзера подгружать тоже, убрать потом
    }

    */
}
