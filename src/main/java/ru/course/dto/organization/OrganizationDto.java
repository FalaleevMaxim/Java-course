package ru.course.dto.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * View object for Organization entity
 */
public class OrganizationDto {
    @JsonProperty(required = true)
    private int id;
    @NotBlank
    @JsonProperty(required = true)
    private String name;
    @NotBlank
    @JsonProperty(required = true)
    private String fullName;
    @NotBlank
    @Pattern(regexp = "[0-9]{10}", message = "Inn must be 10 digits")
    @JsonProperty(required = true)
    private String inn;
    @Length(min = 9, max = 9)
    @JsonProperty(required = true)
    private String kpp;
    @NotBlank
    @JsonProperty(required = true)
    private String address;
    private String phone;
    @JsonProperty(value = "isActive")
    private boolean active = true;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }


    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
