package com.sushanthko.exercises.liveodds.service;

import com.sushanthko.exercises.liveodds.domain.Match;
import com.sushanthko.exercises.liveodds.domain.Score;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ScoreBoard {
    private final List<Match> matches = new ArrayList<>();

    /**
     * Starts a match
     *
     * @param homeTeam name of the home team
     * @param awayTeam name of the away team
     * @return a reference to the started {@link Match}
     */
    public Match startMatch(String homeTeam, String awayTeam) {
        Objects.requireNonNull(homeTeam, "Home team cannot be null");
        Objects.requireNonNull(awayTeam, "Away team cannot be null");

        if (homeTeam.isBlank()) {
            throw new RuntimeException("Home team cannot be blank");
        }

        if (awayTeam.isBlank()) {
            throw new RuntimeException("Away team cannot be blank");
        }

        homeTeam = homeTeam.trim();
        awayTeam = awayTeam.trim();

        List<String> teams = collectTeamsInLowerCase();

        if (teams.contains(homeTeam.toLowerCase())) {
            throw new RuntimeException(String.format("%s is part of a match in progress", homeTeam));
        }

        if (teams.contains(awayTeam.toLowerCase())) {
            throw new RuntimeException(String.format("%s is part of a match in progress", awayTeam));
        }

        Score score = new Score(0, 0);

        Match match = new Match(homeTeam, awayTeam, score);

        matches.add(match);

        return match;
    }

    /**
     * Returns the score of a match as a string
     *
     * @param match a {@link Match} in progress
     * @return the string representation of the match score
     */
    public String getScore(Match match) {
        Match foundMatch = findMatch(match);

        Score score = foundMatch.getScore();

        return String.format("%s %s - %s %s", foundMatch.getHomeTeam(), score.homeTeamGoals(), match.getAwayTeam(),
                score.awayTeamGoals());
    }

    /**
     * Updates the score of an ongoing match
     *
     * @param match         reference to a {@link Match}
     * @param homeTeamGoals number of goals scored by the home team
     * @param awayTeamGoals number of goals scored by the away team
     */
    public void updateScore(Match match, int homeTeamGoals, int awayTeamGoals) {
        Objects.requireNonNull(match, "Match cannot be null");

        if (homeTeamGoals < 0 || awayTeamGoals < 0) {
            throw new RuntimeException("The number of goals for a team cannot be negative");
        }

        Match foundMatch = findMatch(match);

        Score score = new Score(homeTeamGoals, awayTeamGoals);

        foundMatch.setScore(score);
    }

    /**
     * Finishes the match on the scoreboard
     *
     * @param match a {@link Match} to finish
     */
    public void finishMatch(Match match) {
        matches.remove(match);
    }

    /**
     * Get a list of ongoing matches
     *
     * @return a {@link List} of matches
     */
    public List<Match> getMatches() {
        return matches;
    }

    /**
     * Get the summary of the matches on the scoreboard
     *
     * @return a {@link String} representation of the match summary
     */
    public String getSummary() {
        Comparator<Match> byTotalScore = Comparator.comparingInt(Match::getTotalScore);

        matches.sort(byTotalScore);

        List<Match> sortedReverseOrderedMatches = matches.reversed();

        return IntStream
                .range(0, sortedReverseOrderedMatches.size())
                .mapToObj(i -> String.format("%s. %s", i + 1, getScore(sortedReverseOrderedMatches.get(i))))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Retrieves a match from the list of ongoing matches
     *
     * @param match a {@link Match}
     * @return a reference to the match, if found
     */
    private Match findMatch(Match match) {
        return matches
                .stream()
                .filter(matchInList -> matchInList == match ||
                        (Objects.equals(matchInList.getHomeTeam(), match.getHomeTeam())
                                && Objects.equals(matchInList.getAwayTeam(), match.getAwayTeam())))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Match finished or is yet to start"));
    }

    /**
     * Returns a list of all the teams that are involved in matches
     *
     * @return a {@link List} of lower case strings
     */
    private List<String> collectTeamsInLowerCase() {
        return matches
                .stream()
                .flatMap(match -> Stream.of(match.getHomeTeam().toLowerCase(), match.getAwayTeam().toLowerCase()))
                .collect(Collectors.toList());
    }
}
