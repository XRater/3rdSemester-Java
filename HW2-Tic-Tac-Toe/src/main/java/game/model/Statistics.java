package game.model;

class Statistics {

    private int gamesNumber;

    private int xWins;
    private int yWins;
    private int draws;

    void onXWin() {
        gamesNumber++;
        xWins++;
    }

    void onYWin() {
        gamesNumber++;
        yWins++;
    }

    void onDraw() {
        gamesNumber++;
        draws++;
    }
}
