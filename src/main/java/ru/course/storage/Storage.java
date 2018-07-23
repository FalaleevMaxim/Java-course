package ru.course.storage;

public interface Storage<T> {
    T get(int id);
    boolean contains(int id);
    void save(T organization);
    boolean remove(int id);
    void update(T organization);
}
