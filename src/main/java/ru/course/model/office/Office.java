package ru.course.model.office;

import ru.course.model.organization.Organization;

import javax.persistence.*;

@Entity
@Table(name = "office", indexes = {
        @Index(name = "office_id", columnList = "id", unique = true),
        @Index(name = "office_org", columnList = "org_id"),
        @Index(name = "office_phone", columnList = "phone"),
        @Index(name = "office_active", columnList = "active")
})
public class Office {
    private int id;
    private Organization organization;
    private String name;
    private String address;
    private String phone;
    private boolean isActive = true;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(nullable = false)
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
