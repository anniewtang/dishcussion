package codeu.model.store.persistence;

import codeu.model.data.User;

import java.time.Instant;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Contains tests of the PersistentStorageAgent class. Currently that class is just a pass-through
 * to PersistentDataStore, so these tests are pretty trivial. If you modify how
 * PersistentStorageAgent writes to PersistentDataStore, or if you swap out the backend to something
 * other than PersistentDataStore, then modify these tests.
 */
public class PersistentStorageAgentTest {

    private PersistentDataStore mockPersistentDataStore;
    private PersistentStorageAgent persistentStorageAgent;

    @Before
    public void setup() {
        mockPersistentDataStore = Mockito.mock(PersistentDataStore.class);
        persistentStorageAgent = PersistentStorageAgent.getTestInstance(mockPersistentDataStore);
    }

    @Test
    public void testLoadUsers() throws PersistentDataStoreException {
        persistentStorageAgent.loadUsers();
        Mockito.verify(mockPersistentDataStore).loadUsers();
    }

    @Test
    public void testWriteThroughUser() {
        User user =
                new User(
                        UUID.randomUUID(),
                        "test_username",
                        "$2a$10$5GNCbSPS1sqqM9.hdiE2hexn1w.vnNoR.CaHIztFEhdAD7h82tqX.",
                        Instant.now(),
                        "My name is my name");
        persistentStorageAgent.writeThrough(user);
        Mockito.verify(mockPersistentDataStore).writeThrough(user);
    }
}
