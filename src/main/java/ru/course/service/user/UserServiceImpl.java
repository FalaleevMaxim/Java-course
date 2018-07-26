package ru.course.service.user;

import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.course.controller.advice.NotFoundException;
import ru.course.dto.user.*;
import ru.course.model.user.User;
import ru.course.storage.dictionaries.DictionariesStorage;
import ru.course.storage.office.OfficeStorage;
import ru.course.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final OfficeStorage officeStorage;
    private final UserStorage userStorage;
    private final DictionariesStorage dictionaries;
    private final MapperFactory mapperFactory;

    @Autowired
    public UserServiceImpl(OfficeStorage officeStorage, UserStorage userStorage, DictionariesStorage dictionaries, MapperFactory mapperFactory) {
        this.officeStorage = officeStorage;
        this.userStorage = userStorage;
        this.dictionaries = dictionaries;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public List<UserListItemDto> filter(UserFilterDto filter) {
        return userStorage.filter(filter).stream()
                .map(mapperFactory.getMapperFacade(User.class, UserListItemDto.class)::map)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto get(int id) {
        User user = userStorage.get(id);
        if (user == null) return null;
        return mapperFactory.getMapperFacade(User.class, UserDto.class).map(user);
    }

    @Override
    public void save(UserCreateDto dto) {
        if (dto.officeId != null && !officeStorage.contains(dto.officeId)) {
            throw new NotFoundException("Office by id " + dto.officeId + " not found");
        }
        if (dto.citizenshipCode != null && !dictionaries.containsCountryCode(dto.citizenshipCode)) {
            throw new NotFoundException("Country by code " + dto.citizenshipCode + " not found");
        }
        if (dto.docCode != null && !dictionaries.containsDocumentCode(dto.docCode)) {
            throw new NotFoundException("Document by code " + dto.docCode + " not found");
        }
        User user = mapperFactory.getMapperFacade(UserDto.class, User.class).map(dto);
        userStorage.save(user);
    }

    @Override
    public void update(UserUpdateDto dto) {
        if (dto.officeId != null && !officeStorage.contains(dto.officeId)) {
            throw new NotFoundException("Office by id " + dto.officeId + " not found");
        }
        if (dto.citizenshipCode != null && !dictionaries.containsCountryCode(dto.citizenshipCode)) {
            throw new NotFoundException("Country by code " + dto.citizenshipCode + " not found");
        }
        if (dto.docCode != null && !dictionaries.containsDocumentCode(dto.docCode)) {
            throw new NotFoundException("Document by code " + dto.docCode + " not found");
        }
        User user = mapperFactory.getMapperFacade(UserDto.class, User.class).map(dto);
        userStorage.update(user);
    }
}
