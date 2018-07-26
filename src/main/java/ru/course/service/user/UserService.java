package ru.course.service.user;

import org.springframework.stereotype.Service;
import ru.course.dto.user.*;

import java.util.List;

@Service
public interface UserService {
    List<UserListItemDto> filter(UserFilterDto filter);
    UserDto get(int id);
    void save(UserCreateDto dto);
    void update(UserUpdateDto dto);
}
