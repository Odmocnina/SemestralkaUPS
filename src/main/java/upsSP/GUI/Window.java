package upsSP.GUI;

import upsSP.Nastroje.GameState;
import upsSP.Nastroje.States;

import javax.swing.*;
import java.awt.*;

import static upsSP.Nastroje.Constants.*;

public class Window extends JFrame {
    private CardLayout widnowList;
    private JPanel mainWindow;

    GameEvaluationScreen gameEvaluation;
    GameWindow game;

    public Window() {
        Informator.getInstance(this); //setnuti informatora
        widnowList = new CardLayout();
        mainWindow = new JPanel(widnowList);
        LoginWindow login = new LoginWindow(this);
        //OknoLobby lobby = new OknoLobby(this);
        game = new GameWindow(this);
        AfterTurnWindow afterTurn = new AfterTurnWindow(this);
        gameEvaluation = new GameEvaluationScreen(this);
        WaitingScreen waiting = new WaitingScreen(this);
        AfterLoginWindow afterLogin = new AfterLoginWindow(this);
        FuckedConnectionWindow fuckedConnection = new FuckedConnectionWindow(this);

        mainWindow.add("login", login);
        //mainWindow.add("Lobby", lobby);
        mainWindow.add("wait", waiting);
        mainWindow.add("game", game);
        mainWindow.add("gameJudgement", gameEvaluation);
        mainWindow.add("afterPlay", afterTurn);
        mainWindow.add("afterLogin", afterLogin);
        mainWindow.add("fuckedConnection", fuckedConnection);

        add(mainWindow);
        setTitle("Kamen nuzky papir tapir spock");
        setSize(WIDTH_STOCK, HEIGHT_STOCK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void setParamaters(String message) {
        gameEvaluation.setGameState(message);
    }

    public void zobrazHru(String sceneName) {
        GameState.getInstance().setState(StringToState(sceneName));
        widnowList.show(mainWindow, sceneName);
    }

    public States StringToState(String state) {
        switch (state) {
            case "login":
                return States.LOGIN;
            case "wait":
                return States.QUEUE;
            case "game":
                return States.GAME;
            case "afterPlay":
                return States.AFTERTURN;
            case "gameJudgement":
                return States.GAMEJUDGEMENT;
            //case "fuckedConnection":
                //return States.FUCKEDCONNECTION;
        }
        return null;
    }

    public String StateToString(States state) {
        if (state == null) {
            return null;
        }
        switch (state) {
            case LOGIN:
                return "login";
            case QUEUE:
                return "wait";
            case GAME:
                return "game";
            case AFTERTURN:
                return "afterPlay";
            case GAMEJUDGEMENT:
                return "gameJudgement";
            case FUCKEDCONNECTION:
                return "fuckedConnection";
            default:
                return null; // nebo třeba vyhození výjimky, pokud nečekáš jiný stav
        }
    }

    public void aktializujLably() {
        game.updateLabes();
    }
}
