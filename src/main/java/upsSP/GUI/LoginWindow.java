package upsSP.GUI;

import upsSP.Nastroje.Konstanty;
import upsSP.Server.Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginWindow extends JPanel {

    public LoginWindow(Window okno) {
        GridBagLayout mriz = new GridBagLayout();
        setLayout(mriz);
        setBackground(Konstanty.BARVA_POZADI);

        GridBagConstraints hraniceMrize = new GridBagConstraints();
        hraniceMrize.insets = new Insets(7, 7, 7, 7);
        hraniceMrize.fill = GridBagConstraints.BOTH;
        hraniceMrize.anchor = GridBagConstraints.WEST;

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

        JTextField ipTextField = new JTextField("localhost", 15);
        hraniceMrize.gridx = 1;
        hraniceMrize.gridy = 1;
        add(ipTextField, hraniceMrize);

        JLabel portLabel = new JLabel("port:");
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 2;
        add(portLabel, hraniceMrize);

        JTextField portTextField = new JTextField("10000", 15);
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
                try {
                    Connection spoj = Connection.getInstance();
                    spoj.setConfiguration(Integer.parseInt(portTextField.getText()), ipTextField.getText());
                    String responce = spoj.sendMessage("Mess:login:" + jmenoTextField.getText() + ":\n");
                    //System.out.println("Odpoved serveru: " + responce);
                    //if (responce != null) {
                        okno.zobrazHru("Cekani");
                    //}
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
