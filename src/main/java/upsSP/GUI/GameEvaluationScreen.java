package upsSP.GUI;

import upsSP.Nastroje.Constants;
import upsSP.Server.Connection;
import upsSP.Nastroje.GameState;
import upsSP.Nastroje.States;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameEvaluationScreen extends JPanel implements Connection.IListenerInJudgement {

    static JLabel turnLabel, opponentTurnLabel, resultRoundLabel, gameResultLabel;
    static int opponentTurnValue = -1;
    static String roundResult = "";

    static int valueTurn = -1;

    static JButton nextRoundButton;

    public GameEvaluationScreen(Window window) {
        GridBagLayout mriz = new GridBagLayout();
        setLayout(mriz);
        setBackground(Constants.BACKGROUND_COLOR);

        GridBagConstraints gridBorders = new GridBagConstraints();
        gridBorders.insets = new Insets(7, 7, 7, 7);
        gridBorders.fill = GridBagConstraints.BOTH;

        // Přidání popisku pro jméno
        turnLabel = new JLabel("Zvolil jste: ");
        turnLabel.setFont(new Font("Arial", Font.BOLD, Constants.SIZE_OF_LETTER_IN_EVALUATION));
        gridBorders.gridx = 0;
        gridBorders.gridy = 0;
        add(turnLabel, gridBorders);

        opponentTurnLabel = new JLabel("Protivník zvolil: ");
        opponentTurnLabel.setFont(new Font("Arial", Font.BOLD, Constants.SIZE_OF_LETTER_IN_EVALUATION));
        gridBorders.gridx = 0;
        gridBorders.gridy = 1;
        add(opponentTurnLabel, gridBorders);

        resultRoundLabel = new JLabel("Výsledek kola: ");
        resultRoundLabel.setFont(new Font("Arial", Font.BOLD, Constants.SIZE_OF_LETTER_IN_EVALUATION));
        gridBorders.gridx = 0;
        gridBorders.gridy = 2;
        add(resultRoundLabel, gridBorders);

        gameResultLabel = new JLabel("Výsledek hry: ");
        gameResultLabel.setFont(new Font("Arial", Font.BOLD, Constants.SIZE_OF_LETTER_IN_EVALUATION));
        gridBorders.gridx = 0;
        gridBorders.gridy = 3;
        add(gameResultLabel, gridBorders);

        // Pridani tlacitka pro dalsi kolo
        nextRoundButton = new JButton("Další kolo");
        // zvetseni tlacitka
        nextRoundButton.setFont(new Font("Arial", Font.BOLD, Constants.SIZE_OF_LETTER_IN_EVALUATION));
        gridBorders.gridx = 0;
        gridBorders.gridy = 4;
        add(nextRoundButton, gridBorders);
        nextRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*try {
                    Connection.getInstance().sendMessage("Mess:ready:" + Connection.getInstance().clientId + ":\n");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }*/
                window.aktializujLably();
                if (GameState.getInstance().gameInProgress) {
                    window.zobrazHru("game");
                } else {
                    window.zobrazHru("wait");
                    try {
                        Connection.getInstance().sendMessage("Mess:game:" + Connection.getInstance().clientId + ":");
                        //GameState.getInstance().setScores(0, 0, 0);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    public static String ziskejNazevZHodnoty(int hodnota) {
        switch (hodnota) {
            case Constants.STONE_VALUE:
                return "Kámen";
            case Constants.SCISSORS_VALUE:
                return "Nůžky";
            case Constants.PAPER_VALUE:
                return "Papír";
            case Constants.LIZZARD_VALUE:
                return "Tapír";
            case Constants.SPOCK_VALUE:
                return "Spock";
            default:
                return "Neznámá hodnota";
        }
    }

    public static void aktualizujLably() {
        //if (opponentTurnLabel != null) {
            turnLabel.setText("Zvolil jste: " + ziskejNazevZHodnoty(valueTurn));
            opponentTurnLabel.setText("Protivník zvolil: " + ziskejNazevZHodnoty(opponentTurnValue));
            resultRoundLabel.setText("Výsledek kola: " + roundResult);
            if (GameState.getInstance().numberOfWonRounds == Constants.NUMBER_OF_ROUNDS) {
                gameResultLabel.setText("Výsledek hry: Vyhrál si");
                nextRoundButton.setText("Zpátky do fronty");
                GameState.getInstance().gameInProgress = false;
                GameState.getInstance().setScores(0, 0, 0);
            } else if (GameState.getInstance().numberOfLostRounds == Constants.NUMBER_OF_ROUNDS) {
                gameResultLabel.setText("Výsledek hry: Prohrál si");
                //dalsiKoloButton.setText("Zpátky na login");
                nextRoundButton.setText("Zpátky do fronty");
                GameState.getInstance().gameInProgress = false;
                GameState.getInstance().setScores(0, 0, 0);
            } else {
                gameResultLabel.setText("Výsledek hry: V průběhu hry");
            }
        //}
    }

    public static void setGameState(String message) {
        String[] messageFragments = message.split(":");
        opponentTurnValue = Integer.parseInt(messageFragments[3]);
        if (messageFragments[2].equals("w")) {
            roundResult = "Výhra";
        } else if (messageFragments[2].equals("l")) {
            roundResult = "Prohra";
        } else if (messageFragments[2].equals("s")) {
            roundResult = "Remíza";
        } else {
            roundResult = "Chyba";
        }
        //System.out.println("parametry: " + parametry[4] + " " + parametry[5]);
        GameState.getInstance().setScores(Integer.parseInt(messageFragments[4]), Integer.parseInt(messageFragments[5]), Integer.parseInt(messageFragments[6]));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void onMessage(String message) {

    }
}
