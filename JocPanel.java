package cat.snakegame.interficie;

import cat.snakegame.entitats.Serp;
import cat.snakegame.entitats.Fruita;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JocPanel extends JPanel {

    private Serp serp;
    private Fruita fruita;
    private int punts;
    private boolean jocEnMarxa;
    private Timer timer;
    private Timer timerJoc;
    private Timer timerReinici;

    public JocPanel() {
        this.serp = new Serp();
        this.fruita = new Fruita();

        // Assignació CRÍTICA que faltava:
        this.serp.setFruita(this.fruita);

        this.punts = 0;
        this.jocEnMarxa = true;

        timer = new Timer(150, e -> {
            if (jocEnMarxa) {
                actualitzarJoc();
            }
        });
        timer.start();

        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                serp.canviarDireccio(e);
            }
        });

        // Debug inicial
        System.out.println("Posició inicial serp: " + serp.getCos().getFirst());
        System.out.println("Posició inicial fruita: " + fruita.getPosicio());
    }

    // Mètode que actualitza l'estat del joc: moviment de la serp, detecció de col·lisions
    private void actualitzarJoc() {
        if (!jocEnMarxa) return;

        serp.moure();

        // 1. Mort de la serp (es topa amb ella)
        if (serp.esTopaAmbElla()) {
            jocEnMarxa = true;
            timer.stop();
            JOptionPane.showMessageDialog(this, "Vigila amb el teu cos...!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 1.2 Mort de la serp (toca una paret)
        if (serp.haSortitDePantalla(getWidth(), getHeight())) {
            jocEnMarxa = false;
            timer.stop();
            JOptionPane.showMessageDialog(this, "Vigila amb les parets...!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 2. Comprovar si ha menjat
        if (serp.haMenjatFruita()) {
            // 3. Actualitzar puntuació
            punts += fruita.obtenirPunts();
            System.out.println("Punts actuals: " + punts);

            // 4. Generar nova fruita
            fruita.generarNova();

            // 5. Fer créixer la serp
            serp.creixer();
            System.out.println("Mida serp: " + serp.getCos().size());

            // 6. Assignar la nova fruita
            serp.setFruita(fruita);
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibuixar la serp
        serp.dibuixar(g);

        // Dibuixar la fruita
        fruita.dibuixar(g);

        // Si ha mort, destacar el punt de xoc
        if (serp.haMort()) {
            Point cap = serp.getCos().getFirst();
            g.setColor(Color.RED);
            g.fillRect(cap.x, cap.y, Serp.TAMANY, Serp.TAMANY);
        }

        // Dibuixar puntuació
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Punts: " + punts, 20, 20);
    }

    // Mètode que reinicia el joc. Inicialitza la serp, la fruita i la puntuació
    public void reiniciarJoc() {
        this.serp = new Serp();
        this.fruita = new Fruita();
        this.serp.setFruita(this.fruita);
        this.punts = 0;
        this.jocEnMarxa = true;

        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(150, e -> {
            if (jocEnMarxa) {
                actualitzarJoc();
            }
        });
        timer.start();

        requestFocusInWindow();
    }

    private void fiDelJoc(String missatge) {
        jocEnMarxa = false;
        timerJoc.stop();

        // Missatge amb HTML per millor format
        String htmlMsg = "<html><div style='text-align: center;'>"
                + "<h2 style='color: red;'>GAME OVER</h2>"
                + "<p style='font-size: 14pt;'>" + missatge + "</p>"
                + "<p>Puntuació final: <b style='font-size: 16pt;'>" + punts + "</b></p>"
                + "</div></html>";

        // Opcions per al diàleg
        Object[] opcions = {"Reiniciar Joc", "Sortir"};
        int eleccio = JOptionPane.showOptionDialog(
                this,
                htmlMsg,
                "Fi del Joc",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                opcions,
                opcions[0]
        );

        if (eleccio == JOptionPane.YES_OPTION) {
            reiniciarJoc();
        } else {
            System.exit(0);
        }
    }


    // Aturar el joc
    public void aturarJoc() {
        jocEnMarxa = false;
    }

    // Obtenir la puntuació actual
    public int getPunts() {
        return punts;
    }

    // Comprova si el joc està en marxa
    public boolean isJocEnMarxa() {
        return jocEnMarxa;
    }
}
