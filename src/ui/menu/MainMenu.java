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
        this.reservationService = new ReservationService(this.hotelService);
        this.running = true;
    }

    public void start() {
        while (running) {
            if (currentUser == null) {
                MenuHandler.showWelcomeMenu();
                handleMainChoice();
            } else {
                handleUserMenu();
            }
        }
        scanner.close();
    }

    private void handleRegistration() {
        MenuHandler.showRegistrationForm();
        MenuHandler.showPrompt("Nom complet");
        String fullName = scanner.nextLine();
        MenuHandler.showPrompt("Email");
        String email = scanner.nextLine();
        MenuHandler.showPrompt("Mot de passe (min 6 caractères)");
        String password = scanner.nextLine();
        
        MenuHandler.showRoleSelection();
        String roleChoice = scanner.nextLine();
        String role = roleChoice.equals("1") ? "CLIENT" : "ADMIN";

        if (authService.register(fullName, email, password, role)) {
            MenuHandler.showSuccess(" Inscription réussie!");
        } else {
            MenuHandler.showError("Email déjà utilisé ou données invalides");
        }
        waitForEnter();
    }

    private void handleLogin() {
        MenuHandler.showLoginForm();
        MenuHandler.showPrompt("Email");
        String email = scanner.nextLine();
        MenuHandler.showPrompt("Mot de passe");
        String password = scanner.nextLine();

        if (authService.login(email, password)) {
            currentUser = authService.getCurrentUser(email);
            authService.updateSession(email);
            MenuHandler.showSuccess(" Connexion réussie!");
        } else {
            MenuHandler.showError("Email ou mot de passe incorrect");
        }
        waitForEnter();
    }

    private void handleMainChoice() {
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                handleRegistration();
                break;
            case "2":
                handleLogin();
                break;
            case "3":
                handleExit();
                break;
            default:
                MenuHandler.showError("Choix invalide");
        }
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

    private void handleUserMenu() {
        while (running && currentUser != null) {
            if ("ADMIN".equals(currentUser.getRole())) {
                MenuHandler.showAdminMenu(currentUser.getUsername());
            } else {
                MenuHandler.showClientMenu(currentUser.getUsername());
            }

            String choice = scanner.nextLine();
            boolean shouldContinue = "ADMIN".equals(currentUser.getRole()) ?
                    handleAdminChoice(choice) : handleClientChoice(choice);

            if (!shouldContinue) {
                break;
            }
        }
    }

    private boolean handleAdminMenu() {
        MenuHandler.showAdminMenu(currentUser.getUsername());
        String choice = scanner.nextLine();
        return handleAdminChoice(choice);
    }

    private boolean handleClientMenu() {
        MenuHandler.showClientMenu(currentUser.getUsername());
        String choice = scanner.nextLine();
        return handleClientChoice(choice);
    }

    // New methods for admin-specific functionality
    private void handleViewAllReservations() {
        System.out.println("\n=== Toutes les réservations ===");


        waitForEnter();
    }

    private void handleManageReservations() {
        System.out.println("\n=== Gestion des réservations ===");
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
            case "1":
                handleCreateHotel();
                return true;
            case "2":
                handleUpdateHotel();
                return true;
            case "3":
                handleDeleteHotel();
                return true;
            case "4":
                handleListHotels();
                return true;
            case "5":
                handleViewAllReservations();
                return true;
            case "6":
                handleManageReservations();
                return true;
            case "7":
                handleUpdateProfile();
                return true;
            case "8":
                handleChangePassword();
                return true;
            case "9":
                handleLogout();
                return false;
            default:
                MenuHandler.showError("Choix invalide");
                return true;
        }
    }

    private boolean handleClientChoice(String choice) {
        switch (choice) {
            case "1":
                handleListAvailableHotels();
                return true;
            case "2":
                handleCreateReservation();
                return true;
            case "3":
                handleCancelReservation();
                return true;
            case "4":
                handleReservationHistory();
                return true;
            case "5":
                handleUpdateProfile();
                return true;
            case "6":
                handleChangePassword();
                return true;
            case "7":
                handleLogout();
                return false;
            default:
                MenuHandler.showError("Choix invalide");
                return true;
        }
    }

    private void handleCreateHotel() {
        MenuHandler.showHotelFormHeader("CRÉATION D'HÔTEL");
        MenuHandler.showPrompt("Nom");
        String name = scanner.nextLine();
        MenuHandler.showPrompt("Adresse");
        String address = scanner.nextLine();
        MenuHandler.showPrompt("Nombre de chambres");
        int rooms = Integer.parseInt(scanner.nextLine());
        MenuHandler.showPrompt("Note (0-5)");
        float rating = Float.parseFloat(scanner.nextLine());
        MenuHandler.showPrompt("Prix par nuit");
        double price = Double.parseDouble(scanner.nextLine());

        Hotel hotel = hotelService.createHotel(name, address, rooms, rating, price);
        if (hotel != null) {
            MenuHandler.showSuccess(" Hôtel créé avec succès!");
        } else {
            MenuHandler.showError("Impossible de créer l'hôtel");
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
        List<Hotel> hotels = hotelService.getAllHotels();
        if (hotels.isEmpty()) {
            MenuHandler.showError("Aucun hôtel disponible");
        } else {
            displayHotelsList(hotels);
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
        System.out.println("\nListe des hôtels:");
        System.out.println(MenuHandler.DOUBLE_BORDER);
        for (Hotel hotel : hotels) {
            System.out.printf("ID: %s\nNom: %s\nAdresse: %s\nChambres disponibles: %d/%d\nNote: %.1f\nPrix: %.2fMAD\n",
                    hotel.getHotelId(), hotel.getName(), hotel.getAddress(),
                    hotel.getAvailableRooms(), hotel.getTotalRooms(),
                    hotel.getRating(), hotel.getPrice());
            System.out.println(MenuHandler.MENU_BORDER);
        }
    }

    private void handleCreateReservation() {
        MenuHandler.showHotelFormHeader("NOUVELLE RÉSERVATION");

        List<Hotel> hotels = hotelService.getAllHotels();
        if (hotels.isEmpty()) {
            MenuHandler.showError("Aucun hôtel disponible");
            waitForEnter();
            return;
        }

        displayHotelsList(hotels);

        MenuHandler.showPrompt("Entrez l'ID de l'hôtel");
        String hotelId = scanner.nextLine();

        Hotel selectedHotel = hotelService.getHotelById(hotelId);
        if (selectedHotel == null) {
            MenuHandler.showError("Hôtel non trouvé");
            waitForEnter();
            return;
        }
        System.out.println("DEBUG: Selected hotel price=" + selectedHotel.getPrice());
        MenuHandler.showPrompt("Nombre de nuits");
        int nights = Integer.parseInt(scanner.nextLine());
        MenuHandler.showPrompt("Nombre de chambres");
        int numberOfRooms = Integer.parseInt(scanner.nextLine());
        if (numberOfRooms > selectedHotel.getAvailableRooms()) {
            MenuHandler.showError("Nombre de chambres non disponible");
            waitForEnter();
            return;
        }
        double totalPrice = hotelService.calculateTotalPrice(hotelId, nights, numberOfRooms);
        System.out.println("DEBUG: Calculated totalPrice=" + totalPrice);
        String reservationId = UUID.randomUUID().toString();
        String checkInDate = "2025-09-20";
        String checkOutDate = "2025-09-" + (20 + nights);
        Reservation reservation = reservationService.createReservation(
                reservationId, hotelId, currentUser.getId(),
                checkInDate, checkOutDate, numberOfRooms,
                totalPrice, "Confirmed"
        );
        if (reservation != null) {
            MenuHandler.showSuccess(" Réservation créée avec succès!");
//            System.out.println("Prix total: " + totalPrice + "MAD");
        } else {
            MenuHandler.showError("Erreur lors de la création de la réservation");
        }
        waitForEnter();
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
        
        System.out.printf("%-36s | %-20s | %-10s | %-10s | %8d | %8.2fMAD | %s%n",
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
        boolean changed = false;
        if (!newUsername.trim().isEmpty()) {
            currentUser.setUsername(newUsername);
            changed = true;
        }
        if (!newEmail.trim().isEmpty()) {
            if (newEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                currentUser.setEmail(newEmail);
                changed = true;
            } else {
                MenuHandler.showError("Format d'email invalide");
            }
        }
        if (changed) {
            if (authService.updateUser(currentUser.getId(), currentUser)) {
                MenuHandler.showSuccess(" Profil mis à jour avec succès!");
            } else {
                MenuHandler.showError("Erreur lors de la mise à jour du profil");
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
        MenuHandler.showSuccess(" Déconnexion réussie");
        waitForEnter();
    }

    private void handleExit() {
        if (currentUser != null) {
            handleLogout();
        }
        MenuHandler.showSuccess(" Au revoir!");
        running = false;
    }

    private void waitForEnter() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }
}
