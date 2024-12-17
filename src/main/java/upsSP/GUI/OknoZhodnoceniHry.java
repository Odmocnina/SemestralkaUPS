package upsSP.GUI;

import upsSP.Nastroje.Konstanty;
import upsSP.Server.Connection;
import upsSP.Nastroje.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OknoZhodnoceniHry extends JPanel implements Connection.IListenerInJudgement {

    static JLabel volbaLabel, volbaProtivnikaLabel, vysledekKolaLabel, vysledekHryLabel;
    static int hodnotaProtivnika = -1;
    static String vysledekKola = "";

    static int hodnota = -1;

    static JButton dalsiKoloButton;

    public OknoZhodnoceniHry(Window okno) {
        GridBagLayout mriz = new GridBagLayout();
        setLayout(mriz);
        setBackground(Konstanty.BARVA_POZADI);

        GridBagConstraints hraniceMrize = new GridBagConstraints();
        hraniceMrize.insets = new Insets(7, 7, 7, 7);
        hraniceMrize.fill = GridBagConstraints.BOTH;

        // Přidání popisku pro jméno
        volbaLabel = new JLabel("Zvolil jste: ");
        volbaLabel.setFont(new Font("Arial", Font.BOLD, Konstanty.VELIKOST_PISMA_ZHODNOCENI));
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 0;
        add(volbaLabel, hraniceMrize);

        volbaProtivnikaLabel = new JLabel("Protivník zvolil: ");
        volbaProtivnikaLabel.setFont(new Font("Arial", Font.BOLD, Konstanty.VELIKOST_PISMA_ZHODNOCENI));
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 1;
        add(volbaProtivnikaLabel, hraniceMrize);

        vysledekKolaLabel = new JLabel("Výsledek kola: ");
        vysledekKolaLabel.setFont(new Font("Arial", Font.BOLD, Konstanty.VELIKOST_PISMA_ZHODNOCENI));
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 2;
        add(vysledekKolaLabel, hraniceMrize);

        vysledekHryLabel = new JLabel("Výsledek hry: ");
        vysledekHryLabel.setFont(new Font("Arial", Font.BOLD, Konstanty.VELIKOST_PISMA_ZHODNOCENI));
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 3;
        add(vysledekHryLabel, hraniceMrize);

        // Pridani tlacitka pro dalsi kolo
        dalsiKoloButton = new JButton("Další kolo");
        // zvetseni tlacitka
        dalsiKoloButton.setFont(new Font("Arial", Font.BOLD, Konstanty.VELIKOST_PISMA_ZHODNOCENI));
        hraniceMrize.gridx = 0;
        hraniceMrize.gridy = 4;
        add(dalsiKoloButton, hraniceMrize);
        dalsiKoloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*try {
                    Connection.getInstance().sendMessage("Mess:ready:" + Connection.getInstance().clientId + ":\n");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }*/
                okno.aktializujLably();
                if (GameState.getInstance().gameInProgress) {
                    okno.zobrazHru("Hra");
                } else {
                    okno.zobrazHru("Cekani");
                    try {
                        Connection.getInstance().sendMessage("Mess:game:" + Connection.getInstance().clientId + ":\n");
                        //GameState.getInstance().setScores(0, 0, 0);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    public static String ziskejNazevZHodnoty(int hodnota) {
        System.out.println("Hodnota: " + hodnota);
        switch (hodnota) {
            case 1:
                return "Kámen";
            case 2:
                return "Nůžky";
            case 3:
                return "Papír";
            case 4:
                return "Tapír";
            case 5:
                return "Spock";
            default:
                return "Neznámá hodnota";
        }
    }

    public static void aktualizujLably() {
        volbaLabel.setText("Zvolil jste: " + ziskejNazevZHodnoty(hodnota));
        volbaProtivnikaLabel.setText("Protivník zvolil: " + ziskejNazevZHodnoty(hodnotaProtivnika));
        vysledekKolaLabel.setText("Výsledek kola: " + vysledekKola);
        if (GameState.getInstance().pocetVyhranychKol == Konstanty.KOL_POCET) {
            vysledekHryLabel.setText("Výsledek hry: Vyhrál si");
            dalsiKoloButton.setText("Zpátky na login");
            GameState.gameInProgress = false;
            GameState.getInstance().setScores(0, 0, 0);
        } else if (GameState.getInstance().pocetProhranychKol == Konstanty.KOL_POCET) {
            vysledekHryLabel.setText("Výsledek hry: Prohrál si");
            dalsiKoloButton.setText("Zpátky na login");
            GameState.gameInProgress = false;
            GameState.getInstance().setScores(0, 0, 0);
        } else {
            vysledekHryLabel.setText("Výsledek hry: V průběhu hry");
        }
    }

    public static void setParametry(String message) {
        String[] parametry = message.split(":");
        hodnotaProtivnika = Integer.parseInt(parametry[3]);
        if (parametry[2].equals("w")) {
            vysledekKola = "Výhra";
        } else if (parametry[2].equals("l")) {
            vysledekKola = "Prohra";
        } else if (parametry[2].equals("s")) {
            vysledekKola = "Remíza";
        } else {
            vysledekKola = "Chyba";
        }
        //System.out.println("parametry: " + parametry[4] + " " + parametry[5]);
        GameState.getInstance().setScores(Integer.parseInt(parametry[4]), Integer.parseInt(parametry[5]), Integer.parseInt(parametry[6]));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void onMessage(String message) {

    }
}
