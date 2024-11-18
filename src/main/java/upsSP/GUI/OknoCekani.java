package upsSP.GUI;

import upsSP.Nastroje.Konstanty;
import upsSP.Server.Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OknoCekani extends JPanel {
    public OknoCekani(Window okno) {
        GridBagLayout mriz = new GridBagLayout();
        setLayout(mriz);
        setBackground(Konstanty.BARVA_POZADI);

        GridBagConstraints hraniceMrize = new GridBagConstraints();
        hraniceMrize.insets = new Insets(7, 7, 7, 7);
        hraniceMrize.fill = GridBagConstraints.CENTER;
        hraniceMrize.anchor = GridBagConstraints.CENTER; // Ukotví nápis ve středu

        hraniceMrize.weighty = 1.0;
        hraniceMrize.weightx = 1.0;
        hraniceMrize.gridwidth = GridBagConstraints.REMAINDER;

        // Vytvoření nápisu
        JLabel napis = new JLabel("Čekání na protihráče...");
        napis.setForeground(Color.WHITE); // Nastavení barvy textu
        napis.setFont(new Font("Arial", Font.BOLD, 24)); // Nastavení většího a tučného písma

        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 0;

        add(napis, hraniceMrize);

        // Vytvoření tlačítka
        JButton tlacitko = new JButton("Zpátky na login");
        tlacitko.setFont(new Font("Arial", Font.PLAIN, 18)); // Nastavení písma pro tlačítko
        tlacitko.setPreferredSize(new Dimension(200, 50)); // Nastavení velikosti tlačítka

        // Přidání tlačítka pod nápis (do dalšího řádku)
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 1; // Tlačítko bude v řádku 1
        add(tlacitko, hraniceMrize);

        tlacitko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Zavolej metodu pro zobrazení nové stránky
                try {
                    Connection spoj = Connection.getInstance();
                    String responce = spoj.sendMessage("Mess:disconnect:" + spoj.clientId + ":" + "\n");
                    System.out.println("Odpoved serveru: " + responce);
                    okno.zobrazHru("Login");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
