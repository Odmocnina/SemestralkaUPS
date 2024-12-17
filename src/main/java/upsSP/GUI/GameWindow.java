package upsSP.GUI;

import upsSP.Nastroje.Konstanty;
import upsSP.Server.Connection;
import upsSP.VolbyTahu.*;
import upsSP.Nastroje.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GameWindow extends JPanel implements Connection.IListenerInGame {

    int hodnota = -10;

    ITurn[] volby = new ITurn[Konstanty.POCET_MOZNOSTI + 1];

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
        koloLabel = new JLabel("Kolo číslo: " + GameState.getInstance().pocetOdehranychKol, SwingConstants.CENTER);
        koloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 0;
        hraniceMrize.gridwidth = 1;  // Nápis přes všechny tři sloupce
        add(koloLabel, hraniceMrize);

        // Přidání nadpisu pro stav hry
        //stavLabel = new JLabel("stavHry: " + pocetOdehranychKol, SwingConstants.CENTER);
        stavLabel = new JLabel("<html>Výher: " + GameState.getInstance().pocetVyhranychKol + "<br>"
                + "Proher: " + GameState.getInstance().pocetProhranychKol, SwingConstants.CENTER);
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

        try {
            Connection.getInstance().addListnerInGame(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tlacitko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hodnota = volba.getValue();
                try {
                    Connection spoj = Connection.getInstance();
                    spoj.sendMessage("Mess:turn:" + hodnota + ":\n");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                OknoZhodnoceniHry.hodnota = hodnota;
                okno.zobrazHru("PoTahu");
                System.out.println("Hodnota: " + hodnota + " Kolo: " + GameState.getInstance().pocetOdehranychKol);
            }
        });
    }

    /*public void pridejKolo() {
        this.pocetOdehranychKol = this.pocetOdehranychKol + 1;
    }*/

    public void aktualizujLably() {
        System.out.println("v akturalizaci labelu");
        koloLabel.setText("Kolo číslo: " + GameState.getInstance().pocetOdehranychKol);
        //stavLabel.setText("stavHry: " + pocetOdehranychKol);
        stavLabel.setText("<html>Výher: " + GameState.getInstance().pocetVyhranychKol + "<br>" + "Proher:" + GameState.getInstance().pocetProhranychKol + "</html>");
    }

    @Override
    public void onMessage(String message) {
        if (message.startsWith("Mess:turn:")) {
            System.out.println("Zprava identifikovana jako tah");
            Window window = (Window) SwingUtilities.getWindowAncestor(this);
            window.zobrazHru("zhodnoceniHry");
        }
    }
}
