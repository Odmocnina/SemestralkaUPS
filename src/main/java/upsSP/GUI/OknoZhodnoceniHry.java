package upsSP.GUI;

import upsSP.Nastroje.Konstanty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OknoZhodnoceniHry extends JPanel {

    static JLabel volbaLabel, volbaProtivnikaLabel, vysledekKolaLabel, vysledekHryLabel;

    public OknoZhodnoceniHry(Window okno) {
        GridBagLayout mriz = new GridBagLayout();
        setLayout(mriz);
        setBackground(Konstanty.BARVA_POZADI);

        GridBagConstraints hraniceMrize = new GridBagConstraints();
        hraniceMrize.insets = new Insets(7, 7, 7, 7);
        hraniceMrize.fill = GridBagConstraints.BOTH;

        // Přidání popisku pro jméno
        volbaLabel = new JLabel("Zvolil jste: ");
        volbaLabel.setFont(new Font("Arial", Font.BOLD, Konstanty.VELIKOST_PISMA_ZHODNOCENI));
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 0;
        add(volbaLabel, hraniceMrize);

        volbaProtivnikaLabel = new JLabel("Protivník zvolil: ");
        volbaProtivnikaLabel.setFont(new Font("Arial", Font.BOLD, Konstanty.VELIKOST_PISMA_ZHODNOCENI));
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 1;
        add(volbaProtivnikaLabel, hraniceMrize);

        vysledekKolaLabel = new JLabel("Výsledek kola: ");
        vysledekKolaLabel.setFont(new Font("Arial", Font.BOLD, Konstanty.VELIKOST_PISMA_ZHODNOCENI));
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 2;
        add(vysledekKolaLabel, hraniceMrize);

        vysledekHryLabel = new JLabel("Výsledek hry: ");
        vysledekHryLabel.setFont(new Font("Arial", Font.BOLD, Konstanty.VELIKOST_PISMA_ZHODNOCENI));
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 3;
        add(vysledekHryLabel, hraniceMrize);

        // Pridani tlacitka pro dalsi kolo
        JButton dalsiKoloButton = new JButton("Další kolo");
        // zvetseni tlacitka
        dalsiKoloButton.setFont(new Font("Arial", Font.BOLD, Konstanty.VELIKOST_PISMA_ZHODNOCENI));
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 4;
        add(dalsiKoloButton, hraniceMrize);
        dalsiKoloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okno.zobrazHru("Hra");
            }
        });
    }

    public static String ziskejNazevZHodnoty(int hodnota) {
        switch (hodnota) {
            case 1:
                return "Kámen";
            case 2:
                return "Nůžky";
            case 3:
                return "Papír";
            case 4:
                return "Tapír";
            case 5:
                return "Spock";
            default:
                return "Neznámá hodnota";
        }
    }

    public static void aktualizujLably(int hodnota) {
        volbaLabel.setText("Zvolil jste: " + ziskejNazevZHodnoty(hodnota));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
