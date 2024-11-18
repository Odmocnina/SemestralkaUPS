package upsSP.VolbyTahu;

import upsSP.Nastroje.Konstanty;

public class Stone implements ITurn {

    public Stone() {

    }

    public String getNameOfTurn() {
        return "Kámen";
    }

    public String getNameOfPictureFile() {
        return "Kamen.png";
    }

    public int getValue() {
        return Konstanty.HODNOTA_KAMEN;
    }
}
