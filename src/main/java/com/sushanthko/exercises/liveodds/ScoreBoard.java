package com.sushanthko.exercises.liveodds;

import com.sushanthko.exercises.liveodds.domain.Match;
import com.sushanthko.exercises.liveodds.domain.Score;

public class ScoreBoard {
    public Match startMatch(String homeTeam, String awayTeam) {
        Score score = new Score(0, 0);
        Match match = new Match(homeTeam, awayTeam, score);

        return match;
    }

    public String getScore(Match match) {
        Score score = match.getScore();

        return String.format("%s %s - %s %s", match.getHomeTeam(), score.homeGoals(), match.getAwayTeam(),
                score.awayGoals());
    }

    public void updateScore(Match match, Integer homeTeamGoals, Integer awayTeamGoals) {
        Score score = new Score(homeTeamGoals, awayTeamGoals);

        match.setScore(score);
    }
}
