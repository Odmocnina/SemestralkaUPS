package upsSP.GUI;

import upsSP.Nastroje.Konstanty;
import upsSP.Server.Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ReadyWindow extends JPanel {
    /*public ReadyWindow(Window okno) {
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
        JLabel napis = new JLabel("Čekání až protihráč bude připraven...");
        napis.setForeground(Color.WHITE); // Nastavení barvy textu
        napis.setFont(new Font("Arial", Font.BOLD, 24)); // Nastavení většího a tučného písma

        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 0;

        add(napis, hraniceMrize);

        try {
            Connection.getInstance().addListnerAfterTurn(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /*@Override
    public void onMessage(String message) {
        if (message.startsWith("Mess:gameResult:")) {
            System.out.println("Zprava identifikovana jako vysledek hry");
            OknoZhodnoceniHry.setParametry(message);
            OknoZhodnoceniHry.aktualizujLably();
        }
        if (message.startsWith("Mess:bothPlayerTurn:")) {
            System.out.println("Zprava identifikovana jako oba hraci hrali");
            Window window = (Window) SwingUtilities.getWindowAncestor(this);
            window.zobrazHru("ZhodnoceniHry");
        }
    }*/
}
