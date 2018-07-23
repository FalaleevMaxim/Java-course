package ru.course.storage.dictionaries;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.course.model.dictionary.Country;
import ru.course.model.dictionary.Document;

import java.util.List;

@Repository
@Transactional
public class HibernareDictionary implements DictionariesStorage {
    private final SessionFactory sessionFactory;

    @Autowired
    public HibernareDictionary(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Document> getDocuments() {
        return sessionFactory.getCurrentSession().createQuery("from Document d order by d.code").list();
    }

    @Override
    public Document getDocumentByCode(int code) {
        return (Document) sessionFactory.getCurrentSession().createQuery("from Document d where d.code=:code")
                .setInteger("code", code).uniqueResult();
    }

    @Override
    public Document getDocumentById(int id) {
        return sessionFactory.getCurrentSession().get(Document.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Country> getCountries() {
        return sessionFactory.getCurrentSession().createQuery("from Country c order by c.name").list();
    }

    @Override
    public Country getCountryByCode(int code) {
        return (Country) sessionFactory.getCurrentSession().createQuery("from Country c where c.code=:code")
                .setInteger("code", code).uniqueResult();
    }

    @Override
    public Country getCountryById(int id) {
        return sessionFactory.getCurrentSession().get(Country.class, id);
    }

    @Override
    public boolean containsCountryCode(int code) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select 1 from Country c where c.code=:code")
                .setInteger("code", code);
        return query.uniqueResult() != null;
    }

    @Override
    public boolean containsDocumentCode(int code) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select 1 from Document d where d.code=:code")
                .setInteger("code", code);
        return query.uniqueResult() != null;
    }
}
