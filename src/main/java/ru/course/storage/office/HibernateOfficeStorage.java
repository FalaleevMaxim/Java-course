package ru.course.storage.office;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.course.model.office.Office;
import ru.course.storage.AbstractHibernateStorage;

import java.util.List;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class HibernateOfficeStorage extends AbstractHibernateStorage<Office> implements OfficeStorage {

    public HibernateOfficeStorage(SessionFactory sessionFactory) {
        super(sessionFactory, Office.class);
    }

    @Override
    public List<Office> filter(int orgId, String name, String phone, Boolean isActive) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Office.class);
        criteria.add(Restrictions.eq("organization.id", orgId));
        if (name != null) criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        if (phone != null) criteria.add(Restrictions.eq("phone", phone));
        if (isActive != null) criteria.add(Restrictions.eq("active", isActive));
        return criteria.list();
    }

    @Override
    public boolean contains(int id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select 1 from Office o where o.id=:id")
                .setInteger("id", id);
        return query.uniqueResult() != null;
    }
}
