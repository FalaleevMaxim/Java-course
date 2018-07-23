package ru.course.model.dictionary;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "countries", indexes = {
        @Index(name = "country_id", columnList = "id", unique = true),
        @Index(name = "country_code", columnList = "code", unique = true),
        @Index(name = "country_name", columnList = "name", unique = true)
})
public class Country implements Serializable {
    private int id;
    private int code;
    private String name;

    @Id
    @GeneratedValue
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

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
