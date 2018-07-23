package ru.course.storage.office;

import ru.course.model.office.Office;
import ru.course.storage.Storage;

import java.util.List;

public interface OfficeStorage extends Storage<Office> {
    List<Office> filter(int orgId, String name, String phone, Boolean isActive);
}
