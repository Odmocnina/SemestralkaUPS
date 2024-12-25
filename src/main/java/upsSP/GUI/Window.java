package upsSP.GUI;

import javax.swing.*;
import java.awt.*;

import static upsSP.Nastroje.Constants.*;

public class Window extends JFrame {
    private CardLayout widnowList;
    private JPanel mainWindow;

    GameEvaluationScreen gameEvaluation;
    GameWindow game;

    public Window() {
        widnowList = new CardLayout();
        mainWindow = new JPanel(widnowList);
        LoginWindow login = new LoginWindow(this);
        //OknoLobby lobby = new OknoLobby(this);
        game = new GameWindow(this);
        AfterTurnWindow afterTurn = new AfterTurnWindow(this);
        gameEvaluation = new GameEvaluationScreen(this);
        WaitingScreen waiting = new WaitingScreen(this);
        AfterLoginWindow afterLogin = new AfterLoginWindow(this);

        mainWindow.add("Login", login);
        //mainWindow.add("Lobby", lobby);
        mainWindow.add("Cekani", waiting);
        mainWindow.add("Game", game);
        mainWindow.add("ZhodnoceniHry", gameEvaluation);
        mainWindow.add("PoTahu", afterTurn);
        mainWindow.add("poLoginu", afterLogin);

        add(mainWindow);
        setTitle("Kamen nuzky papir");
        setSize(WIDTH_STOCK, HEIGHT_STOCK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void setParamaters(String message) {
        gameEvaluation.setGameState(message);
    }

    public void zobrazHru(String sceneName) {
        widnowList.show(mainWindow, sceneName);
    }

    public void aktializujLably() {
        game.updateLabes();
    }
}
