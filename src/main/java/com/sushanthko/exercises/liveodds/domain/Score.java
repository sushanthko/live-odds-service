package com.sushanthko.exercises.liveodds.domain;

/**
 * The {@link Score} class represents the score of a match on the scoreboard. It holds information such as the number of
 * goals scored by the home and away teams
 */
public record Score(int homeTeamGoals, int awayTeamGoals) {
}
