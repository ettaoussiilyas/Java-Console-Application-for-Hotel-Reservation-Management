package ui.menu;

public class MenuHandler {
    public static void showHotelFormHeader(String title) {
        clearScreen();
        System.out.println(DOUBLE_BORDER);
        System.out.println("           " + title);
        System.out.println(DOUBLE_BORDER);
    }

    // Add this constant at the top of the class
    public static final String MENU_BORDER = "====================================";
    public static final String DOUBLE_BORDER = "====================================================";

    // Welcome Menu (not logged in)
    public static void showWelcomeMenu() {
        clearScreen();
        System.out.println(DOUBLE_BORDER);
        System.out.println("          SYSTÈME DE RÉSERVATION D'HÔTELS");
        System.out.println(DOUBLE_BORDER);
        System.out.println("\n1. S'inscrire (Register)");
        System.out.println("2. Se connecter (Login)");
        System.out.println("3. Quitter (Exit)");
        System.out.println(MENU_BORDER);
        System.out.print("Votre choix: ");
    }

    // Admin Menu
    public static void showAdminMenu(String username) {
        clearScreen();
        System.out.println(DOUBLE_BORDER);
        System.out.println("    MENU ADMINISTRATEUR - Connecté en tant que: " + username);
        System.out.println(DOUBLE_BORDER);
        System.out.println("\n=== Gestion des Hôtels ===");
        System.out.println("1. Créer un hôtel");
        System.out.println("2. Modifier un hôtel");
        System.out.println("3. Supprimer un hôtel");
        System.out.println("4. Liste des hôtels");
        System.out.println("\n=== Réservations ===");
        System.out.println("5. Consulter toutes les réservations");
        System.out.println("6. Gérer les réservations");
        System.out.println("\n=== Compte ===");
        System.out.println("7. Modifier profil");
        System.out.println("8. Changer mot de passe");
        System.out.println("9. Se déconnecter");
        System.out.println(MENU_BORDER);
        System.out.print("Votre choix: ");
    }

    // Client Menu
    public static void showClientMenu(String username) {
        clearScreen();
        System.out.println(DOUBLE_BORDER);
        System.out.println("    MENU CLIENT - Connecté en tant que: " + username);
        System.out.println(DOUBLE_BORDER);
        System.out.println("\n=== Réservations ===");
        System.out.println("1. Liste des hôtels disponibles");
        System.out.println("2. Réserver une chambre");
        System.out.println("3. Annuler une réservation");
        System.out.println("4. Mon historique de réservations");
        System.out.println("\n=== Compte ===");
        System.out.println("5. Modifier mon profil");
        System.out.println("6. Changer mot de passe");
        System.out.println("7. Se déconnecter");
        System.out.println(MENU_BORDER);
        System.out.print("Votre choix: ");
    }

    // Messages d'aide et de validation
    public static void showRegistrationForm() {
        clearScreen();
        System.out.println(MENU_BORDER);
        System.out.println("            INSCRIPTION");
        System.out.println(MENU_BORDER);
    }

    public static void showLoginForm() {
        clearScreen();
        System.out.println(MENU_BORDER);
        System.out.println("            CONNEXION");
        System.out.println(MENU_BORDER);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void showError(String message) {
        System.out.println("\nErreur: " + message);
    }

    public static void showSuccess(String message) {
        System.out.println("\nSuccess" + message);
    }

    public static void showPrompt(String message) {
        System.out.print(message + ": ");
    }

    public static void showRoleSelection(){
        System.out.println("\nChoisissez votre role: ");
        System.out.println("1. Client");
        System.out.println("2. Admin");
        System.out.print("Votre choix (1 ou 2): ");
    }
}