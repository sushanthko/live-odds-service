package com.sushanthko.exercises.liveodds.service;

import com.sushanthko.exercises.liveodds.domain.Match;
import com.sushanthko.exercises.liveodds.domain.Score;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ScoreBoard {
    private final List<Match> matches = new ArrayList<>();

    /**
     * Start a match and return the {@link Match} reference
     * @param homeTeam name of the home team
     * @param awayTeam name of the away team
     * @return a reference to the started match
     */
    public Match startMatch(String homeTeam, String awayTeam) {
        Score score = new Score(0, 0);

        Match match = new Match(homeTeam, awayTeam, score);

        matches.add(match);

        return match;
    }

    public String getScore(Match match) {
        Match foundMatch = findMatch(match);

        Score score = foundMatch.getScore();

        return String.format("%s %s - %s %s", foundMatch.getHomeTeam(), score.homeTeamGoals(), match.getAwayTeam(),
                score.awayTeamGoals());
    }

    public void updateScore(Match match, int homeTeamGoals, int awayTeamGoals) {
        Match foundMatch = findMatch(match);

        Score score = new Score(homeTeamGoals, awayTeamGoals);

        foundMatch.setScore(score);
    }

    public void finishMatch(Match match) {
        matches.remove(match);
    }

    public List<Match> getMatches() {
        return matches;
    }

    private Match findMatch(Match match) {
        return matches
                .stream()
                .filter(matchInList -> matchInList == match ||
                        (Objects.equals(matchInList.getHomeTeam(), match.getHomeTeam())
                                && Objects.equals(matchInList.getAwayTeam(), match.getAwayTeam())))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Match finished or is yet to start"));
    }

    public String getSummary() {
        Comparator<Match> byTotalScore = Comparator.comparingInt(Match::getTotalScore);

        matches.sort(byTotalScore);

        List<Match> sortedReverseOrderedMatches = matches.reversed();

        return IntStream
                .range(0, sortedReverseOrderedMatches.size())
                .mapToObj(i -> String.format("%s. %s", i + 1, getScore(sortedReverseOrderedMatches.get(i))))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
