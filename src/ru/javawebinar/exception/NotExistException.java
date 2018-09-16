package ru.javawebinar.exception;

public class NotExistException extends StorageException {
    public NotExistException(String uuid) {
        super("Error! Resume " + uuid + " is not exist!", uuid);
    }
}
