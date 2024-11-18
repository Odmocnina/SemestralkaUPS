package upsSP.GUI;

import upsSP.Nastroje.Konstanty;
import upsSP.VolbyTahu.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JPanel {

    int hodnota = -10;

    ITurn[] volby = new ITurn[Konstanty.POCET_MOZNOSTI + 1];

    int pocetOdehranychKol = 0;

    JLabel koloLabel, stavLabel;

    public GameWindow(Window okno) {
        ITurn kamen = new Stone();
        volby[0] = kamen;
        ITurn nuzky = new Scissors();
        volby[1] = nuzky;
        ITurn papir = new Paper();
        volby[2] = papir;
        ITurn tapir = new Lizard();
        volby[3] = tapir;
        ITurn spock = new Spock();
        volby[4] = spock;
        ITurn nahoda = new Mandom();
        volby[5] = nahoda;
        setBackground(Konstanty.BARVA_POZADI);

        GridBagLayout mriz = new GridBagLayout();
        setLayout(mriz);

        GridBagConstraints hraniceMrize = new GridBagConstraints();
        hraniceMrize.insets = new Insets(7, 7, 7, 7);
        hraniceMrize.fill = GridBagConstraints.BOTH;  // Roztažení na obě strany
        hraniceMrize.anchor = GridBagConstraints.CENTER;  // Zarovnání na střed
        hraniceMrize.weightx = 1;  // Roztahování na šířku
        hraniceMrize.weighty = 1;  // Roztahování na výšku

        // Přidání nadpisu pro kolo číslo
        koloLabel = new JLabel("Kolo číslo: " + pocetOdehranychKol, SwingConstants.CENTER);
        koloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 0;
        hraniceMrize.gridwidth = 1;  // Nápis přes všechny tři sloupce
        add(koloLabel, hraniceMrize);

        // Přidání nadpisu pro stav hry
        stavLabel = new JLabel("stavHry: " + pocetOdehranychKol, SwingConstants.CENTER);
        koloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        hraniceMrize.gridx = 1;
        hraniceMrize.gridy = 0;
        hraniceMrize.gridwidth = 1;  // Nápis přes všechny tři sloupce
        add(stavLabel, hraniceMrize);

        JLabel pravidlaLabel = new JLabel("<html>Kámen > Nůžky, Tapír" +
                "<br>Papír > Kámen, Tapír" +
                "<br>Nůžky > Papír, Spock" +
                "<br>Tapír > Nůžky, Spock" +
                "<br>Spock > Kámen, Papír</html>"
                , SwingConstants.CENTER);

        koloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        hraniceMrize.gridx = 2;
        hraniceMrize.gridy = 0;
        hraniceMrize.gridwidth = 1;  // Tento label bude přes všechny tři sloupce
        add(pravidlaLabel, hraniceMrize);

        pridaniTlacitka(hraniceMrize, 0, 1, kamen, okno);
        pridaniTlacitka(hraniceMrize, 1, 1, nuzky, okno);
        pridaniTlacitka(hraniceMrize, 2, 1, papir, okno);
        pridaniTlacitka(hraniceMrize, 0, 2, tapir, okno);
        pridaniTlacitka(hraniceMrize, 1, 2, spock, okno);
        pridaniTlacitka(hraniceMrize, 2, 2, nahoda, okno);
    }

    private void pridaniTlacitka(GridBagConstraints mriz, int x, int y, ITurn volba, Window okno) {
        // První řádek tlačítek (gridx = 0, 1, 2 pro tři sloupce)
        JButton tlacitko = new JButton(volba.getNameOfTurn());

        ImageIcon obrazek = new ImageIcon(Konstanty.CESTA_DO_DATA + volba.getNameOfPictureFile());
        // Změna velikosti ikony
        Image img = obrazek.getImage();
        Image newimg = img.getScaledInstance((int)(Konstanty.VELIKOST_OBRAZKU),
                (int)(Konstanty.VELIKOST_OBRAZKU), java.awt.Image.SCALE_SMOOTH); // Změň velikost podle potřeby
        ImageIcon scaledObrazek = new ImageIcon(newimg);
        tlacitko.setBackground(Color.WHITE);
        tlacitko.setIcon(scaledObrazek);

        mriz.gridx = x;  // První sloupec
        mriz.gridy = y;  // První řádek
        add(tlacitko, mriz);

        tlacitko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hodnota = volba.getValue();
                pridejKolo();
                aktualizujLably();
                OknoZhodnoceniHry.aktualizujLably(hodnota);
                okno.zobrazHru("ZhodnoceniHry");
                System.out.println("Hodnota: " + hodnota + " Kolo: " + pocetOdehranychKol);
            }
        });
    }

    public void pridejKolo() {
        this.pocetOdehranychKol = this.pocetOdehranychKol + 1;
    }

    private void aktualizujLably() {
        koloLabel.setText("Kolo číslo: " + pocetOdehranychKol);
        stavLabel.setText("stavHry: " + pocetOdehranychKol);
    }

        /*int poctetRadku = 3;

    int pocetSloupcu = 3;

    //IVolba[] volby;

    public OknoHra(Okno okno) {
        volby = new IVolba[Konstanty.POCET_MOZNOSTI + 1];
        //boolean jeZvoleno = false;
        //IVolba zvoleno = null;
        //double Xkliknuto = 0;
        //double Ykliknuto = 0;
        //while (!jeZvoleno) {

        if (true) {
            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    System.out.println("Kliknuto na: " + (e.getX() - getWidth() / 2) + " " + (e.getY() - getHeight() / 2));
                    double Xkliknuto = e.getX() - getWidth() / 2;
                    double Ykliknuto = e.getY() - getHeight() / 2;
                    IVolba zvoleno = coByloVybrano(Xkliknuto, Ykliknuto);
                    if (zvoleno != null) {
                        int hodnota = zvoleno.ziskejHodnotu();
                        //jeZvoleno = true;
                        if (hodnota == -1) {
                            Random nahoda = new Random();
                            hodnota = nahoda.nextInt(1, Konstanty.POCET_MOZNOSTI);
                        }
                        System.out.println("Hodnota: " + hodnota + " Kolo: " + Okno.pocetOdehranychKol);
                            Okno.pocetOdehranychKol = Okno.pocetOdehranychKol + 1;
                        if (Okno.pocetOdehranychKol >= Konstanty.POCET_KOL) {
                            System.out.println("Konec hry");
                        }
                        //okno.zobrazHru("ZhodnoceniHry");
                        repaint();
                    }
                }
            });
        }

    }

    private void pripravGrafiku(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double sirka = getWidth();
        double vyska = getHeight();
        g.translate(sirka / 2, vyska / 2);
        double meritkoX = sirka / Konstanty.SIRKA_OKNA;
        double meritkoY = vyska / Konstanty.VYSKA_OKNA;
        double mensi = Math.min(meritkoX, meritkoY);
        g.scale(mensi, mensi);
    }

    private double ziskejMeritkoX() {
        double sirka = getWidth();
        return sirka / Konstanty.SIRKA_OKNA;
    }

    private double ziskejMeritkoY() {
        double vyska = getHeight();
        return vyska / Konstanty.VYSKA_OKNA;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        pripravGrafiku(g2);
        setBackground(new Color(0, 128, 0));
        //setLayout(new GridLayout(poctetRadku, pocetSloupcu));
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
        g2.drawString("Kolo: " + (Okno.pocetOdehranychKol + 1), 0 + Konstanty.SIRKA_OKNA / 3 + Konstanty.OFFSET_PISMA_VE_HRE / 2, 0 - Konstanty.VYSKA_OKNA / 2 + Konstanty.OFFSET_PISMA_VE_HRE);

        aktualizaceRozmeru();

        kamen.nakresliVolbu(g2, 1, 1, pocetSloupcu, poctetRadku, getWidth(), getHeight());
        nuzky.nakresliVolbu(g2, 2, 1, pocetSloupcu, poctetRadku, getWidth(), getHeight());
        papir.nakresliVolbu(g2, 3, 1, pocetSloupcu, poctetRadku, getWidth(), getHeight());
        tapir.nakresliVolbu(g2, 1, 2, pocetSloupcu, poctetRadku, getWidth(), getHeight());
        spock.nakresliVolbu(g2, 2, 2, pocetSloupcu, poctetRadku, getWidth(), getHeight());
        nahoda.nakresliVolbu(g2, 3, 2, pocetSloupcu, poctetRadku, getWidth(), getHeight());
    }*/

    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public IVolba coByloVybrano(double x, double y) {
        IVolba navrat = null;
        boolean nalezeno = false;
        int i = 0;
        while (i < volby.length && !nalezeno) {
            if (((AbstractVolba)volby[i]).jeZvoleno(x, y)) {
                navrat = volby[i];
                nalezeno = true;

            }
            i = i + 1;
        }
        return navrat;
    }

    private void aktualizaceRozmeru() {
        for (IVolba volba : volby) {
            volba.prepocitejRozmery(getWidth(), getHeight(), pocetSloupcu, poctetRadku);
        }
    }*/
}
