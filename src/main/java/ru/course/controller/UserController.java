package ru.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.course.dto.ResponseDto;
import ru.course.dto.SuccessResponseDto;
import ru.course.dto.user.*;
import ru.course.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.course.util.ResponseUtil.*;

@RestController
@RequestMapping(value = "/api/user", produces = APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto<List<UserListItemDto>>> list(@RequestBody @Valid UserFilterDto filter, BindingResult binding) {
        if (binding.hasErrors()) {
            return errorResponse(binding);
        }
        return dataResponse(service.filter(filter));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto<UserDto>> get(@PathVariable int id) {
        UserDto user = service.get(id);
        if (user == null) return errorResponse("Office not found", HttpStatus.NOT_FOUND);
        return dataResponse(user);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto<SuccessResponseDto>> save(@RequestBody @Valid UserCreateDto dto, BindingResult binding) {
        service.save(dto);
        return successResponse();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto<SuccessResponseDto>> update(@RequestBody @Valid UserUpdateDto dto, BindingResult binding) {
        if (binding.hasErrors()) {
            return errorResponse(binding);
        }
        service.update(dto);
        return successResponse();
    }
}