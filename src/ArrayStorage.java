import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int actualStorageSize = 0;

    void clear() {
        for (int i = 0; i < actualStorageSize; i++) {
            storage[i] = null;
        }
        actualStorageSize = 0;
    }

    void save(Resume resume) {
        storage[actualStorageSize] = resume;
        actualStorageSize++;
    }

    Resume get(String uuid) {
        int searchingResumePointer = -1;
        for (int i = 0; i < actualStorageSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                searchingResumePointer = i;
                break;
            }
        }
        return searchingResumePointer == -1 ? null : storage[searchingResumePointer];
    }

    void delete(String uuid) {
        boolean rewritingFlag = false;
        for (int i = 0; i < actualStorageSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                rewritingFlag = true;
            }
            if (rewritingFlag) {
                storage[i] = storage[i + 1];
            }
        }
        actualStorageSize--;
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
