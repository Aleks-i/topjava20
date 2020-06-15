package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final AtomicInteger counter = new AtomicInteger(0);

    private final Map<Integer, User> usersDB = new ConcurrentHashMap<>();

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        counter.decrementAndGet();
        return usersDB.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        usersDB.put(counter.incrementAndGet(), user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return usersDB.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return usersDB.values().stream()
                .sorted(Comparator.comparing(AbstractNamedEntity::getName, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        if (email == null) return null;
        List<User> userListWithEmail = usersDB.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
        return userListWithEmail.size() > 0 ? userListWithEmail.get(0) : null;
    }
}
