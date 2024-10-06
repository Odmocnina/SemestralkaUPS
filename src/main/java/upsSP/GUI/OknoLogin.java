package upsSP.GUI;

import upsSP.Nastroje.Konstanty;
import upsSP.Nastroje.NacitacTextur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OknoLogin extends JPanel {

    public OknoLogin(Okno okno) {
        GridBagLayout mriz = new GridBagLayout();
        setLayout(mriz);

        GridBagConstraints hraniceMrize = new GridBagConstraints();
        hraniceMrize.insets = new Insets(7, 7, 7, 7);
        hraniceMrize.fill = GridBagConstraints.HORIZONTAL;
        hraniceMrize.anchor = GridBagConstraints.CENTER;

        // Přidání popisku pro jméno
        JLabel jmenoLabel = new JLabel("Jméno:");
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 0;
        add(jmenoLabel, hraniceMrize);

        // Přidání textového pole pro zadání jména
        JTextField jmenoTextField = new JTextField(15);
        hraniceMrize.gridx = 1;
        hraniceMrize.gridy = 0;
        add(jmenoTextField, hraniceMrize);

        JLabel ipLabel = new JLabel("ip:");
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 1;
        add(ipLabel, hraniceMrize);

        JTextField ipTextField = new JTextField(15);
        hraniceMrize.gridx = 1;
        hraniceMrize.gridy = 1;
        add(ipTextField, hraniceMrize);

        JLabel portLabel = new JLabel("port:");
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 2;
        add(portLabel, hraniceMrize);

        JTextField portTextField = new JTextField(15);
        hraniceMrize.gridx = 1;
        hraniceMrize.gridy = 2;
        add(portTextField, hraniceMrize);

        // Přidání tlačítka pro potvrzení
        JButton potvrditButton = new JButton("Potvrdit");
        hraniceMrize.gridx = 1;
        hraniceMrize.gridy = 3;
        add(potvrditButton, hraniceMrize);

        // Přidání prázdného sloupce napravo, aby se komponenty netáhly
        hraniceMrize.gridx = 2;
        hraniceMrize.gridy = 0;
        hraniceMrize.weightx = 1;  // Prázdný prostor pro roztažení
        add(Box.createHorizontalStrut(1), hraniceMrize);

        potvrditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Zavolej metodu pro zobrazení nové stránky
                okno.zobrazHru("Hra");
            }
        });

        hraniceMrize.gridx = 2;
        hraniceMrize.gridy = 0;
        hraniceMrize.weightx = 1;
        add(Box.createHorizontalStrut(1), hraniceMrize);

//        // Přidání obrázku na pravou stranu
//        JPanel obrazekPanel = new JPanel();
//        ImageIcon imageIcon = new ImageIcon("src/main/resources/data/VitacObr.png"); // Zde zadej cestu k obrázku
//        JLabel obrazekLabel = new JLabel(imageIcon);
//        obrazekPanel.add(obrazekLabel);
//
//        // Přidání panelu s obrázkem na pravou stranu
//        hraniceMrize.gridx = 2; // Umístění obrázku do třetího sloupce
//        hraniceMrize.gridy = 0; // Umístění do prvního řádku
//        hraniceMrize.gridheight = 4; // Obrazek zabere čtyři řádky
//        add(obrazekPanel, hraniceMrize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}
