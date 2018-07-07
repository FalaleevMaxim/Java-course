package ru.course.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.course.model.User;

import java.util.List;

@Repository
@Transactional
public class UsersStorage {
    private final SessionFactory sessionFactory;

    @Autowired
    public UsersStorage(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    public List<User> all(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User u").list();
    }

    public int add(String name){
        return (int)sessionFactory.getCurrentSession().save(new User(name));
    }

    public User get(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }
}
