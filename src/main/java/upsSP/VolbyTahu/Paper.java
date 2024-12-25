package upsSP.VolbyTahu;

import upsSP.Nastroje.Constants;

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
        return Constants.PAPER_VALUE;
    }
}