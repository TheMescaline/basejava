import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        int actualStorageSize = size();
        for (int i = 0; i < actualStorageSize; i++) {
            storage[i] = null;
        }
    }

    void save(Resume resume) {
        storage[size()] = resume;
    }

    Resume get(String uuid) {
        int searchingResumePointer = -1;
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                searchingResumePointer = i;
                break;
            }
        }
        return searchingResumePointer == -1 ? null : storage[searchingResumePointer];
    }

    void delete(String uuid) {
        boolean rewritingFlag = false;
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                rewritingFlag = true;
            }
            if (rewritingFlag) {
                storage[i] = storage[i + 1];
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int actualSizeCounter = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                actualSizeCounter = i;
                break;
            }
        }
        return Arrays.copyOf(storage, actualSizeCounter);
    }

    int size() {
        return getAll().length;
    }
}
