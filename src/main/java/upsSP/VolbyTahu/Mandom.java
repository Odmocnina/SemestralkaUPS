package upsSP.VolbyTahu;

import upsSP.Nastroje.Constants;
import java.util.Random;

public class Mandom implements ITurn {

    public Mandom() {

    }

    public String getNameOfTurn() {
        return "Náhoda";
    }

    public String getNameOfPictureFile() {
        return "Nahoda.png";
    }

    public int getValue() {
        //java.util.Random nahoda = new java.util.Random();
        Random random = new Random();
        return random.nextInt(Constants.NUMBER_OF_TURNS) + 1;
    }
}
