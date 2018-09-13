import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int actualStorageSize = 0;

    void clear() {
        for (int i = 0; i < actualStorageSize; i++) {
            storage[i] = null;
        }
        actualStorageSize = 0;
    }

    void save(Resume resume) {
        if (actualStorageSize == storage.length) {
            System.out.println("Error! Storage is full!");
        } else {
            int pointer = findResumePointerByUuid(resume.uuid);
            if (pointer != -1) {
                System.out.println("Error! This resume is already in the storage!");
            } else {
                storage[actualStorageSize] = resume;
                actualStorageSize++;
            }
        }
    }

    void update(Resume resume) {
        int pointer = findResumePointerByUuid(resume.uuid);
        if (pointer != -1) {
            storage[pointer] = resume;
        } else {
            System.out.println("Error! This resume is not exist!");
        }
    }

    Resume get(String uuid) {
        int pointer = findResumePointerByUuid(uuid);
        if (pointer == -1) {
            System.out.println("Error! This resume is not exist!");
        }
        return pointer == -1 ? null : storage[pointer];
    }

    void delete(String uuid) {
        int pointer = findResumePointerByUuid(uuid);
        if (pointer != -1) {
            storage[pointer] = storage[actualStorageSize - 1];
            storage[actualStorageSize - 1] = null;
            actualStorageSize--;
        } else {
            System.out.println("Error! This resume is not exist!");
        }
    }

    private int findResumePointerByUuid(String uuid) {
        int searchingResumePointer = -1;
        for (int i = 0; i < actualStorageSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                searchingResumePointer = i;
                break;
            }
        }
        return searchingResumePointer;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, actualStorageSize);
    }

    int size() {
        return actualStorageSize;
    }
}
