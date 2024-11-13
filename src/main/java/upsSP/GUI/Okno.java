package upsSP.GUI;

import upsSP.Nastroje.Konstanty;
import upsSP.VolbyTahu.IVolba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import static upsSP.Nastroje.Konstanty.*;

public class Okno extends JFrame {
    private CardLayout seznamStranek;
    private JPanel hlavniOkno;

    public Okno() {
        seznamStranek = new CardLayout();
        hlavniOkno = new JPanel(seznamStranek);
        OknoLogin login = new OknoLogin(this);
        OknoLobby lobby = new OknoLobby(this);
        OknoHra hra = new OknoHra(this);
        OknoZhodnoceniHry zhodnoceniHry = new OknoZhodnoceniHry(this);
        OknoCekani cekani = new OknoCekani(this);

        hlavniOkno.add("Login", login);
        hlavniOkno.add("Lobby", lobby);
        hlavniOkno.add("Cekani", cekani);
        hlavniOkno.add("Hra", hra);
        hlavniOkno.add("ZhodnoceniHry", zhodnoceniHry);

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
