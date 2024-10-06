package upsSP.GUI;

import upsSP.Nastroje.Konstanty;

import javax.swing.*;
import java.awt.*;

public class Pozadi extends JPanel {

    private Image pozadiObrazek;

    public Pozadi(String nazev) {
        // Načtení obrázku jako pozadí
        pozadiObrazek = new ImageIcon("src/main/resources/data/" + nazev).getImage();
        pozadiObrazek = pozadiObrazek.getScaledInstance(Konstanty.SIRKA_OKNA, Konstanty.VYSKA_OKNA, Image.SCALE_SMOOTH); // Změňte 400 a 300 na požadovanou velikost
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Vykreslení obrázku na pozadí
        g.drawImage(pozadiObrazek, 0, 0, getWidth(), getHeight(), this);
    }
}
