package upsSP.GUI;

import javax.swing.*;
import java.awt.*;

import static upsSP.Nastroje.Konstanty.*;

public class Okno extends JFrame {
    private CardLayout seznamStranek;
    private JPanel hlavniOkno;

    public Okno() {
        seznamStranek = new CardLayout();
        hlavniOkno = new JPanel(seznamStranek);
        OknoLogin login = new OknoLogin(this);
        OknoHra hra = new OknoHra();

        hlavniOkno.add("Login", login);
        hlavniOkno.add("Hra", hra);

//        mainPanel.add(new LoginPanel(this), "Login");
//        mainPanel.add(new LobbyPanel(), "Lobby");
//        mainPanel.add(new QueuePanel(), "Queue");
//        mainPanel.add(new GamePanel(), "Game");

        add(hlavniOkno);
        setTitle("Kamen nuzky papir");
        setSize(SIRKA_OKNA, VYSKA_OKNA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    public void zobrazHru(String sceneName) {
        seznamStranek.show(hlavniOkno, sceneName);
    }
}
