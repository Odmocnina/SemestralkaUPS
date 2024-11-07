package upsSP.VolbyTahu;

import upsSP.Nastroje.Konstanty;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Papir implements IVolba {

    public Papir() {

    }

    public String ziskejNazev() {
        return "Papír";
    }

    public String ziskejNazevSoubor() {
        return "Papir.png";
    }

    public int ziskejHodnotu() {
        return Konstanty.HODNOTA_PAPIR;
    }
}