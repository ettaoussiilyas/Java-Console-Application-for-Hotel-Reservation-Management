package repository.interfaces;

import entity.User;

public interface AuthRepository {
    boolean authenticate(String email, String password);
    boolean register(String username, String email, String password, String role);
    boolean logout(String email);
    User getUserByEmail(String email);
}