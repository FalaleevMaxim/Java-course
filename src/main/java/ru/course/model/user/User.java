package ru.course.model.user;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import ru.course.model.dictionary.Country;
import ru.course.model.office.Office;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "user_id", columnList = "id", unique = true),
        @Index(name = "user_office", columnList = "office_id"),
        @Index(name = "user_citizenship", columnList = "citizenship_code"),
        @Index(name = "user_firstname", columnList = "firstName"),
        @Index(name = "user_secondName", columnList = "secondName"),
        @Index(name = "user_middleName", columnList = "middleName"),
        @Index(name = "user_position", columnList = "position"),
        @Index(name = "user_phone", columnList = "phone"),
})
public class User {
    private Integer id;
    private String firstName;
    private String secondName;
    private String middleName;
    private String position;
    private String phone;
    private UserDocument document;
    private Country citizenship;
    private Office office;
    private boolean identified;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotBlank
    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(nullable = false)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /*@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyToOne(LazyToOneOption.FALSE)*/
    public UserDocument getDocument() {
        return document;
    }

    public void setDocument(UserDocument document) {
        this.document = document;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "citizenship_code", referencedColumnName = "code")
    public Country getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(Country citizenship) {
        this.citizenship = citizenship;
    }

    public boolean isIdentified() {
        return identified;
    }

    public void setIdentified(boolean identified) {
        this.identified = identified;
    }

    @ManyToOne
    @JoinColumn(name = "office_id")
    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
}
