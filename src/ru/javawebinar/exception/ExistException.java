package ru.javawebinar.exception;

public class ExistException extends StorageException {
    public ExistException(String uuid) {
        super("Error! Resume " + uuid + " is already in the storage!", uuid);
    }
}
