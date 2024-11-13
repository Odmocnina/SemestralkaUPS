package upsSP.GUI;

import upsSP.Nastroje.Konstanty;

import javax.swing.*;
import java.awt.*;

public class OknoCekani extends JPanel {
    public OknoCekani(Okno okno) {
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
    }
}
