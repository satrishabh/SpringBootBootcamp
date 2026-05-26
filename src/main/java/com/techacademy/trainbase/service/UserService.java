package com.techacademy.trainbase.service;

import com.techacademy.trainbase.entity.User;
import com.techacademy.trainbase.exception.UserAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public UserService() {
        User sample = User.builder()
            .id(nextId.getAndIncrement())
            .username("john_doe")
            .email("john@example.com")
            .firstName("John")
            .lastName("Doe")
            .password("secret")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        users.put(sample.getId(), sample);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public Page<User> getUsersPaginated(Pageable pageable) {
        List<User> sorted = users.values().stream()
            .sorted((a, b) -> a.getId().compareTo(b.getId()))
            .collect(Collectors.toList());

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int start = currentPage * pageSize;
        int end = Math.min(start + pageSize, sorted.size());

        List<User> pageContent = start >= sorted.size() ? new ArrayList<>() : sorted.subList(start, end);
        return new PageImpl<>(pageContent, pageable, sorted.size());
    }

    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<User> getUserByUsername(String username) {
        return users.values().stream()
            .filter(user -> user.getUsername().equalsIgnoreCase(username))
            .findFirst();
    }

    public Optional<User> getUserByEmail(String email) {
        return users.values().stream()
            .filter(user -> user.getEmail().equalsIgnoreCase(email))
            .findFirst();
    }

    public boolean existsByUsername(String username) {
        return users.values().stream()
            .anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    public boolean existsByEmail(String email) {
        return users.values().stream()
            .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    public User createUser(User user) {
        if (existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        long id = nextId.getAndIncrement();
        user.setId(id);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        users.put(id, user);
        return user;
    }

    public User updateUser(Long id, User user) {
        user.setUpdatedAt(LocalDateTime.now());
        users.put(id, user);
        return user;
    }

    public boolean deleteUser(Long id) {
        return users.remove(id) != null;
    }
}
