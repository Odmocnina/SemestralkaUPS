package upsSP.Nastroje;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NacitacTextur extends JPanel {

    private TexturePaint textura;

    public NacitacTextur(String cestaKObr) {
        textura = nactiTexturu(cestaKObr);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (textura != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(textura);
            g2d.fill(new Rectangle2D.Float(0, 0, getWidth(), getHeight()));
        }
    }

    public static TexturePaint nactiTexturu(String cestaKObr) {
        try {
            // Načtení obrázku z cesty
            BufferedImage obrazek = ImageIO.read(new File("src/main/resources/data/" + cestaKObr));

            // Vytvoření obdélníku pro texturu
            Rectangle2D.Float ctverec = new Rectangle2D.Float(0, 0, obrazek.getWidth(), obrazek.getHeight());

            // Vytvoření TexturePaint z obrázku a obdélníku
            return new TexturePaint(obrazek, ctverec);
        } catch (IOException e) {
            e.printStackTrace(); // Zpracování výjimek
            return null; // Nebo vrátit nějakou výchozí texturu
        }
    }
}
