package ru.javawebinar.topjava.repository.inmemory;

import javafx.collections.transformation.SortedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, User> userList = new ConcurrentHashMap<>();

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return userList.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        userList.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userList.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userList.entrySet().stream()
                .map(Map.Entry::getValue)
                .sorted(Comparator.comparing(u -> u.getName(), Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        if (email == null) return null;
        List<User> userListWithEmail = userList.entrySet().stream()
                .filter(userList -> userList.getValue().getEmail().equalsIgnoreCase(email))
                .map(user -> user.getValue())
                .collect(Collectors.toList());
        return userListWithEmail.size() > 0 ? userListWithEmail.get(0) : null;

    }

}
