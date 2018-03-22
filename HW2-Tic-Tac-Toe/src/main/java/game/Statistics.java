package game;

@SuppressWarnings("unused")
public class Statistics {

    private int gamesNumber;

    private int xWins;
    private int oWins;
    private int draws;

    void onXWin() {
        gamesNumber++;
        xWins++;
    }

    void onOWin() {
        gamesNumber++;
        oWins++;
    }

    void onDraw() {
        gamesNumber++;
        draws++;
    }

    public int getXWins() {
        return xWins;
    }

    public int getOWins() {
        return oWins;
    }

    public int getDraws() {
        return draws;
    }

    public int getGamesNumber() {
        return gamesNumber;
    }
}
