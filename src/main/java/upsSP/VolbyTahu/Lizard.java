package upsSP.VolbyTahu;

import upsSP.Nastroje.Constants;

public class Lizard implements ITurn {

    public Lizard() {

    }

    public String getNameOfTurn() {
        return "Tapír";
    }

    public String getNameOfPictureFile() {
        return "Tapir.png";
    }

    public int getValue() {
        return Constants.LIZZARD_VALUE;
    }
}
