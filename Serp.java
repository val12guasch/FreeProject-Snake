package cat.snakegame.entitats;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

/**
 * Classe que representa la serp del joc.
 * Gestiona el moviment, creixement i dibuix de la serp.
 */
public class Serp {

    private LinkedList<Point> cos;
    private String direccio;
    private boolean haMort;
    private Fruita fruita;

    public static final int TAMANY = 20; // Tamany d'un segment de la serp

    // Constructor per inicialitzar la serp
    public Serp() {
        cos = new LinkedList<>();
        cos.add(new Point(100, 100));  // Inici de la serp a una posició central
        direccio = "DRETA";  // Direcció inicial
        haMort = false;
        fruita = null;  // Inicialitzem a null (s'haurà d'assignar després)
    }

    /**
     * Moure la serp segons la direcció actual.
     * Si la serp menja fruita, creix un segment.
     */
    public void moure() {
        if (haMort) return;

        Point cap = cos.peekFirst(); // La primera posició de la cua és la capçalera
        Point novaPosicio = new Point(cap);

        // Moure la capçalera en funció de la direcció
        switch (direccio) {
            case "DRETA":
                novaPosicio.x += TAMANY;
                break;
            case "ESQUERRA":
                novaPosicio.x -= TAMANY;
                break;
            case "AMUNT":
                novaPosicio.y -= TAMANY;
                break;
            case "AVALL":
                novaPosicio.y += TAMANY;
                break;
        }

        // Afegir la nova posició al principi de la llista (la capçalera)
        cos.addFirst(novaPosicio);

        // Si no hem menjat fruita, eliminar l'últim segment de la cua
        if (!haMenjatFruita()) {
            cos.removeLast();
        }
    }

    /**
     * Canvia la direcció de la serp.
     *
     * @param e L'event de la tecla pressionada.
     */
    public void canviarDireccio(KeyEvent e) {
        if (haMort) return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (!direccio.equals("AVALL")) direccio = "AMUNT";
                break;
            case KeyEvent.VK_DOWN:
                if (!direccio.equals("AMUNT")) direccio = "AVALL";
                break;
            case KeyEvent.VK_LEFT:
                if (!direccio.equals("DRETA")) direccio = "ESQUERRA";
                break;
            case KeyEvent.VK_RIGHT:
                if (!direccio.equals("ESQUERRA")) direccio = "DRETA";
                break;
        }
    }

    /**
     * Comprova si la serp ha menjat fruita.
     *
     * @return Cert si ha menjat fruita, fals si no.
     */
    public boolean haMenjatFruita() {
        if (this.fruita == null || this.fruita.getPosicio() == null) {
            return false;
        }

        Point cap = this.cos.getFirst();
        Point posFruita = this.fruita.getPosicio();

        // Comprovació EXACTA de coordenades (imprescindible)
        boolean colisio = cap.x == posFruita.x && cap.y == posFruita.y;

        if (colisio) {
            System.out.println("MENJADA! Serp: [" + cap.x + "," + cap.y + "] " +
                    "Fruita: [" + posFruita.x + "," + posFruita.y + "]");
        }
        return colisio;
    }
    /**
     * Dibuixa la serp.
     *
     * @param g L'objecte Graphics per dibuixar.
     */
    public void dibuixar(Graphics g) {
        // Dibuixar tot el cos verd primer
        g.setColor(Color.GREEN);
        for (Point segment : cos) {
            g.fillRect(segment.x, segment.y, TAMANY, TAMANY);
        }

        // Dibuixar el cap blau sobre el primer segment
        if (!cos.isEmpty()) {
            Point cap = cos.getFirst();

            // Cap amb gradient
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gradient = new GradientPaint(
                    cap.x, cap.y, new Color(0, 100, 255), // Blau fosc
                    cap.x + TAMANY, cap.y + TAMANY, Color.CYAN, // Blau clar
                    true // Repetir
            );
            g2d.setPaint(gradient);
            g2d.fillRect(cap.x, cap.y, TAMANY, TAMANY);

            // Contorn
            g2d.setColor(Color.WHITE);
            g2d.drawRect(cap.x, cap.y, TAMANY, TAMANY);
        }
    }

    /**
     * Verifica si la serp ha topat amb ella mateixa.
     *
     * @return Cert si la serp ha topat amb ella mateixa, fals si no.
     */
    public boolean haSortitDePantalla(int amplePanel, int altPanel) {
        Point cap = cos.getFirst();
        boolean xoca = cap.x < 0 || cap.x >= amplePanel || cap.y < 0 || cap.y >= altPanel;
        if (xoca) {
            haMort = true;
        }
        return haMort;
    }

    public boolean esTopaAmbElla() {
        if (cos.size() < 5) return false; // No pot xocar amb si mateixa si és massa curta

        Point cap = cos.getFirst();

        // Comprovar tots els segments excepte el cap (primer element)
        for (int i = 4; i < cos.size(); i++) {  // Comença des del 4t segment
            if (cap.equals(cos.get(i))) {
                haMort = true;
                System.out.println("Ups, vigila per on vas!");
                return true;
            }
        }
        return false;
    }

    public void reiniciar() {
        this.cos = new LinkedList<>();
        this.cos.add(new Point(100, 100));
        this.direccio = "DRETA";
        this.haMort = false;
        this.fruita = null;
    }

    /**
     * Verifica si la serp ha sortit dels límits de la pantalla.
     *
     * @return Cert si la serp ha sortit de la pantalla, fals si no.
     */
    public boolean haSortitDePantalla() {
        Point cap = cos.peekFirst();
        return cap.x < 0 || cap.y < 0 || cap.x >= 600 || cap.y >= 600;
    }

    /**
     * Afegeix un segment extra a la cua quan la serp menja fruita.
     */
    public void creixer() {
        cos.addLast(new Point(cos.peekLast()));  // Afegir un segment al final
    }

    /**
     * Retorna el cos de la serp.
     *
     * @return La llista de punts del cos de la serp.
     */
    public LinkedList<Point> getCos() {
        return cos;
    }

    /**
     * Assigna una fruita a la serp.
     *
     * @param fruita La fruita que podrà menjar la serp.
     */
    public void setFruita(Fruita fruita) {
        this.fruita = fruita;
    }

    /**
     * Retorna si la serp ha mort.
     *
     * @return Cert si la serp ha mort, fals si no.
     */
    public boolean haMort() {
        return haMort;
    }

    public Fruita getFruita() {
        return this.fruita;
    }
}