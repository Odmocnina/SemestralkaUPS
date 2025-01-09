package upsSP.GUI;

import javax.swing.*;

import upsSP.Nastroje.GameState;
import upsSP.Nastroje.States;

/************************************************************
 * Trida zajistujici komunikaci mezi ruznymi castmi aplikace
 * a zobrazovani informaci uzivateli o stavu hry.
 *
 * @author  Michael Hladky
 * @version 1.00.00
 */
public class Informator {

    /**Reference na hlavni okno aplikace**/
    static Window window;

    /**Singleton instance tridy Informator**/
    static Informator informator;

    /**
     * Privatni konstruktor pro inicializaci informatora
     * @param window hlavni okno aplikace
     */
    private Informator(Window window) {
        this.window = window;
    }

    /**
     * Vraci singleton instanci tridy Informator
     * @param window hlavni okno aplikace
     * @return instance tridy Informator
     */
    public static Informator getInstance(Window window) {
        if (informator == null) {
            informator = new Informator(window);
        }
        return informator;
    }

    /**
     * Informuje o chybe spojeni
     * Zobrazi obrazovku "fuckedConnection"
     */
    public void informAboutFuckedConnection() {
        window.zobrazHru("fuckedConnection");
    }

    /**
     * Informuje o chybe spojeni protivnika
     */
    public void informAboutOpponentsFuckedConnection(int level) {
        if (level == 1) {
            window.zobrazHru("opponentsFuckedConnection");
        } else {
            GameEvaluationScreen.aktualizujLably();
            GameWindow.updateLabes();
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Opponent nepřijímal moc dlouho", "Chyba",
                        JOptionPane.ERROR_MESSAGE);
                window.zobrazHru("wait");
            });
        }
    }

    /**
     * Informuje o domrdanym spojeni protivnika
     */
    public void informAboutTimeout() {
        System.out.println("Timeout");
        GameEvaluationScreen.aktualizujLably();
        GameWindow.updateLabes();
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, "Moc dlouho nepříjem", "Chyba",
                    JOptionPane.ERROR_MESSAGE);
            window.zobrazHru("login");
        });
    }

    /**
     * Informuje o navalidni zprave
     */
    public void informAboutInvalidMessage() {
        GameEvaluationScreen.aktualizujLably();
        GameWindow.updateLabes();
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, "Nevladní", "Chyba",
                    JOptionPane.ERROR_MESSAGE);
            window.zobrazHru("login");
        });
    }

    /**
     * Informuje o tom ze se jde do loginu
     */
    public void informToShow() {
        window.zobrazHru("login");
    }

    /**
     * opraveni hry pri reconnectu
     * @return vraci vzdy nulu lol
     */
    public int repairGame() {
        window.zobrazHru(window.StateToString(GameState.getInstance().stateOfGame));
        return 0;
    }
}
