package upsSP.Nastroje;

public class GameState {

    public int numberOfPlayedRounds = 0;

    public int numberOfWonRounds = 0;
    public int numberOfLostRounds = 0;
    public int numberOfSM = 0;
    public boolean gameInProgress = true;

    static GameState instance;
    public States stateOfGame;
    private GameState() {

    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public void setScores(int numberOfRounds, int numberOfStalemates, int numberOfWins) {
        //System.out.println("v metode");
        numberOfPlayedRounds = numberOfRounds;
        numberOfSM = numberOfStalemates;
        numberOfWonRounds = numberOfWins;
        numberOfLostRounds = numberOfRounds - numberOfWins - numberOfStalemates;
        //System.out.println("nastavene hodnoty " + numberOfPlayedRounds + " " + numberOfWonRounds + " "
        //        + numberOfLostRounds);
    }

    public void setState(States state) {
        if (state != null) {
            this.stateOfGame = state;
        }
    }

}
