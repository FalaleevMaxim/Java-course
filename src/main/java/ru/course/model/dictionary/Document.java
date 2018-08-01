package ru.course.model.dictionary;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "documents", indexes = {
        @Index(name = "document_id", columnList = "id", unique = true),
        @Index(name = "document_code", columnList = "code", unique = true)
})
public class Document implements Serializable {
    private int id;
    private int code;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(unique = true)
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
