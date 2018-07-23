package ru.course.dto.office;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

public class OfficeDto {
    @JsonProperty(required = true)
    private int id;
    @NotBlank
    @JsonProperty(required = true)
    private String name;
    @NotBlank
    @JsonProperty(required = true)
    private String address;
    private String phone;
    @JsonProperty(value = "isActive")
    private boolean isActive = true;
    private Integer orgId;

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
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}
