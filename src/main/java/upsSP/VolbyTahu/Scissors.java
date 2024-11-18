package upsSP.VolbyTahu;

import upsSP.Nastroje.Konstanty;

public class Scissors implements ITurn {

    public Scissors() {

    }

    public String getNameOfTurn() {
        return "Nůžky";
    }

    public String getNameOfPictureFile() {
        return "Nuzky.png";
    }

    public int getValue() {
        return Konstanty.HODNOTA_NUZKY;
    }
}
