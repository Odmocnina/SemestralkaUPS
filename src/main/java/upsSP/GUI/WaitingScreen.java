package upsSP.GUI;

import upsSP.Nastroje.Constants;
import upsSP.Server.Connection;
import upsSP.Nastroje.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class WaitingScreen extends JPanel implements Connection.IListenerInQueue {
    public WaitingScreen(Window window) {
        GridBagLayout mriz = new GridBagLayout();
        setLayout(mriz);
        setBackground(Constants.BACKGROUND_COLOR);

        GridBagConstraints gridBorders = new GridBagConstraints();
        gridBorders.insets = new Insets(7, 7, 7, 7);
        gridBorders.fill = GridBagConstraints.CENTER;
        gridBorders.anchor = GridBagConstraints.CENTER; // Ukotví nápis ve středu

        gridBorders.weighty = 1.0;
        gridBorders.weightx = 1.0;
        gridBorders.gridwidth = GridBagConstraints.REMAINDER;

        // Vytvoření nápisu
        JLabel napis = new JLabel("Čekání na protihráče...");
        napis.setForeground(Color.WHITE); // Nastavení barvy textu
        napis.setFont(new Font("Arial", Font.BOLD, 24)); // Nastavení většího a tučného písma

        gridBorders.gridx = 0;
        gridBorders.gridy = 0;

        add(napis, gridBorders);

        // Vytvoření tlačítka
        JButton button = new JButton("Zpátky na login");
        button.setFont(new Font("Arial", Font.PLAIN, 18)); // Nastavení písma pro tlačítko
        button.setPreferredSize(new Dimension(200, 50)); // Nastavení velikosti tlačítka

        // Přidání tlačítka pod nápis (do dalšího řádku)
        gridBorders.gridx = 0;
        gridBorders.gridy = 1; // Tlačítko bude v řádku 1
        add(button, gridBorders);

        try {
            Connection.getInstance().addListnerInQueue(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Zavolej metodu pro zobrazení nové stránky
                try {
                    Connection connection = Connection.getInstance();
                    String responce = connection.sendMessage("Mess:logout:" + connection.clientId + ":");
                    //System.out.println("Odpoved serveru: " + responce);
                    Connection.getInstance().closeConnection();
                    window.zobrazHru("Login");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @Override
    public void onMessage(String message) {
        if (message.startsWith("Mess:gameBegin:")) {
            System.out.println("Zprava identifikovana jako start hry");
            Window window = (Window) SwingUtilities.getWindowAncestor(this);
            window.zobrazHru("Game");
            GameEvaluationScreen.nextRoundButton.setText("Další kolo");
            GameState.getInstance().gameInProgress = true;
        }
        if (message.startsWith("Mess:login:")) {
            System.out.println("Zprava identifikovana jako login");
            String id = message.split(":")[2];
            try {
                Connection.getInstance().clientId = Integer.parseInt(id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (message.startsWith("Mess:logout:")) {
            System.out.println("Zprava identifikovana jako logout");
            try {
                Connection.getInstance().clientId = -1;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
