package ru.course.storage.dictionaries;

import ru.course.model.dictionary.Country;
import ru.course.model.dictionary.Document;

import java.util.List;

public interface DictionariesStorage {
    List<Document> getDocuments();
    Document getDocumentByCode(int code);
    Document getDocumentById(int id);
    List<Country> getCountries();
    Country getCountryByCode(int code);
    Country getCountryById(int id);
    boolean containsCountryCode(int code);
    boolean containsDocumentCode(int code);
}
