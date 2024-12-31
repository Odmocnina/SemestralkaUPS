package upsSP.GUI;

import upsSP.Nastroje.Constants;
import upsSP.Nastroje.GameState;
import upsSP.Server.Connection;
import upsSP.Nastroje.States;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginWindow extends JPanel {

    public LoginWindow(Window window) {
        GridBagLayout mriz = new GridBagLayout();
        setLayout(mriz);
        setBackground(Constants.BACKGROUND_COLOR);

        GridBagConstraints gridBorders = new GridBagConstraints();
        gridBorders.insets = new Insets(7, 7, 7, 7);
        gridBorders.fill = GridBagConstraints.BOTH;
        gridBorders.anchor = GridBagConstraints.WEST;

        // Přidání popisku pro jméno
        JLabel nameLabel = new JLabel("Jméno:");
        gridBorders.gridx = 0;
        gridBorders.gridy = 0;
        add(nameLabel, gridBorders);

        // Přidání textového pole pro zadání jména
        JTextField jmenoTextField = new JTextField(15);
        gridBorders.gridx = 1;
        gridBorders.gridy = 0;
        add(jmenoTextField, gridBorders);

        JLabel ipLabel = new JLabel("ip:");
        gridBorders.gridx = 0;
        gridBorders.gridy = 1;
        add(ipLabel, gridBorders);

        JTextField ipTextField = new JTextField("localhost", 15);
        gridBorders.gridx = 1;
        gridBorders.gridy = 1;
        add(ipTextField, gridBorders);

        JLabel portLabel = new JLabel("port:");
        gridBorders.gridx = 0;
        gridBorders.gridy = 2;
        add(portLabel, gridBorders);

        JTextField portTextField = new JTextField("10000", 15);
        gridBorders.gridx = 1;
        gridBorders.gridy = 2;
        add(portTextField, gridBorders);

        // Přidání tlačítka pro potvrzení
        JButton potvrditButton = new JButton("Potvrdit");
        gridBorders.gridx = 1;
        gridBorders.gridy = 3;
        add(potvrditButton, gridBorders);

        // Přidání prázdného sloupce napravo, aby se komponenty netáhly
        gridBorders.gridx = 2;
        gridBorders.gridy = 0;
        gridBorders.weightx = 1;  // Prázdný prostor pro roztažení
        add(Box.createHorizontalStrut(1), gridBorders);

        potvrditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Zavolej metodu pro zobrazení nové stránky
                try {
                    if (jmenoTextField.getText().length() > 15) {
                        JOptionPane.showMessageDialog(null, "Jméno moc dlouhé!", "Chyba",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        Connection connection = Connection.getInstance();
                        connection.setConfiguration(Integer.parseInt(portTextField.getText()), ipTextField.getText());
                        String responce = connection.sendMessage("Mess:login:" + jmenoTextField.getText() + ":");
                        connection.sendingPingToServer();
                        window.zobrazHru("help");
                    }
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(null, "Port musí být číslo!", "Chyba",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException e2) {
                    JOptionPane.showMessageDialog(null,
                            "Nelze se připojit k serveru. Zkontrolujte IP adresu a port.",
                            "Chyba připojení", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gridBorders.gridx = 2;
        gridBorders.gridy = 0;
        gridBorders.weightx = 1;
        add(Box.createHorizontalStrut(1), gridBorders);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}
