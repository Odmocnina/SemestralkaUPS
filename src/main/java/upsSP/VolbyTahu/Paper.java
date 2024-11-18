package upsSP.VolbyTahu;

import upsSP.Nastroje.Konstanty;

public class Paper implements ITurn {

    public Paper() {

    }

    public String getNameOfTurn() {
        return "Papír";
    }

    public String getNameOfPictureFile() {
        return "Papir.png";
    }

    public int getValue() {
        return Konstanty.HODNOTA_PAPIR;
    }
}