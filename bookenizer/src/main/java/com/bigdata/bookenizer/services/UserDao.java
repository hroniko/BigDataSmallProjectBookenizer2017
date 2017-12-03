package com.bigdata.bookenizer.services;

import com.bigdata.bookenizer.model.User;
import com.bigdata.bookenizer.model.entity.UsersEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;


@Component
public class UserDao {

    ArrayList<User> listUsers;



    public UserDao() {
       listUsers = new ArrayList<>();
        User user1 = new User(4L, "Alex" ,"Fam" ,"alex0","123456","mail","22","RF" );
       User user2 = new User(2L,"Eva" ,"Fam" ,"eva1995","newyear","mail","19","RF" );
        User user3 = new User(3L,"Anna" ,"Fam" ,"anet","","mail","19","RF" );
       listUsers.add(user1);
       listUsers.add(user2);
        listUsers.add(user3);





    }

    public Optional<User> findByUsername(String username) {

        for ( User user: listUsers) {
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

        // return Optional.empty();
        return findByUsername(username); // просто чтобы встроенного юзера подгружать тоже, убрать потом
    }


}
