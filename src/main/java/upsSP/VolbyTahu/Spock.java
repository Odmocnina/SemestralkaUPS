package upsSP.VolbyTahu;

import upsSP.Nastroje.Constants;

public class Spock implements ITurn {

    public Spock() {

    }

    public String getNameOfTurn() {
        return "Spock";
    }

    public String getNameOfPictureFile() {
        return "Spock.png";
    }

    public int getValue() {
        return Constants.SPOCK_VALUE;
    }
}
