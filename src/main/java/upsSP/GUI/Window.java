package upsSP.GUI;

import upsSP.Nastroje.GameState;
import upsSP.Nastroje.States;

import javax.swing.*;
import java.awt.*;

import static upsSP.Nastroje.Constants.*;

/************************************************************
 * Trida reprezentujici hlavni okno aplikace
 * s funkcionalitou pro spravu ruznych obrazovek
 *
 * @author  Michael Hladky
 * @version 1.00.00
 */
public class Window extends JFrame {

    /**Layout pro spravu jednotlivych obrazovek**/
    private CardLayout widnowList;

    /**Hlavni panel obsahujici vsechny obrazovky**/
    private JPanel mainWindow;

    /**Obrazovka pro vyhodnoceni hry**/
    GameEvaluationScreen gameEvaluation;

    /**Obrazovka samotne hry**/
    GameWindow game;

    /**
     * Konstruktor tridy Window
     * Inicializuje hlavni okno a pridava jednotlive obrazovky
     */
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
        InBetweenWindow help = new InBetweenWindow(this);
        FuckedConnectionWindow fuckedConnection = new FuckedConnectionWindow(this);
        FuckedConnectionOpponentScreen fuckedConnectionOpponentScreen = new FuckedConnectionOpponentScreen(this);


        mainWindow.add("login", login);
        //mainWindow.add("Lobby", lobby);
        mainWindow.add("wait", waiting);
        mainWindow.add("game", game);
        mainWindow.add("gameJudgement", gameEvaluation);
        mainWindow.add("afterPlay", afterTurn);
        mainWindow.add("help", help);
        mainWindow.add("fuckedConnection", fuckedConnection);
        mainWindow.add("opponentsFuckedConnection", fuckedConnectionOpponentScreen);

        add(mainWindow);
        setTitle("Kamen nuzky papir tapir spock");
        setSize(WIDTH_STOCK, HEIGHT_STOCK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Nastavi parametry hry na zaklade zpravy
     * @param message zprava obsahujici stav hry
     */
    public void setParamaters(String message) {
        gameEvaluation.setGameState(message);
    }


    /**
     * Zobrazi obrazovku hry podle zadane sceny
     * @param sceneName nazev sceny, kterou chceme zobrazit
     */
    public void zobrazHru(String sceneName) {
        GameState.getInstance().setState(StringToState(sceneName));
        widnowList.show(mainWindow, sceneName);
    }

    /**
     * Prevadi retezec na stav hry
     * @param state retezec reprezentujici stav
     * @return stav hry odpovidajici retezci
     */
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

    /**
     * Prevadi stav hry na retezec
     * @param state stav hry
     * @return retezec odpovidajici stavu hry
     */
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

    /**Aktualizuje popisky ve hre**/
    public void aktializujLably() {
        game.updateLabes();
    }
}
