
import ui.menu.MainMenu;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Démarrage du système de réservation...");
            
            // Create and start the main menu
            MainMenu mainMenu = new MainMenu();
            mainMenu.start();
            
        } catch (Exception e) {
            System.err.println("Une erreur inattendue s'est produite: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cleanup resources if needed
            System.out.println("Fermeture du système de réservation.");
        }
    }
}