package com.sushanthko.exercises.liveodds.domain;

public class Match {
    private String homeTeam;
    private String awayTeam;
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
}
