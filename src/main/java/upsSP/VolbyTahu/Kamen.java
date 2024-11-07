package upsSP.VolbyTahu;

import upsSP.Nastroje.Konstanty;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Kamen implements IVolba {

    public Kamen() {

    }

    public String ziskejNazev() {
        return "Kámen";
    }

    public String ziskejNazevSoubor() {
        return "Kamen.png";
    }

    public int ziskejHodnotu() {
        return Konstanty.HODNOTA_KAMEN;
    }
}
