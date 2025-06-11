package cat.snakegame.entitats;

import java.awt.*;
import java.util.Random;

/**
 * Classe que representa la fruita al joc.
 * Pot tenir diferents tipus amb efectes diferents sobre la puntuació.
 */
public class Fruita {

    public static final int TAMANY = 20; // Tamany de la fruita

    private Point posicio;
    private String tipus; // Tipus de fruita: normal, especial, negativa
    private Serp serp;

    /**
     * Constructor que genera una fruita en una posició aleatòria.
     */
    public Fruita() {
        this.posicio = new Point(0, 0);  // Inicialitzem abans de generar
        this.tipus = "normal";
        generarPosicio();  // Ara genera una posició vàlida
    }

    public void generarPosicio() {
        Random rand = new Random();

        // Definim zona central (70% de l'ample i alt)
        int margeX = (int)(0.15 * 30 * TAMANY);  // 15% de marge a cada costat
        int margeY = (int)(0.15 * 30 * TAMANY);

        // Generem posicions dins la zona central
        int x = margeX + rand.nextInt(30 * TAMANY - 2 * margeX);
        int y = margeY + rand.nextInt(30 * TAMANY - 2 * margeY);

        // Ajustem a la graella (múltiples de TAMANY)
        this.posicio = new Point(
                (x / TAMANY) * TAMANY,
                (y / TAMANY) * TAMANY
        );

        // Debug
        System.out.println("Nova fruita generada a: " + posicio + " (zona central)");
    }

    /**
     * Dibuixa la fruita al panell.
     *
     * @param g L'objecte Graphics per dibuixar.
     */
    public void dibuixar(Graphics g) {
        switch (tipus) {
            case "normal":
                g.setColor(Color.GREEN);
                break;
            case "especial":
                g.setColor(Color.YELLOW);
                break;
            case "negativa":
                g.setColor(Color.RED);
                break;
        }
        g.fillRect(posicio.x, posicio.y, TAMANY, TAMANY);
    }

    /**
     * Retorna la posició de la fruita.
     *
     * @return La posició de la fruita.
     */
    public Point getPosicio() {
        return posicio;
    }

    /**
     * Retorna el tipus de fruita.
     *
     * @return Tipus de fruita.
     */
    public String getTipus() {
        return tipus;
    }

    /**
     * Genera una nova fruita amb una nova posició i tipus aleatori.
     */
    public void generarNova() {
        Random rand = new Random();
        // Assegurar múltiples de TAMANY (20)
        this.posicio = new Point(
                rand.nextInt(30) * TAMANY,  // 0-580
                rand.nextInt(30) * TAMANY   // 0-580
        );
        System.out.println("Nova fruita a: " + posicio);
    }

    /**
     * Retorna els punts que suma la fruita en funció del tipus.
     * - Fruit normal: +1 punt
     * - Fruit especial: +2 punts
     * - Fruit negativa: -1 punt
     */
    public int obtenirPunts() {
        switch (tipus) {
            case "especial":
                return 2; // Fruit especial dona 2 punts
            case "negativa":
                return -1; // Fruit negativa resta 1 punt
            default:
                return 1; // Fruit normal dona 1 punt
        }
    }
}
