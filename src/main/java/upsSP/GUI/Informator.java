package upsSP.GUI;

import javax.swing.*;

import upsSP.Nastroje.GameState;
import upsSP.Nastroje.States;

public class Informator {
    static Window window;
    static Informator informator;
    private Informator(Window window) {
        this.window = window;
    }

    public static Informator getInstance(Window window) {
        if (informator == null) {
            informator = new Informator(window);
        }
        return informator;
    }

    public void informAboutFuckedConnection() {
        window.zobrazHru("fuckedConnection");
    }

    public void informAboutOpponentsFuckedConnection(int level) {
        if (level == 1) {
            window.zobrazHru("opponentsFuckedConnection");
        } else {
            GameEvaluationScreen.aktualizujLably();
            GameWindow.updateLabes();
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Opponent má skurvenej net", "Chyba",
                        JOptionPane.ERROR_MESSAGE);
                window.zobrazHru("wait");
            });
        }
    }

    public void informAboutTimeout() {
        System.out.println("Timeout");
        GameEvaluationScreen.aktualizujLably();
        GameWindow.updateLabes();
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, "Maš skurvenej net", "Chyba",
                    JOptionPane.ERROR_MESSAGE);
            window.zobrazHru("login");
        });
    }

    public void informAboutInvalidMessage() {
        GameEvaluationScreen.aktualizujLably();
        GameWindow.updateLabes();
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, "Co mi to posíláš vole", "Chyba",
                    JOptionPane.ERROR_MESSAGE);
            window.zobrazHru("login");
        });
    }

    public int repairGame() {
        window.zobrazHru(window.StateToString(GameState.getInstance().stateOfGame));
        return 0;
    }
}
