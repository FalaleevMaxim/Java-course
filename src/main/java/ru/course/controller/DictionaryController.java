package ru.course.controller;

import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.course.dto.ResponseDto;
import ru.course.dto.dictionaries.CountryDto;
import ru.course.dto.dictionaries.DocumentDto;
import ru.course.model.dictionary.Country;
import ru.course.model.dictionary.Document;
import ru.course.storage.dictionaries.DictionariesStorage;
import ru.course.util.ResponseUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api", method = {RequestMethod.GET, RequestMethod.POST})
public class DictionaryController {
    private final DictionariesStorage storage;
    private final MapperFactory mapperFactory;

    @Autowired
    public DictionaryController(DictionariesStorage storage, MapperFactory mapperFactory) {
        this.storage = storage;
        this.mapperFactory = mapperFactory;
    }

    @RequestMapping("/docs")
    public ResponseEntity<ResponseDto<List<DocumentDto>>> docs() {
        return ResponseUtil.dataResponse(
                storage.getDocuments().stream()
                        .map(mapperFactory.getMapperFacade(Document.class, DocumentDto.class)::map)
                        .collect(Collectors.toList()));
    }

    @RequestMapping("/countries")
    public ResponseEntity<ResponseDto<List<CountryDto>>> countries() {
        return ResponseUtil.dataResponse(
                storage.getCountries().stream()
                        .map(mapperFactory.getMapperFacade(Country.class, CountryDto.class)::map)
                        .collect(Collectors.toList()));
    }
}
