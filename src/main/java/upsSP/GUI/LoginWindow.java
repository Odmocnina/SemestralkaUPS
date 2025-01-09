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

import static java.lang.Thread.sleep;

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

        JTextField ipTextField = new JTextField("147.228.67.105", 15);
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
                    if (jmenoTextField.getText().length() > 9) {
                        JOptionPane.showMessageDialog(null, "Jméno moc dlouhé!", "Chyba",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (jmenoTextField.getText().contains(":")) {
                        JOptionPane.showMessageDialog(null, "Nepovolené znaky v jmeně!",
                                "Chyba", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Získání rodičovského okna
                        java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(LoginWindow.this);

                        // Přetypování na správný typ
                        JDialog waitingDialog;
                        if (parentWindow instanceof Frame) {
                            waitingDialog = new JDialog((Frame) parentWindow, "Čekání", true);
                        } else if (parentWindow instanceof Dialog) {
                            waitingDialog = new JDialog((Dialog) parentWindow, "Čekání", true);
                        } else {
                            throw new IllegalStateException("Rodičovské okno není typu Frame ani Dialog!");
                        }

                        waitingDialog.setLayout(new BorderLayout());
                        waitingDialog.add(new JLabel("Připojuji k serveru, prosím čekejte..."), BorderLayout.CENTER);
                        waitingDialog.setSize(300, 100);
                        waitingDialog.setLocationRelativeTo(null);

                        SwingWorker<Void, Void> worker = new SwingWorker<>() {
                            boolean connected = true;
                            @Override
                            protected Void doInBackground() {
                                // Nastavení připojení na pozadí
                                try {
                                    Connection connection = Connection.getInstance();
                                    connection.setConfiguration(Integer.parseInt(portTextField.getText()), ipTextField.getText(), true);
                                    connection.sendMessage("Mess:login:" + jmenoTextField.getText() + ":");
                                } catch (IOException e3) {
                                    JOptionPane.showMessageDialog(null,
                                            "Nelze se připojit k serveru. Zkontrolujte IP adresu a port.",
                                            "Chyba připojení", JOptionPane.ERROR_MESSAGE);
                                    connected = false;
                                    window.zobrazHru("login");
                                }
                                return null;
                            }

                            @Override
                            protected void done() {
                                waitingDialog.dispose();
                                if (connected) {
                                    window.zobrazHru("help");
                                }
                            }
                        };

                        worker.execute();
                        waitingDialog.setVisible(true);
                    }
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(null, "Port musí být číslo!", "Chyba",
                            JOptionPane.ERROR_MESSAGE);
                    window.zobrazHru("login");
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
