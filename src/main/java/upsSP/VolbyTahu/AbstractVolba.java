package upsSP.VolbyTahu;

import upsSP.Nastroje.Konstanty;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class AbstractVolba {

    double mensiStrana;

    double realneX;

    double relaneY;

    int x;

    int y;

    public void nakresliVolbuObecne(Graphics2D g, String nazev, int x, int y, int pocetSloupcu, int pocetRadku, double sirka, double vyska) {
        // Načtení obrázku
        ImageIcon obrazek = new ImageIcon("src/main/resources/data/" + nazev);
        Image kamen = obrazek.getImage();

        if (kamen == null) {
            System.out.println("Obrázek nebyl nalezen!");
            return;
        }

        // Výpočet velikosti buňky
        double sirkaBunky = sirka / pocetSloupcu;
        double vyskaBunky = vyska / pocetRadku;

        mensiStrana = Math.min(sirkaBunky, vyskaBunky);

        double offsetMezi = 0.37 * mensiStrana;

        realneX = (x - 1) * mensiStrana - sirka / 2
                + Konstanty.OFFSET_Z_STRANY + (x - 1) * offsetMezi;
        relaneY = (y - 1) * mensiStrana - vyska / 2 + Konstanty.OFFSET_Z_HORA + (y - 1) * offsetMezi;;

        System.out.println("realneX: " + realneX + " relaneY: " + relaneY + " mensiStrana: " + mensiStrana);

        g.setColor(Color.WHITE);
        g.fill(new Rectangle2D.Double(realneX, relaneY, mensiStrana, mensiStrana));

        g.setColor(Color.CYAN);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString(nazev, (int)realneX + Konstanty.OFFSET_OBRAZKU, (int)relaneY + Konstanty.OFFSET_OBRAZKU);

        // Vykreslení obrázku na zadané souřadnice
        //g.drawImage(kamen, (int)realneX + Konstanty.OFFSET_OBRAZKU, (int)relaneY + Konstanty.OFFSET_OBRAZKU, null);

        nakresliHitbox(g);

    }

    public void nakresliHitbox(Graphics2D g) {
        g.setColor(Color.CYAN);
        g.setStroke(new BasicStroke(5));
        g.draw(new Rectangle2D.Double(realneX, relaneY, mensiStrana, mensiStrana));
    }

    public boolean jeZvoleno(double x, double y) {
        return (x >= realneX && x <= (realneX + mensiStrana)) && (y >= relaneY && y <= (relaneY + mensiStrana));
    }

    public void prepocitejRozmery(double sirka, double vyska, int pocetSloupcu, int pocetRadku) {
        double sirkaBunky = sirka / pocetSloupcu;
        double vyskaBunky = vyska / pocetRadku;
        mensiStrana = Math.min(sirkaBunky, vyskaBunky);
        double offsetMezi = 0.37 * mensiStrana;
        realneX = (x - 1) * mensiStrana - Konstanty.SIRKA_OKNA / 2
                + Konstanty.OFFSET_Z_STRANY + (x - 1) * offsetMezi;
        relaneY = (y - 1) * mensiStrana - Konstanty.VYSKA_OKNA / 2 + Konstanty.OFFSET_Z_HORA + (y - 1) * offsetMezi;;
    }
}
