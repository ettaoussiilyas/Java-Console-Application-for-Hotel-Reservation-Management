package ui.menu;

import java.util.Scanner;
import java.util.List;
import entity.User;
import entity.Hotel;
import entity.Reservation;
import service.HotelService;
import service.AuthService;
import service.ReservationService;
import java.util.UUID;

public class MainMenu {
    private final Scanner scanner;
    private final AuthService authService;
    private final HotelService hotelService;
    private final ReservationService reservationService;
    private boolean running;
    private User currentUser;

    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.authService = new AuthService();
        this.hotelService = new HotelService();
        this.reservationService = new ReservationService();
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

        MenuHandler.showRoleSelection();
        String roleChoise = scanner.nextLine();
        String role = null;

        switch (roleChoise) {
            case "1":
                role = "CLIENT";
                break;
            case "2":
                role = "ADMIN";
                break;
            default:
                MenuHandler.showError("Choix invalide");
        }

        // Validation
        if (!validateRegistrationInput(nom, prenom, email, password)) {
            return;
        }

        if (authService.register(nom + " " + prenom, email, password, role)) {
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
            if ("ADMIN".equals(currentUser.getRole())) {
                MenuHandler.showAdminMenu(currentUser.getUsername());
                userMenuRunning = handleAdminMenu();
            } else {
                MenuHandler.showClientMenu(currentUser.getUsername());
                userMenuRunning = handleClientMenu();
            }
        }
    }

    private boolean handleAdminMenu() {
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> handleCreateHotel();
            case "2" -> handleUpdateHotel();
            case "3" -> handleDeleteHotel();
            case "4" -> handleListHotels();
            case "5" -> handleViewAllReservations();
            case "6" -> handleManageReservations();
            case "7" -> handleUpdateProfile();
            case "8" -> handleChangePassword();
            case "9" -> {
                handleLogout();
                return false;
            }
            default -> {
                MenuHandler.showError("Option invalide");
                waitForEnter();
            }
        }
        return true;
    }

    private boolean handleClientMenu() {
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> handleListAvailableHotels();
            case "2" -> handleCreateReservation();
            case "3" -> handleCancelReservation();
            case "4" -> handleReservationHistory();
            case "5" -> handleUpdateProfile();
            case "6" -> handleChangePassword();
            case "7" -> {
                handleLogout();
                return false;
            }
            default -> {
                MenuHandler.showError("Option invalide");
                waitForEnter();
            }
        }
        return true;
    }

    // New methods for admin-specific functionality
    private void handleViewAllReservations() {
        System.out.println("\n=== Toutes les réservations ===");


        waitForEnter();
    }

    private void handleManageReservations() {
        System.out.println("\n=== Gestion des réservations ===");
        // TODO: Implement reservation management (admin only)
        waitForEnter();
    }

    // Updated method for client-specific functionality


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
    MenuHandler.showHotelFormHeader("CRÉATION D'UN NOUVEL HÔTEL");

    try {
        MenuHandler.showPrompt("Nom de l'hôtel");
        String name = scanner.nextLine();

        MenuHandler.showPrompt("Adresse");
        String address = scanner.nextLine();

        int totalRooms = 0;
        float rating = 0;
        double price = 0;

        try {
            MenuHandler.showPrompt("Nombre total de chambres");
            totalRooms = Integer.parseInt(scanner.nextLine());

            MenuHandler.showPrompt("Note (0-5)");
            rating = Float.parseFloat(scanner.nextLine());

            MenuHandler.showPrompt("Prix par nuit");
            price = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            MenuHandler.showError("Format de nombre invalide. Veuillez entrer des nombres valides.");
            waitForEnter();
            return;
        }

        if (rating < 0 || rating > 5) {
            MenuHandler.showError("La note doit être comprise entre 0 et 5");
            waitForEnter();
            return;
        }

        Hotel newHotel = hotelService.createHotel(name, address, totalRooms, rating, price);
        if (newHotel != null) {
            MenuHandler.showSuccess(" Hôtel créé avec succès! ID: " + newHotel.getHotelId());
        } else {
            MenuHandler.showError("Impossible de créer l'hôtel. Vérifiez que le nom n'existe pas déjà.");
        }
    } catch (Exception e) {
        MenuHandler.showError("Une erreur est survenue: " + e.getMessage());
    }
    waitForEnter();
}

    private void handleUpdateHotel() {
        MenuHandler.clearScreen();
        System.out.println(MenuHandler.DOUBLE_BORDER);
        System.out.println("           MODIFICATION D'UN HÔTEL");
        System.out.println(MenuHandler.DOUBLE_BORDER);

        // First, list all hotels for reference
        displayHotelsList();

        MenuHandler.showPrompt("ID de l'hôtel à modifier");
        String hotelId = scanner.nextLine();

        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel == null) {
            MenuHandler.showError("Hôtel non trouvé");
            waitForEnter();
            return;
        }

        System.out.println("\nInformations actuelles:");
        System.out.println("Nom: " + hotel.getName());
        System.out.println("Adresse: " + hotel.getAddress());
        System.out.println("Note: " + hotel.getRating());
        System.out.println("Prix: " + hotel.getPrice());

        MenuHandler.showPrompt("\nNouveau nom (ou Entrée pour garder l'ancien)");
        String name = scanner.nextLine();
        name = name.isEmpty() ? hotel.getName() : name;

        MenuHandler.showPrompt("Nouvelle adresse (ou Entrée pour garder l'ancienne)");
        String address = scanner.nextLine();
        address = address.isEmpty() ? hotel.getAddress() : address;

        MenuHandler.showPrompt("Nouveau prix (ou Entrée pour garder l'ancien)");
        String priceStr = scanner.nextLine();
        double price = priceStr.isEmpty() ? hotel.getPrice() : Double.parseDouble(priceStr);

        MenuHandler.showPrompt("Nouvelle note (0-5) (ou Entrée pour garder l'ancienne)");
        String ratingStr = scanner.nextLine();
        float rating = ratingStr.isEmpty() ? hotel.getRating() : Float.parseFloat(ratingStr);

        try {
            if (hotelService.updateHotel(hotelId, name, address, price, rating)) {
                MenuHandler.showSuccess(" Hôtel mis à jour avec succès!");
            } else {
                MenuHandler.showError("Impossible de mettre à jour l'hôtel");
            }
        } catch (NumberFormatException e) {
            MenuHandler.showError("Format de nombre invalide");
        }
        waitForEnter();
    }

private void handleDeleteHotel() {
    MenuHandler.showHotelFormHeader("SUPPRESSION D'UN HÔTEL");
    displayHotelsList();

    MenuHandler.showPrompt("ID de l'hôtel à supprimer");
    String hotelId = scanner.nextLine();

    Hotel hotel = hotelService.getHotelById(hotelId);
    if (hotel == null) {
        MenuHandler.showError("Hôtel non trouvé");
        waitForEnter();
        return;
    }

    MenuHandler.showPrompt("Êtes-vous sûr de vouloir supprimer l'hôtel '" + hotel.getName() + "' ? (O/N)");
    String confirmation = scanner.nextLine();

    if (confirmation.equalsIgnoreCase("O")) {
        if (hotelService.deleteHotel(hotelId)) {
            MenuHandler.showSuccess(" Hôtel supprimé avec succès!");
        } else {
            MenuHandler.showError("Impossible de supprimer l'hôtel. Vérifiez qu'il n'a pas de réservations actives.");
        }
    } else {
        System.out.println("\nSuppression annulée.");
    }
    waitForEnter();
}

private void handleListHotels() {
    if ("ADMIN".equals(currentUser.getRole())) {
        MenuHandler.showHotelFormHeader("LISTE DES HÔTELS");
        displayHotelsList();
    } else {
        // For clients, show only available hotels
        handleListAvailableHotels();
    }
    waitForEnter();
}

    private void handleListAvailableHotels() {
        MenuHandler.clearScreen();
        System.out.println(MenuHandler.DOUBLE_BORDER);
        System.out.println("           HÔTELS DISPONIBLES");
        System.out.println(MenuHandler.DOUBLE_BORDER);

        List<Hotel> availableHotels = hotelService.getAvailableHotels(1); // At least 1 room available
        if (availableHotels.isEmpty()) {
            System.out.println("\nAucun hôtel disponible actuellement.");
        } else {
            displayHotelsList(availableHotels);
        }
        waitForEnter();
    }

    // Helper method to display hotels
    private void displayHotelsList() {
        displayHotelsList(hotelService.getAllHotels());
    }

    private void displayHotelsList(List<Hotel> hotels) {
        if (hotels.isEmpty()) {
            System.out.println("\nAucun hôtel trouvé.");
            return;
        }

        System.out.printf("\n%-36s | %-20s | %-15s | %-8s | %-6s | %s%n",
                "ID", "NOM", "CHAMBRES DISPO", "PRIX", "NOTE", "ADRESSE");
        System.out.println("-".repeat(120));

        for (Hotel hotel : hotels) {
            System.out.printf("%-36s | %-20s | %6d/%-8d | %8.2f€ | %6.1f | %s%n",
                    hotel.getHotelId(),
                    hotel.getName(),
                    hotel.getAvailableRooms(),
                    hotel.getTotalRooms(),
                    hotel.getPrice(),
                    hotel.getRating(),
                    hotel.getAddress());
        }
        System.out.println();
    }

    private void handleCreateReservation() {
    MenuHandler.showHotelFormHeader("NOUVELLE RÉSERVATION");
    
    // Display available hotels
    List<Hotel> availableHotels = hotelService.getAvailableHotels(1);
    if (availableHotels.isEmpty()) {
        System.out.println("Aucun hôtel disponible pour réservation.");
        return;
    }
    
    displayHotelsList(availableHotels);
    
    System.out.print("Entrez l'ID de l'hôtel: ");
    String hotelId = scanner.nextLine();
    
    System.out.print("Nombre de nuits: ");
    int nights = Integer.parseInt(scanner.nextLine());
    
    System.out.print("Nombre de chambres: ");
    int rooms = Integer.parseInt(scanner.nextLine());
    
    // Get current date as check-in
    String checkInDate = java.time.LocalDate.now().toString();
    // Calculate check-out date
    String checkOutDate = java.time.LocalDate.now().plusDays(nights).toString();
    
    double totalPrice = reservationService.calculateTotalPrice(hotelId, nights, rooms);
    
    Reservation reservation = reservationService.createReservation(
        UUID.randomUUID().toString(),
        hotelId,
        currentUser.getId(),
        checkInDate,
        checkOutDate,
        rooms,
        totalPrice,
        "Confirmed"
    );
    
    if (reservation != null) {
        System.out.println("Réservation créée avec succès!");
        System.out.println("Prix total: " + totalPrice + "€");
    } else {
        System.out.println("Erreur lors de la création de la réservation.");
    }
}

private void handleCancelReservation() {
    MenuHandler.showHotelFormHeader("ANNULER UNE RÉSERVATION");
    
    List<Reservation> userReservations = reservationService.getReservationsByCustomerId(
        String.valueOf(currentUser.getId())
    );
    
    if (userReservations.isEmpty()) {
        MenuHandler.showError("Vous n'avez aucune réservation active");
        waitForEnter();
        return;
    }
    
    displayReservationsList(userReservations);
    
    MenuHandler.showPrompt("ID de la réservation à annuler");
    String reservationId = scanner.nextLine();
    
    // Use the new findById method from ReservationService
    Reservation reservation = reservationService.findById(reservationId);
    if (reservation == null || !reservation.getCustomerId().equals(String.valueOf(currentUser.getId()))) {
        MenuHandler.showError("Réservation non trouvée ou non autorisée");
        waitForEnter();
        return;
    }
    
    if (reservationService.cancelReservation(reservationId)) {
        hotelService.updateRoomAvailability(reservation.getHotelId(), reservation.getNumberOfRooms());
        MenuHandler.showSuccess(" Réservation annulée avec succès!");
    } else {
        MenuHandler.showError("Impossible d'annuler la réservation");
    }
    waitForEnter();
}

    private void handleReservationHistory() {
    MenuHandler.showHotelFormHeader("HISTORIQUE DES RÉSERVATIONS");
    
    List<Reservation> reservations;
    if ("ADMIN".equals(currentUser.getRole())) {
        reservations = reservationService.getAllRservations();
    } else {
        reservations = reservationService.getReservationsByCustomerId(
            String.valueOf(currentUser.getId())
        );
    }
    
    if (reservations.isEmpty()) {
        System.out.println("\nAucune réservation trouvée.");
        waitForEnter();
        return;
    }
    
    displayReservationsList(reservations);
    waitForEnter();
}

private void displayReservationsList(List<Reservation> reservations) {
    System.out.printf("\n%-36s | %-20s | %-10s | %-10s | %-6s | %-10s | %s%n",
            "ID", "HOTEL", "CHECK-IN", "CHECK-OUT", "CHAMBRES", "PRIX", "STATUT");
    System.out.println("-".repeat(120));
    
    for (Reservation res : reservations) {
        Hotel hotel = hotelService.getHotelById(res.getHotelId());
        String hotelName = hotel != null ? hotel.getName() : "N/A";
        
        System.out.printf("%-36s | %-20s | %-10s | %-10s | %8d | %8.2f€ | %s%n",
                res.getReservationId(),
                hotelName,
                res.getCheckInDate(),
                res.getCheckOutDate(),
                res.getNumberOfRooms(),
                res.getTotalPrice(),
                res.getReservationStatus());
    }
    System.out.println();
}

    private void handleUpdateProfile() {
    MenuHandler.showHotelFormHeader("MODIFIER MON PROFIL");
    
    MenuHandler.showPrompt("Nouveau nom d'utilisateur (ou Entrée pour garder l'ancien)");
    String newUsername = scanner.nextLine();
    
    MenuHandler.showPrompt("Nouvel email (ou Entrée pour garder l'ancien)");
    String newEmail = scanner.nextLine();
    
    if (!newUsername.trim().isEmpty()) {
        currentUser.setUsername(newUsername);
    }
    
    if (!newEmail.trim().isEmpty()) {
        if (newEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            currentUser.setEmail(newEmail);
            MenuHandler.showSuccess(" Profil mis à jour avec succès!");
        } else {
            MenuHandler.showError("Format d'email invalide");
        }
    }
    waitForEnter();
}

private void handleChangePassword() {
    MenuHandler.showHotelFormHeader("CHANGER MOT DE PASSE");
    
    MenuHandler.showPrompt("Ancien mot de passe");
    String oldPassword = scanner.nextLine();
    
    if (!oldPassword.equals(currentUser.getPassword())) {
        MenuHandler.showError("Mot de passe incorrect");
        waitForEnter();
        return;
    }
    
    MenuHandler.showPrompt("Nouveau mot de passe (min 6 caractères)");
    String newPassword = scanner.nextLine();
    
    if (newPassword.length() < 6) {
        MenuHandler.showError("Le mot de passe doit contenir au moins 6 caractères");
        waitForEnter();
        return;
    }
    
    currentUser.setPassword(newPassword);
    MenuHandler.showSuccess(" Mot de passe changé avec succès!");
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