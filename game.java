package cat.snakegame.main;

import cat.snakegame.interficie.JocFinestra;
import cat.snakegame.utils.Configuracio;

import javax.swing.SwingUtilities;

/**
 * Classe principal del joc Snake.
 * Inicia la configuració global i llança la finestra gràfica.
 */
public class game {

    /**
     * Punt d'entrada del joc Snake.
     * Crea la configuració inicial i arrenca la finestra de joc.
     *
     * @param args Arguments de línia de comandes (per a futurs usos: dificultat, mode, etc.).
     */
    public static void main(String[] args) {
        inicialitzarConfiguracioGlobal(args);

        SwingUtilities.invokeLater(() -> {
            JocFinestra finestra = new JocFinestra("Snake Game");
            finestra.setVisible(true);
        });
    }

    /**
     * Mètode que prepara la configuració global del joc abans d'iniciar la UI.
     *
     * @param args Arguments de línia de comandes.
     */
    private static void inicialitzarConfiguracioGlobal(String[] args) {
        // Aquesta classe podria llegir fitxers, arguments, o aplicar preferències
        Configuracio config = Configuracio.getInstance();

        if (args.length > 0) {
            config.carregarMode(args[0]); // p. ex. "normal", "hard", "zen", etc.
        } else {
            config.carregarMode("normal");
        }

        // Pot carregar mides de panell, velocitat, sons, tema visual, etc.
        System.out.println("Mode carregat: " + config.getMode());
    }
}
