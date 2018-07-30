package ru.course.integration.tests;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import ru.course.model.organization.Organization;
import ru.course.storage.organizations.OrganizationsStorage;

public class BeforeIT {
    private SessionFactory sessionFactory;
    private OrganizationsStorage organizationsStorage;

    @BeforeSuite
    public void prepareDb(){
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:testContext.xml");
            sessionFactory = (SessionFactory) context.getBean("sessionFactory");
            organizationsStorage = context.getBean(OrganizationsStorage.class);
            fillDb();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void fillDb(){
        Assert.assertNotNull(sessionFactory, "SessionFactory null");
        Assert.assertNotNull(organizationsStorage, "storage null");
        Organization org = new Organization();
        org.setName("Org1");
        org.setFullName("Organization1");
        org.setInn("1234567890");
        org.setPhone("12-34-56");
        organizationsStorage.save(org);
    }
}
