package repository.impl;

import repository.interfaces.AuthRepository;
import java.util.HashMap;
import java.util.Map;
import entity.User;

public class AuthRepositoryImpl implements AuthRepository {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Boolean> loggedInUsers = new HashMap<>();

    @Override
    public boolean authenticate(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            loggedInUsers.put(email, true);
            return true;
        }
        return false;
    }

    @Override
    public boolean register(String username, String email, String password, String role) {
        if (users.containsKey(email)) {
            return false;
        }
        User newUser = new User(username, email, password, role);
        users.put(email, newUser);
        return true;
    }

    @Override
    public boolean logout(String email) {
        if (loggedInUsers.containsKey(email)) {
            loggedInUsers.put(email, false);
            return true;
        }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        return users.get(email);
    }
}