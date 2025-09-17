package ui.menu;

import service.AuthService;
import entity.User;
import java.util.Scanner;

public class MainMenu {
    private final AuthService authService;
    private final Scanner scanner;
    private boolean running;
    private static final String MENU_BORDER = "============================";

    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.authService = new AuthService();
        this.running = true;
    }

    // ... other methods remain the same ...

    private void handleRegistration() {
        System.out.print("Entrez votre nom d'utilisateur: ");
        String username = scanner.nextLine();
        System.out.print("Entrez votre email: ");
        String email = scanner.nextLine();
        System.out.print("Entrez votre mot de passe: ");
        String password = scanner.nextLine();

        // Default role is "CLIENT"
        if (authService.register(username, email, password, "CLIENT")) {
            System.out.println("Inscription réussie!");
        } else {
            System.out.println("L'inscription a échoué. L'email existe peut-être déjà.");
        }
    }

    private void handleLogin() {
        System.out.print("Entrez votre email: ");
        String email = scanner.nextLine();
        System.out.print("Entrez votre mot de passe: ");
        String password = scanner.nextLine();

        if (authService.login(email, password)) {
            User user = authService.getCurrentUser(email);
            showLoggedInMenu(user);
        } else {
            System.out.println("Échec de la connexion. Vérifiez vos identifiants.");
        }
    }

    private void showLoggedInMenu(User user) {
        System.out.println("\nBienvenue, " + user.getUsername() + "!");
        System.out.println("Role: " + user.getRole());
        System.out.println("ID: " + user.getId());

        // Show different menu options based on role
        if (user.getRole().equals("ADMIN")) {
            showAdminMenu();
        } else {
            showClientMenu();
        }
    }

    private void showAdminMenu() {
        // Add admin-specific menu options
        System.out.println("1. Gérer les utilisateurs");
        System.out.println("2. Gérer les hôtels");
        System.out.println("3. Déconnexion");
    }

    private void showClientMenu() {
        // Add client-specific menu options
        System.out.println("1. Voir les hôtels");
        System.out.println("2. Mes réservations");
        System.out.println("3. Déconnexion");
    }
}