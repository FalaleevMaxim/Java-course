package ru.course.storage.user;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.course.dto.user.UserFilterDto;
import ru.course.model.user.User;
import ru.course.model.user.UserDocument;
import ru.course.storage.AbstractHibernateStorage;

import java.util.List;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class HibernateUsersStorage extends AbstractHibernateStorage<User> implements UserStorage {

    @Autowired
    public HibernateUsersStorage(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    @Override
    public List<User> filter(UserFilterDto filter) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("office.id", filter.officeId));
        if (filter.firstName != null) criteria.add(Restrictions.eq("firstName", filter.firstName));
        if (filter.lastName != null) criteria.add(Restrictions.eq("secondName", filter.lastName));
        if (filter.middleName != null) criteria.add(Restrictions.eq("middleName", filter.middleName));
        if (filter.citizenshipCode != null) criteria.add(Restrictions.eq("citizenship.code", filter.citizenshipCode));
        if (filter.phone != null) criteria.add(Restrictions.eq("phone", filter.phone));
        if (filter.position != null) criteria.add(Restrictions.eq("position", filter.position));
        return criteria.list();
    }

    @Override
    public Integer userDocumentId(User user) {
        return (Integer) sessionFactory.getCurrentSession()
                .createQuery("select ud.id from UserDocument ud where ud.user=:user")
                .setEntity("user", user)
                .uniqueResult();
    }

    @Override
    public boolean contains(int id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select 1 from User u where u.id=:id")
                .setInteger("id", id);
        return query.uniqueResult() != null;
    }
}
