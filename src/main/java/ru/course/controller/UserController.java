package ru.course.controller;

import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.course.controller.advice.NotFoundException;
import ru.course.dto.ResponseDto;
import ru.course.dto.SuccessResponseDto;
import ru.course.dto.user.*;
import ru.course.model.user.User;
import ru.course.storage.dictionaries.DictionariesStorage;
import ru.course.storage.office.OfficeStorage;
import ru.course.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.course.util.ResponseUtil.dataResponse;
import static ru.course.util.ResponseUtil.errorResponse;

@RestController
@RequestMapping(value = "/api/user", produces = APPLICATION_JSON_VALUE)
public class UserController {
    private final OfficeStorage officeStorage;
    private final UserStorage userStorage;
    private final DictionariesStorage dictionaries;
    private final MapperFactory mapperFactory;

    @Autowired
    public UserController(OfficeStorage officeStorage, UserStorage userStorage, DictionariesStorage dictionaries, MapperFactory mapperFactory) {
        this.officeStorage = officeStorage;
        this.userStorage = userStorage;
        this.dictionaries = dictionaries;
        this.mapperFactory = mapperFactory;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto<List<UserListItemDto>>> list(@RequestBody @Valid UserFilterDto filter) {
        return dataResponse(userStorage.filter(filter).stream()
                .map(mapperFactory.getMapperFacade(User.class, UserListItemDto.class)::map)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto<UserDto>> get(@PathVariable int id) {
        User user = userStorage.get(id);
        if (user == null) return errorResponse("Office not found", HttpStatus.NOT_FOUND);
        UserDto dto = mapperFactory.getMapperFacade(User.class, UserDto.class).map(user);
        return dataResponse(dto);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SuccessResponseDto save(@RequestBody @Valid UserCreateDto dto) {
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
        return new SuccessResponseDto();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SuccessResponseDto update(@RequestBody @Valid UserUpdateDto dto) {
        User user = mapperFactory.getMapperFacade(UserDto.class, User.class).map(dto);
        userStorage.update(user);
        return new SuccessResponseDto();
    }
}