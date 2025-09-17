package ui.menu;

import service.AuthService;
import entity.User;
import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner;
    private final AuthService authService;
    private boolean running;
    private User currentUser;

    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.authService = new AuthService();
        this.running = true;
    }

    public void start() {
        while (running) {
            MenuHandler.showWelcomeMenu();
            handleMainChoice();
        }
    }

    private void handleMainChoice() {
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> handleRegistration();
            case "2" -> handleLogin();
            case "3" -> handleExit();
            default -> MenuHandler.showError("Option invalide");
        }
    }

    private void handleRegistration() {
        MenuHandler.showRegistrationForm();

        MenuHandler.showPrompt("Nom");
        String nom = scanner.nextLine();

        MenuHandler.showPrompt("Prénom");
        String prenom = scanner.nextLine();

        MenuHandler.showPrompt("Email");
        String email = scanner.nextLine();

        MenuHandler.showPrompt("Mot de passe (min 6 caractères)");
        String password = scanner.nextLine();

        // Validation
        if (!validateRegistrationInput(nom, prenom, email, password)) {
            return;
        }

        if (authService.register(nom + " " + prenom, email, password, "CLIENT")) {
            MenuHandler.showSuccess("Inscription réussie!");
        } else {
            MenuHandler.showError("Email déjà utilisé ou invalide");
        }
        waitForEnter();
    }

    private boolean validateRegistrationInput(String nom, String prenom, String email, String password) {
        if (nom.trim().isEmpty() || prenom.trim().isEmpty()) {
            MenuHandler.showError("Le nom et le prénom sont obligatoires");
            return false;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            MenuHandler.showError("Format d'email invalide");
            return false;
        }
        if (password.length() < 6) {
            MenuHandler.showError("Le mot de passe doit contenir au moins 6 caractères");
            return false;
        }
        return true;
    }

    private void handleLogin() {
        MenuHandler.showLoginForm();

        MenuHandler.showPrompt("Email");
        String email = scanner.nextLine();

        MenuHandler.showPrompt("Mot de passe");
        String password = scanner.nextLine();

        if (authService.login(email, password)) {
            currentUser = authService.getCurrentUser(email);
            handleUserMenu();
        } else {
            MenuHandler.showError("Email ou mot de passe incorrect");
            waitForEnter();
        }
    }

    private void handleUserMenu() {
        boolean userMenuRunning = true;
        while (userMenuRunning && running) {
            boolean isAdmin = "ADMIN".equals(currentUser.getRole());
            MenuHandler.showUserMenu(currentUser.getUsername(), isAdmin);
            userMenuRunning = handleUserChoice(isAdmin);
        }
    }

    private boolean handleUserChoice(boolean isAdmin) {
        String choice = scanner.nextLine();
        if (isAdmin) {
            return handleAdminChoice(choice);
        } else {
            return handleClientChoice(choice);
        }
    }

    private boolean handleAdminChoice(String choice) {
        switch (choice) {
            case "1" -> handleCreateHotel();
            case "2" -> handleUpdateHotel();
            case "3" -> handleDeleteHotel();
            case "4" -> handleListHotels();
            case "5" -> handleCreateReservation();
            case "6" -> handleCancelReservation();
            case "7" -> handleReservationHistory();
            case "8" -> handleUpdateProfile();
            case "9" -> handleChangePassword();
            case "10" -> {
                handleLogout();
                return false;
            }
            default -> MenuHandler.showError("Option invalide");
        }
        return true;
    }

    private boolean handleClientChoice(String choice) {
        switch (choice) {
            case "1" -> handleListHotels();
            case "2" -> handleCreateReservation();
            case "3" -> handleCancelReservation();
            case "4" -> handleReservationHistory();
            case "5" -> handleUpdateProfile();
            case "6" -> handleChangePassword();
            case "7" -> {
                handleLogout();
                return false;
            }
            default -> MenuHandler.showError("Option invalide");
        }
        return true;
    }

    // Placeholder methods - to be implemented with actual functionality
    private void handleCreateHotel() {
        System.out.println("Création d'hôtel - À implémenter");
        waitForEnter();
    }

    private void handleUpdateHotel() {
        System.out.println("Modification d'hôtel - À implémenter");
        waitForEnter();
    }

    private void handleDeleteHotel() {
        System.out.println("Suppression d'hôtel - À implémenter");
        waitForEnter();
    }

    private void handleListHotels() {
        System.out.println("Liste des hôtels - À implémenter");
        waitForEnter();
    }

    private void handleCreateReservation() {
        System.out.println("Création de réservation - À implémenter");
        waitForEnter();
    }

    private void handleCancelReservation() {
        System.out.println("Annulation de réservation - À implémenter");
        waitForEnter();
    }

    private void handleReservationHistory() {
        System.out.println("Historique des réservations - À implémenter");
        waitForEnter();
    }

    private void handleUpdateProfile() {
        System.out.println("Modification du profil - À implémenter");
        waitForEnter();
    }

    private void handleChangePassword() {
        System.out.println("Changement de mot de passe - À implémenter");
        waitForEnter();
    }

    private void handleLogout() {
        authService.logout(currentUser.getEmail());
        currentUser = null;
        MenuHandler.showSuccess("Déconnexion réussie");
        waitForEnter();
    }

    private void handleExit() {
        if (currentUser != null) {
            handleLogout();
        }
        MenuHandler.showSuccess("Au revoir!");
        running = false;
    }

    private void waitForEnter() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }
}