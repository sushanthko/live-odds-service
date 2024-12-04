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

/**
 * The {@link Scoreboard} class represents the live scoreboard. It holds information about all the matches in progress
 * and allows to view and update their scores and also provides a summary of matches
 */
public class Scoreboard {
    private final List<Match> matches = new ArrayList<>();

    /**
     * Starts a match
     *
     * @param homeTeam name of the home team
     * @param awayTeam name of the away team
     * @return a reference to the started {@link Match}
     */
    public Match startMatch(String homeTeam, String awayTeam) {
        validateTeams(homeTeam, awayTeam);

        homeTeam = homeTeam.trim();
        awayTeam = awayTeam.trim();

        checkScoreboardPresence(homeTeam, awayTeam);

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
        Objects.requireNonNull(match, "Match cannot be null");

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

    /**
     * Validates the team names for null or blank values
     *
     * @param homeTeam the home team name
     * @param awayTeam the away team name
     */
    private void validateTeams(String homeTeam, String awayTeam) {
        Objects.requireNonNull(homeTeam, "Home team cannot be null");
        Objects.requireNonNull(awayTeam, "Away team cannot be null");

        if (homeTeam.isBlank()) {
            throw new RuntimeException("Home team cannot be blank");
        }

        if (awayTeam.isBlank()) {
            throw new RuntimeException("Away team cannot be blank");
        }
    }

    /**
     * Checks if either the home team or the away team is part of any ongoing match on the scoreboard
     *
     * @param homeTeam the home team name
     * @param awayTeam the away team name
     */
    private void checkScoreboardPresence(String homeTeam, String awayTeam) {
        List<String> teams = collectTeamsInLowerCase();

        if (teams.contains(homeTeam.toLowerCase())) {
            throw new RuntimeException(String.format("%s is part of a match in progress", homeTeam));
        }

        if (teams.contains(awayTeam.toLowerCase())) {
            throw new RuntimeException(String.format("%s is part of a match in progress", awayTeam));
        }
    }
}
