package upsSP.GUI;

import upsSP.Nastroje.Constants;
import upsSP.Server.Connection;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AfterTurnWindow extends JPanel implements Connection.IListenerAfterTurn {
    public AfterTurnWindow(Window window) {
        GridBagLayout grid = new GridBagLayout();
        setLayout(grid);
        setBackground(Constants.BACKGROUND_COLOR);

        GridBagConstraints gridBorders = new GridBagConstraints();
        gridBorders.insets = new Insets(7, 7, 7, 7);
        gridBorders.fill = GridBagConstraints.CENTER;
        gridBorders.anchor = GridBagConstraints.CENTER; // Ukotví nápis ve středu

        gridBorders.weighty = 1.0;
        gridBorders.weightx = 1.0;
        gridBorders.gridwidth = GridBagConstraints.REMAINDER;

        // Vytvoření nápisu
        JLabel text = new JLabel("Čekání na tah protihráče...");
        text.setForeground(Color.WHITE); // Nastavení barvy textu
        text.setFont(new Font("Arial", Font.BOLD, 24)); // Nastavení většího a tučného písma

        gridBorders.gridx = 0;
        gridBorders.gridy = 0;

        add(text, gridBorders);

        try {
            Connection.getInstance().addListnerAfterTurn(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void onMessage(String message) {
        if (message.startsWith("Mess:gameResult:")) {
            System.out.println("Zprava identifikovana jako vysledek hry");
            GameEvaluationScreen.setGameState(message);
            GameEvaluationScreen.aktualizujLably();
        }
        if (message.startsWith("Mess:bothPlayerTurn:")) {
            System.out.println("Zprava identifikovana jako oba hraci hrali");
            Window window = (Window) SwingUtilities.getWindowAncestor(this);
            window.zobrazHru("ZhodnoceniHry");
        }
    }
}

