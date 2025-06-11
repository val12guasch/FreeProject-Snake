package cat.snakegame.interficie;

import javax.swing.*;
import cat.snakegame.interficie.JocPanel;

public class JocFinestra extends JFrame {

    private JocPanel jocPanel;

    public JocFinestra(String snakeGame) {
        jocPanel = new JocPanel();
        add(jocPanel);

        setTitle("Joc Snake");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centra la finestra a la pantalla
        setResizable(false);
        setVisible(true);
    }

    // Iniciar el joc
    public void iniciarJoc() {
        jocPanel.reiniciarJoc();  // Reiniciar el joc des de zero
    }

    // Aturar el joc
    public void aturarJoc() {
        jocPanel.aturarJoc();  // Aturar el joc (pot ser una lògica per mostrar una pantalla de final)
        JOptionPane.showMessageDialog(this, "El joc ha acabat! Puntuació final: " + jocPanel.getPunts(), "Fi del joc", JOptionPane.INFORMATION_MESSAGE);
    }
}
