package upsSP.VolbyTahu;

import upsSP.Nastroje.Konstanty;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Tapir implements IVolba {

    public Tapir() {

    }

    public String ziskejNazev() {
        return "Tapír";
    }

    public String ziskejNazevSoubor() {
        return "Tapir.png";
    }

    public int ziskejHodnotu() {
        return Konstanty.HODNOTA_TAPIR;
    }
}
