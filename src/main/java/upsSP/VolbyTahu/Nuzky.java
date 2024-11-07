package upsSP.VolbyTahu;

import upsSP.Nastroje.Konstanty;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Nuzky implements IVolba {

    public Nuzky() {

    }

    public String ziskejNazev() {
        return "Nůžky";
    }

    public String ziskejNazevSoubor() {
        return "Nuzky.png";
    }

    public int ziskejHodnotu() {
        return Konstanty.HODNOTA_NUZKY;
    }
}
