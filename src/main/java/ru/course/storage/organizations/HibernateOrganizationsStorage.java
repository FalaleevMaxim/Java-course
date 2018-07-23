package ru.course.storage.organizations;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.course.model.organization.Organization;
import ru.course.storage.AbstractHibernateStorage;

import java.util.List;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class HibernateOrganizationsStorage extends AbstractHibernateStorage<Organization> implements OrganizationsStorage {
    public HibernateOrganizationsStorage(SessionFactory sessionFactory) {
        super(sessionFactory, Organization.class);
    }

    @Override
    public List<Organization> filter(String name, String inn, Boolean isActive) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Organization.class);
        criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        if (inn != null) criteria.add(Restrictions.eq("inn", inn));
        if (isActive != null) criteria.add(Restrictions.eq("active", isActive));
        return criteria.list();
    }

    @Override
    public boolean contains(int id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select 1 from Organization o where o.id=:id")
                .setInteger("id", id);
        return query.uniqueResult() != null;
    }
}
