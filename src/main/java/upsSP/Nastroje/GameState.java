package upsSP.Nastroje;

public class GameState {

    public int pocetOdehranychKol = 0;

    public int pocetVyhranychKol = 0;
    public int pocetProhranychKol = 0;
    public int pocetRemiz = 0;
    public static boolean gameInProgress = true;

    static GameState instance;
    private GameState() {

    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public void setScores(int numberOfRounds, int numberOfStalemates, int numberOfWins) {
        System.out.println("v metode");
        pocetOdehranychKol = numberOfRounds;
        pocetRemiz = numberOfStalemates;
        pocetVyhranychKol = numberOfWins;
        pocetProhranychKol = numberOfRounds - numberOfWins - numberOfStalemates;
        System.out.println("nastavene hodnoty " + pocetOdehranychKol + " " + pocetVyhranychKol + " " + pocetProhranychKol);
    }

}
