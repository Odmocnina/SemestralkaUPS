package upsSP.VolbyTahu;

import upsSP.Nastroje.Konstanty;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Nahoda implements IVolba {

    public Nahoda() {

    }

    public String ziskejNazev() {
        return "Náhoda";
    }

    public String ziskejNazevSoubor() {
        return "Nahoda.png";
    }

    public int ziskejHodnotu() {
        Random nahoda = new Random();
        return nahoda.nextInt(Konstanty.POCET_MOZNOSTI) + 1;
    }
}
