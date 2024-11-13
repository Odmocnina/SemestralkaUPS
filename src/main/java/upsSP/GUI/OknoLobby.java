package upsSP.GUI;

import upsSP.Nastroje.Konstanty;
import upsSP.Nastroje.NacitacTextur;
import upsSP.Server.Spojeni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OknoLobby extends JPanel {

    public OknoLobby(Okno okno) {
        GridBagLayout mriz = new GridBagLayout();
        setLayout(mriz);
        setBackground(Konstanty.BARVA_POZADI);

        GridBagConstraints hraniceMrize = new GridBagConstraints();
        hraniceMrize.insets = new Insets(7, 7, 7, 7);
        hraniceMrize.fill = GridBagConstraints.HORIZONTAL; // Tlačítko se roztáhne horizontálně
        hraniceMrize.anchor = GridBagConstraints.SOUTH; // Ukotvíme tlačítko dole

        hraniceMrize.weighty = 1.0; // Zvýšení hmotnosti na svislé ose
        hraniceMrize.weightx = 1.0; // Zvýšení hmotnosti na vodorovné ose, aby tlačítko bylo širší
        hraniceMrize.gridwidth = GridBagConstraints.REMAINDER;

        // Přidání tlačítka pro potvrzení
        JButton vytvoreniHryButton = new JButton("Vytvořit hru");
        //vytvoreniHryButton.setPreferredSize(new Dimension(300, 40)); // Nastavení většího rozměru tlačítka

        hraniceMrize.gridx = 0; // Nastavení x-ové pozice (např. na střed)
        hraniceMrize.gridy = 1; // Nastavení y-ové pozice

        add(vytvoreniHryButton, hraniceMrize);

        vytvoreniHryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Spojeni spoj = Spojeni.getInstance();
                    String responce = spoj.sendMessage("Mess:createLobby:\n");
                    System.out.println(responce);
                    okno.zobrazHru("Cekani");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Přidání mezery na ostatních místech
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

