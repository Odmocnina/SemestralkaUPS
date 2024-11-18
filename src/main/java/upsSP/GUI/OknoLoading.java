package upsSP.GUI;

import javax.swing.*;
import java.awt.*;

public class OknoLoading extends JPanel {

    public OknoLoading(Window okno) {
        // Nastavení layoutu
        setLayout(new BorderLayout());

        // Načtení GIFu
        ImageIcon loadingIcon = new ImageIcon("src/main/resources/data/pravidla.png"); // Změň na správnou cestu k GIFu
        JLabel gifLabel = new JLabel(loadingIcon);

        // Text pod GIFem
        JLabel textLabel = new JLabel("Načítání...", SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Přidání komponentů do panelu
        add(gifLabel, BorderLayout.CENTER);
        add(textLabel, BorderLayout.SOUTH);
    }
}
