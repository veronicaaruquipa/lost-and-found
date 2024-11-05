package org.example.lostandfoundapp.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MockUserService {
    private final Map<Long, String> userDatabase = new HashMap<>();

    public MockUserService() {
        userDatabase.put(1001L, "Alice Johnson");
        userDatabase.put(1002L, "Bob Smith");
        userDatabase.put(1003L, "Charlie Brown");
        userDatabase.put(1004L, "Clerk Kent");
        userDatabase.put(1005L, "Steve Rogers");
    }

    public String getUserName(Long userId) {
        return userDatabase.getOrDefault(userId, "Unknown User");
    }
}