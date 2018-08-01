package ru.course.model.user;

import ru.course.model.dictionary.Document;

import javax.persistence.*;
import java.util.Date;

@Embeddable
public class UserDocument {
    private Document document;
    private String docNumber;
    private Date docDate;
    //private User user;

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
}