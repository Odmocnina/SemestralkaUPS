package upsSP.GUI;

import upsSP.Nastroje.Konstanty;
import upsSP.Nastroje.NacitacTextur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OknoLobby extends JPanel {

    public OknoLobby(Okno okno) {
        GridBagLayout mriz = new GridBagLayout();
        setLayout(mriz);
        setBackground(Konstanty.BARVA_POZADI);

        GridBagConstraints hraniceMrize = new GridBagConstraints();
        hraniceMrize.insets = new Insets(7, 7, 7, 7);
        hraniceMrize.fill = GridBagConstraints.BOTH;
        hraniceMrize.anchor = GridBagConstraints.WEST;

        // Přidání tlačítka pro potvrzení
        JButton vytvoreniHryButton = new JButton("Vytvořit hru");
        hraniceMrize.gridx = 1;
        hraniceMrize.gridy = 3;
        add(vytvoreniHryButton, hraniceMrize);

        // Přidání prázdného sloupce napravo, aby se komponenty netáhly
        hraniceMrize.gridx = 2;
        hraniceMrize.gridy = 0;
        hraniceMrize.weightx = 1;  // Prázdný prostor pro roztažení
        add(Box.createHorizontalStrut(1), hraniceMrize);

        vytvoreniHryButton.addActionListener(new ActionListener() {
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}

