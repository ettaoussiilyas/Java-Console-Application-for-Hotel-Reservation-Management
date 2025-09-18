package ui.menu;

public class MenuHandler {
    private static final String MENU_BORDER = "====================================";
    private static final String DOUBLE_BORDER = "====================================================";

    // Menu principal (non connecté)
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

    // Menu utilisateur connecté
    public static void showUserMenu(String username, boolean isAdmin) {
        clearScreen();
        System.out.println(MENU_BORDER);
        System.out.println("Logged in as: " + username);
        System.out.println(MENU_BORDER);

        if (isAdmin) {
            System.out.println("1. Créer un hôtel");
            System.out.println("2. Modifier un hôtel");
            System.out.println("3. Supprimer un hôtel");
        }

        System.out.println(isAdmin ? "4" : "1" + ". Liste des hôtels");
        System.out.println(isAdmin ? "5" : "2" + ". Réserver une chambre");
        System.out.println(isAdmin ? "6" : "3" + ". Annuler une réservation");
        System.out.println(isAdmin ? "7" : "4" + ". Historique des réservations");
        System.out.println(isAdmin ? "8" : "5" + ". Modifier profil");
        System.out.println(isAdmin ? "9" : "6" + ". Changer mot de passe");
        System.out.println(isAdmin ? "10" : "7" + ". Se déconnecter");
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