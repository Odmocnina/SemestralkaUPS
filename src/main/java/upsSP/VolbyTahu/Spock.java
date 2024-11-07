package upsSP.VolbyTahu;

import upsSP.Nastroje.Konstanty;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Spock implements IVolba {

    public Spock() {

    }

    public String ziskejNazev() {
        return "Spock";
    }

    public String ziskejNazevSoubor() {
        return "Spock.png";
    }

    public int ziskejHodnotu() {
        return Konstanty.HODNOTA_SPOCK;
    }
}
