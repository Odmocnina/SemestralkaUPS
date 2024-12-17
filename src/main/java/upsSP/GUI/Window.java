package upsSP.GUI;

import javax.swing.*;
import java.awt.*;

import static upsSP.Nastroje.Konstanty.*;

public class Window extends JFrame {
    private CardLayout widnowList;
    private JPanel mainWindow;

    OknoZhodnoceniHry zhodnoceniHry;
    GameWindow hra;

    public Window() {
        widnowList = new CardLayout();
        mainWindow = new JPanel(widnowList);
        LoginWindow login = new LoginWindow(this);
        //OknoLobby lobby = new OknoLobby(this);
        hra = new GameWindow(this);
        AfterTurnWindow poTahu = new AfterTurnWindow(this);
        zhodnoceniHry = new OknoZhodnoceniHry(this);
        OknoCekani cekani = new OknoCekani(this);

        mainWindow.add("Login", login);
        //mainWindow.add("Lobby", lobby);
        mainWindow.add("Cekani", cekani);
        mainWindow.add("Hra", hra);
        mainWindow.add("ZhodnoceniHry", zhodnoceniHry);
        mainWindow.add("PoTahu", poTahu);

        add(mainWindow);
        setTitle("Kamen nuzky papir");
        setSize(SIRKA_OKNA, VYSKA_OKNA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void setParamaters(String message) {
        zhodnoceniHry.setParametry(message);
    }

    public void zobrazHru(String sceneName) {
        System.out.println("Zobrazuju scenu " + sceneName);
        widnowList.show(mainWindow, sceneName);
    }

    public void aktializujLably() {
        hra.aktualizujLably();
    }
}
