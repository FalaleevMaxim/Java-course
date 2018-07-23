package ru.course.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractHibernateStorage<T> implements Storage<T> {
    protected final SessionFactory sessionFactory;
    private final Class<T> tClass;

    public AbstractHibernateStorage(SessionFactory sessionFactory, Class<T> aClass) {
        this.sessionFactory = sessionFactory;
        tClass = aClass;
    }

    @Override
    public T get(int id) {
        return sessionFactory.getCurrentSession().get(tClass, id);
    }

    @Override
    public void save(T entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public boolean remove(int id) {
        Session session = sessionFactory.getCurrentSession();
        T entity = session.byId(tClass).load(id);
        if (entity == null) return false;
        session.delete(entity);
        return true;
    }

    public void update(T entity) {
        sessionFactory.getCurrentSession().update(entity);
    }
}
