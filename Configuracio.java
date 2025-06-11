package cat.snakegame.utils;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Classe que gestiona la configuració global del joc.
 * Permet carregar paràmetres de configuració des de fitxers o per línia de comandes.
 */
public class Configuracio {

    private static Configuracio instance;
    private Properties prop;

    // Variables per a configuracions globals (exemples)
    private String mode;
    private int velocitat;

    /**
     * Constructor privat per al patró Singleton.
     * Carrega les configuracions des d'un fitxer.
     */
    private Configuracio() {
        prop = new Properties();
        carregarConfiguracio();
    }

    /**
     * Obté la instància única de Configuracio.
     *
     * @return La instància única de Configuracio.
     */
    public static Configuracio getInstance() {
        if (instance == null) {
            instance = new Configuracio();
        }
        return instance;
    }

    /**
     * Carrega la configuració des d'un fitxer o configura valors per defecte.
     */
    private void carregarConfiguracio() {
        try (FileInputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
            mode = prop.getProperty("mode", "normal"); // Mode per defecte
            velocitat = Integer.parseInt(prop.getProperty("velocitat", "100"));
        } catch (IOException e) {
            System.out.println("No s'ha pogut carregar el fitxer de configuració. Usant valors per defecte.");
            mode = "normal";
            velocitat = 100; // Velocitat per defecte
        }
    }

    /**
     * Carrega el mode desitjat del joc (p. ex., normal, difícil).
     *
     * @param mode Mode del joc.
     */
    public void carregarMode(String mode) {
        this.mode = mode;
    }

    /**
     * Retorna el mode actual del joc.
     *
     * @return El mode actual del joc.
     */
    public String getMode() {
        return mode;
    }

    /**
     * Retorna la velocitat del joc.
     *
     * @return La velocitat del joc.
     */
    public int getVelocitat() {
        return velocitat;
    }
}
