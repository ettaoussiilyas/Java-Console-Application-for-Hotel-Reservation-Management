package service;

import repository.interfaces.AuthRepository;
import repository.impl.AuthRepositoryImpl;
import entity.User;

public class AuthService {
    private final AuthRepository authRepository;

    public AuthService() {
        this.authRepository = new AuthRepositoryImpl();
    }

    public boolean login(String email, String password) {
        if (email == null || password == null ||
                email.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        }
        return authRepository.authenticate(email, password);
    }

    public boolean register(String username, String email, String password, String role) {
        if (username == null || email == null || password == null || role == null ||
                username.trim().isEmpty() || email.trim().isEmpty() ||
                password.trim().isEmpty() || role.trim().isEmpty()) {
            return false;
        }
        // Basic email validation
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return false;
        }
        return authRepository.register(username, email, password, role);
    }

    public boolean logout(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return authRepository.logout(email);
    }

    public User getCurrentUser(String email) {
        return authRepository.getUserByEmail(email);
    }
}