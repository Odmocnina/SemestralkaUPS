package upsSP;

import javax.swing.*;

import upsSP.GUI.Okno;
import upsSP.Server.Spojeni;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        // Získání instance klienta
        try {
            Spojeni client = Spojeni.getInstance();

            // Odeslání zprávy na server a přečtení odpovědi
            String response = client.sendMessage("Mess:Nazdar nezajdem na jedno?!");
            System.out.println("Odpověď od serveru: " + response);
            SwingUtilities.invokeLater(() -> {
                Okno okno = new Okno();

                okno.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

//        SwingUtilities.invokeLater(() -> {
//            Okno okno = new Okno();
//
//            okno.setVisible(true);
//        });
    }
}