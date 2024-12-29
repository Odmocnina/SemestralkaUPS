package upsSP.GUI;

import upsSP.Nastroje.Constants;
import upsSP.Server.Connection;
import upsSP.VolbyTahu.*;
import upsSP.Nastroje.GameState;
import upsSP.Nastroje.States;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GameWindow extends JPanel implements Connection.IListenerInGame {

    int turnValue = -10;

    ITurn[] turns = new ITurn[Constants.NUMBER_OF_TURNS + 1];

    static JLabel roundLabel, stavLabel;

    public GameWindow(Window window) {
        ITurn stone = new Stone();
        turns[0] = stone;
        ITurn scissors = new Scissors();
        turns[1] = scissors;
        ITurn paper = new Paper();
        turns[2] = paper;
        ITurn lizard = new Lizard();
        turns[3] = lizard;
        ITurn spock = new Spock();
        turns[4] = spock;
        ITurn mandom = new Mandom();
        turns[5] = mandom;
        setBackground(Constants.BACKGROUND_COLOR);

        GridBagLayout grid = new GridBagLayout();
        setLayout(grid);

        GridBagConstraints gridBorders = new GridBagConstraints();
        gridBorders.insets = new Insets(7, 7, 7, 7);
        gridBorders.fill = GridBagConstraints.BOTH;  // Roztažení na obě strany
        gridBorders.anchor = GridBagConstraints.CENTER;  // Zarovnání na střed
        gridBorders.weightx = 1;  // Roztahování na šířku
        gridBorders.weighty = 1;  // Roztahování na výšku

        // Přidání nadpisu pro kolo číslo
        roundLabel = new JLabel("Kolo číslo: " + GameState.getInstance().numberOfPlayedRounds, SwingConstants.CENTER);
        roundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gridBorders.gridx = 0;
        gridBorders.gridy = 0;
        gridBorders.gridwidth = 1;  // Nápis přes všechny tři sloupce
        add(roundLabel, gridBorders);

        // Přidání nadpisu pro stav hry
        stavLabel = new JLabel("<html>Výher: " + GameState.getInstance().numberOfWonRounds + "<br>"
                + "Proher: " + GameState.getInstance().numberOfLostRounds + "<br>" +
                "Remíz:" + GameState.getInstance().numberOfSM + "</html>", SwingConstants.CENTER);
        roundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gridBorders.gridx = 1;
        gridBorders.gridy = 0;
        gridBorders.gridwidth = 1;  // Nápis přes všechny tři sloupce
        add(stavLabel, gridBorders);

        JLabel pravidlaLabel = new JLabel("<html>Kámen > Nůžky, Tapír" +
                "<br>Papír > Kámen, Tapír" +
                "<br>Nůžky > Papír, Spock" +
                "<br>Tapír > Nůžky, Spock" +
                "<br>Spock > Kámen, Papír</html>"
                , SwingConstants.CENTER);

        roundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gridBorders.gridx = 2;
        gridBorders.gridy = 0;
        gridBorders.gridwidth = 1;  // Tento label bude přes všechny tři sloupce
        add(pravidlaLabel, gridBorders);

        addButton(gridBorders, 0, 1, stone, window);
        addButton(gridBorders, 1, 1, scissors, window);
        addButton(gridBorders, 2, 1, paper, window);
        addButton(gridBorders, 0, 2, lizard, window);
        addButton(gridBorders, 1, 2, spock, window);
        addButton(gridBorders, 2, 2, mandom, window);
    }

    private void addButton(GridBagConstraints grid, int x, int y, ITurn turn, Window window) {
        // První řádek tlačítek (gridx = 0, 1, 2 pro tři sloupce)
        JButton button = new JButton(turn.getNameOfTurn());

        ImageIcon image = new ImageIcon(Constants.PATH_TO_DATA + turn.getNameOfPictureFile());
        // Změna velikosti ikony
        Image img = image.getImage();
        Image newimg = img.getScaledInstance((int)(Constants.SOIOT),
                (int)(Constants.SOIOT), java.awt.Image.SCALE_SMOOTH); // Změň velikost podle potřeby
        ImageIcon scaledImage = new ImageIcon(newimg);
        button.setBackground(Color.WHITE);
        button.setIcon(scaledImage);

        grid.gridx = x;  // První sloupec
        grid.gridy = y;  // První řádek
        add(button, grid);

        try {
            Connection.getInstance().addListnerInGame(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnValue = turn.getValue();
                try {
                    Connection connection = Connection.getInstance();
                    connection.sendMessage("Mess:turn:" + turnValue + ":");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                GameEvaluationScreen.valueTurn = turnValue;
                window.zobrazHru("afterPlay");
            }
        });
    }

    /*public void pridejKolo() {
        this.pocetOdehranychKol = this.pocetOdehranychKol + 1;
    }*/

    public static void updateLabes() {
        System.out.println("v akturalizaci labelu");
        roundLabel.setText("Kolo číslo: " + GameState.getInstance().numberOfPlayedRounds);
        //stavLabel.setText("stavHry: " + pocetOdehranychKol);
        //if (stavLabel != null) {
        stavLabel.setText("<html>Výher: " + GameState.getInstance().numberOfWonRounds + "<br>" + "Proher:"
                + GameState.getInstance().numberOfLostRounds
                + "<br>" + "Remíz:" + GameState.getInstance().numberOfSM + "</html>");
    }

    @Override
    public void onMessage(String message) {
        if (message.startsWith("Mess:turn:")) {
            System.out.println("Zprava identifikovana jako tah");
            Window window = (Window) SwingUtilities.getWindowAncestor(this);
            window.zobrazHru("gameJudgement");
        }
    }
}
