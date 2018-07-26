package ru.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.course.dto.ResponseDto;
import ru.course.dto.SuccessResponseDto;
import ru.course.dto.organization.OrganizationCreateDto;
import ru.course.dto.organization.OrganizationDto;
import ru.course.dto.organization.OrganizationFilterDto;
import ru.course.dto.organization.OrganizationListItemDto;
import ru.course.service.organization.OrganizationService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.course.util.ResponseUtil.dataResponse;
import static ru.course.util.ResponseUtil.errorResponse;
import static ru.course.util.ResponseUtil.successResponse;

@RestController
@RequestMapping(value = "/api/organization", produces = APPLICATION_JSON_VALUE)
public class OrganizationController {
    private final OrganizationService service;

    @Autowired
    public OrganizationController(OrganizationService service) {
        this.service = service;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto<List<OrganizationListItemDto>>> list(@RequestBody @Valid OrganizationFilterDto filter, BindingResult binding) {
        if (binding.hasErrors()) {
            return errorResponse(binding);
        }
        return dataResponse(service.filter(filter));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto<OrganizationDto>> get(@PathVariable int id) {
        OrganizationDto org = service.get(id);
        if (org == null) return errorResponse("Organization not found", HttpStatus.NOT_FOUND);
        return dataResponse(org);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto<SuccessResponseDto>> save(@RequestBody @Valid OrganizationCreateDto dto, BindingResult binding) {
        if (binding.hasErrors()) {
            return errorResponse(binding);
        }
        service.save(dto);
        return successResponse();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto<SuccessResponseDto>> update(@RequestBody @Valid OrganizationDto dto, BindingResult binding) {
        if (binding.hasErrors()) {
            return errorResponse(binding);
        }
        service.update(dto);
        return successResponse();
    }
}