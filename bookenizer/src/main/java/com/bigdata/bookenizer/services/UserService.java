package com.bigdata.bookenizer.services;


import com.bigdata.bookenizer.model.User;
import com.bigdata.bookenizer.model.entity.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UsersEntityDao ued;



    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {

        // UsersEntity user = ued.findOne(1);

        Iterable<UsersEntity> users = ued.findAll();
        //return
        return userDao.findByUsername(users, username).orElseThrow(() -> new UsernameNotFoundException("user " + username + " was not found!"));


        // return userDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user " + username + " was not found!"));

    }


    public User getCurrentUser()
    {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    public Long getCurrentUserId()
    {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long id = user.getId();
        return id;
    }

}
