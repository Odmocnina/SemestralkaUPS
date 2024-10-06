package upsSP.GUI;

import upsSP.Nastroje.Konstanty;
import upsSP.VolbyTahu.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class OknoHra extends JPanel {

    private JLabel rockLabel, paperLabel, scissorsLabel, tapirLabel, spockLabel;

    int poctetRadku = 3;

    int pocetSloupcu = 3;

    public OknoHra() {
    }

    private void prepareGraphics(Graphics2D g2d) {
        // Anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get the size of the panel
        float width = getWidth();
        float height = getHeight();

        // Translate the origin to the center of the panel
        g2d.translate(width / 2, height / 2);

        // Scale based on the size of the window
        double scaleX = width / Konstanty.SIRKA_OKNA;
        double scaleY = height / Konstanty.VYSKA_OKNA;
        double scale = Math.min(scaleX, scaleY);
        g2d.scale(scale, scale);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        prepareGraphics(g2);
        setBackground(new Color(0, 128, 0));
        //setLayout(new GridLayout(poctetRadku, pocetSloupcu));
        IVolba[] volby = new IVolba[Konstanty.POCET_MOZNOSTI + 1];
        IVolba kamen = new Kamen();
        volby[0] = kamen;
        IVolba nuzky = new Nuzky();
        volby[1] = nuzky;
        IVolba papir = new Papir();
        volby[2] = papir;
        IVolba tapir = new Tapir();
        volby[3] = tapir;
        IVolba spock = new Spock();
        volby[4] = spock;
        IVolba nahoda = new Nahoda();
        volby[5] = nahoda;


        g2.setFont(new Font("Arial", Font.BOLD, 35));
        g2.drawString("Vyberte: ", 0 - Konstanty.SIRKA_OKNA / 2 + Konstanty.OFFSET_PISMA_VE_HRE / 2, 0 - Konstanty.VYSKA_OKNA / 2 + Konstanty.OFFSET_PISMA_VE_HRE);

        //g.fillOval(100, 100, 50, 50);


        kamen.nakresliVolbu(g2, 1, 1, pocetSloupcu, poctetRadku);
        nuzky.nakresliVolbu(g2, 2, 1, pocetSloupcu, poctetRadku);
        papir.nakresliVolbu(g2, 3, 1, pocetSloupcu, poctetRadku);
        tapir.nakresliVolbu(g2, 1, 2, pocetSloupcu, poctetRadku);
        spock.nakresliVolbu(g2, 2, 2, pocetSloupcu, poctetRadku);
        nahoda.nakresliVolbu(g2, 3, 2, pocetSloupcu, poctetRadku);

        boolean jeZvoleno = false;
        IVolba zvoleno = null;
        double Xkliknuto = 0;
        double Ykliknuto = 0;
        //while (!jeZvoleno) {
            this.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Kliknuto na: " + e.getX() + " " + e.getY());
                    IVolba zvoleno = coByloVybrano(volby, e.getX() - Konstanty.SIRKA_OKNA / 2
                            , e.getY() - Konstanty.VYSKA_OKNA / 2);
                    if (zvoleno != null) {
                        int hodnota = zvoleno.ziskejHodnotu();
                        //jeZvoleno = true;
                        if (hodnota == -1) {
                            Random nahoda = new Random();
                            zvoleno = volby[nahoda.nextInt(0, Konstanty.POCET_MOZNOSTI)];
                            hodnota = zvoleno.ziskejHodnotu();
                        }
                        System.out.println("Hodnota: " + hodnota);
                    }
                }
            });
        //}
    }

    public IVolba coByloVybrano(IVolba[] pole, double x, double y) {
        IVolba navrat = null;
        boolean nalezeno = false;
        int i = 0;
        while (i < pole.length && !nalezeno) {
            if (((AbstractVolba)pole[i]).jeZvoleno(x, y)) {
                navrat = pole[i];
                nalezeno = true;

            }
            i = i + 1;
        }
        return navrat;
    }
}
