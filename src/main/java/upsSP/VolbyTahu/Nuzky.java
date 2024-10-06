package upsSP.VolbyTahu;

import upsSP.Nastroje.Konstanty;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Nuzky extends AbstractVolba implements IVolba {

    public Nuzky() {

    }

    public void nakresliVolbuO(Graphics2D g, int x, int y, int pocetSloupcu, int pocetRadku) {
        // Načtení obrázku
        /*ImageIcon obrazek = new ImageIcon("src/main/resources/data/kamen.png"); // cesta k tvému obrázku kamene
        Image kamen = obrazek.getImage();

        if (kamen == null) {
            System.out.println("Obrázek nebyl nalezen!");
            return;
        }*/

        //System.out.println(Konstanty.VYSKA_OKNA + " " + Konstanty.SIRKA_OKNA + " " + pocetSloupcu + " " + pocetRadku);

        System.out.println();

        // Výpočet velikosti buňky
        double sirkaBunky = Konstanty.SIRKA_OKNA / pocetSloupcu;
        double vyskaBunky = Konstanty.VYSKA_OKNA / pocetRadku;

        double mensiStrana = Math.min(sirkaBunky, vyskaBunky);

        //Image zmensenyKamen = kamen.getScaledInstance((int)mensiStrana, (int)mensiStrana, Image.SCALE_SMOOTH);

        //ImageIcon zmensenyKamenIcon = new ImageIcon(zmensenyKamen);

        //System.out.println("Souřadnice kamene: " + realX + ", " + realY);

        //System.out.println(realX + " " + realY + " " + mensiStrana + " " + mensiStrana);

        double offsetMezi = 0.37 * mensiStrana;

        double realneX = (x - 1) * mensiStrana - Konstanty.SIRKA_OKNA / 2
                + Konstanty.OFFSET_Z_STRANY + (x - 1) * offsetMezi;
        double relaneY = (y - 1) * mensiStrana - Konstanty.VYSKA_OKNA / 2 + Konstanty.OFFSET_Z_HORA + (y - 1) * offsetMezi;;
        System.out.println(realneX + " " + relaneY + " " + mensiStrana);

        g.setColor(Color.WHITE);

        g.fill(new Rectangle2D.Double(realneX, relaneY, mensiStrana, mensiStrana));

        g.setColor(Color.CYAN);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("Nůžky", (float)(realneX + mensiStrana / 4), (float)(relaneY + mensiStrana / 2));

        //double offsetObrazku = mensiStrana / 10;

        //kamen = kamen.getScaledInstance((int)(mensiStrana - 2 * offsetObrazku), (int)(mensiStrana - 2 * offsetObrazku), Image.SCALE_SMOOTH);

        // Vykreslení obrázku na zadané souřadnice
        //g.drawImage(kamen, (int)realneX, (int)relaneY, null);

    }

    public void nakresliVolbu(Graphics2D g, int x, int y, int pocetSloupcu, int pocetRadku) {
        super.nakresliVolbuObecne(g, "Nůžky", x, y, pocetSloupcu, pocetRadku);

    }

    public int ziskejHodnotu() {
        return Konstanty.HODNOTA_NUZKY;
    }
}
