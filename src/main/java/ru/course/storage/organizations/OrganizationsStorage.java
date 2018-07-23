package ru.course.storage.organizations;

import ru.course.model.organization.Organization;
import ru.course.storage.Storage;

import java.util.List;

public interface OrganizationsStorage extends Storage<Organization> {
    List<Organization> filter(String name, String inn, Boolean isActive);
}
