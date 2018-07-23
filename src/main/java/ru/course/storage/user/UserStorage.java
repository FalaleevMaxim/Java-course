package ru.course.storage.user;

import ru.course.dto.user.UserFilterDto;
import ru.course.model.user.User;
import ru.course.storage.Storage;

import java.util.List;

public interface UserStorage extends Storage<User> {
    List<User> filter(UserFilterDto filter);
}
