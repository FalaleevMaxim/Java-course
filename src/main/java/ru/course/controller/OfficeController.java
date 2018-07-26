package ru.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.course.dto.ResponseDto;
import ru.course.dto.SuccessResponseDto;
import ru.course.dto.office.OfficeCreateDto;
import ru.course.dto.office.OfficeDto;
import ru.course.dto.office.OfficeFilterDto;
import ru.course.dto.office.OfficeListItemDto;
import ru.course.service.office.OfficeService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.course.util.ResponseUtil.*;

@RestController
@RequestMapping(value = "/api/office", produces = APPLICATION_JSON_VALUE)
public class OfficeController {
    private final OfficeService service;

    @Autowired
    public OfficeController(OfficeService service) {
        this.service = service;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto<List<OfficeListItemDto>>> list(@RequestBody @Valid OfficeFilterDto filter, BindingResult binding) {
        if (binding.hasErrors()) {
            return errorResponse(binding);
        }
        return dataResponse(service.filter(filter));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto<OfficeDto>> get(@PathVariable int id) {
        OfficeDto office = service.get(id);
        if (office == null) return errorResponse("Office not found", HttpStatus.NOT_FOUND);
        return dataResponse(office);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto<SuccessResponseDto>> save(@RequestBody @Valid OfficeCreateDto dto, BindingResult binding) {
        if (binding.hasErrors()) {
            return errorResponse(binding);
        }
        service.save(dto);
        return successResponse();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto<SuccessResponseDto>> update(@RequestBody @Valid OfficeDto dto, BindingResult binding) {
        if (binding.hasErrors()) {
            return errorResponse(binding);
        }
        service.update(dto);
        return successResponse();
    }
}