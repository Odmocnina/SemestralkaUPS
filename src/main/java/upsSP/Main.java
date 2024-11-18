package upsSP;

import javax.swing.*;

import upsSP.GUI.Window;
import upsSP.Server.Connection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        // Získání instance klienta
//        try {
//            Connection client = Connection.getInstance();
//
//            // Odeslání zprávy na server a přečtení odpovědi
//            String response = client.sendMessage("Mess:Nazdar nezajdem na jedno?!");
//            System.out.println("Odpověď od serveru: " + response);
//            SwingUtilities.invokeLater(() -> {
//                Window okno = new Window();
//
//                okno.setVisible(true);
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        SwingUtilities.invokeLater(() -> {
            Window window = new Window();

            window.setVisible(true);
        });
    }
}