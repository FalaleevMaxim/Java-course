package ru.course.model.user;

import ru.course.model.dictionary.Document;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_document", indexes = {
        @Index(name = "userdoc_id", columnList = "user_id", unique = true),
        @Index(name = "userdoc_code", columnList = "doc_code")
})
public class UserDocument {
    private int id;
    private Document document;
    private String docNumber;
    private Date docDate;
    private User user;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_code", referencedColumnName = "code")
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if(user.getDocument()==null) user.setDocument(this);
    }
}