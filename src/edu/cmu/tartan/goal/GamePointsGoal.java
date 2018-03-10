package edu.cmu.tartan.goal;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.Player;

public class GamePointsGoal implements GameGoal {
    private Integer winningScore = 0;
    private Player player;

    public GamePointsGoal(Integer g, Player p) {
        this.winningScore = g;
        this.player = p;
    }

    @Override
    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append("The objective of this type of game is to score " + winningScore + " points" );
        return sb.toString();
    }

    @Override
    public Boolean isAchieved() {
        if (player.getScore() >= winningScore) {
            return true;
        }
        return false;
    }
}
