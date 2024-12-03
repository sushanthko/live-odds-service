package com.sushanthko.exercises.liveodds.domain;

/**
 * The {@link Match} class represents a match on the scoreboard. It holds information about a match such as home and
 * away team names and the score
 */
public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private Score score;

    public Match(String homeTeam, String awayTeam, Score score) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.score = score;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public int getTotalScore() {
        return score.homeTeamGoals() + score.awayTeamGoals();
    }
}
