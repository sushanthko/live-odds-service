package com.sushanthko.exercises.liveodds.service

import spock.lang.Specification

class ScoreboardSpecification extends Specification {
    def "Start a match and add it to the scoreboard"() {
        given: "A scoreboard"
        def scoreBoard = new ScoreBoard()

        when: "Start a match"
        def match = scoreBoard.startMatch('Home team', 'Away team')

        then: "Get the score"
        scoreBoard.getScore(match) == 'Home team 0 - Away team 0'

        when: "Update the score"
        scoreBoard.updateScore(match, 1, 0)

        then: "Get the updated score"
        scoreBoard.getScore(match) == 'Home team 1 - Away team 0'

        when: "Finish the match and try to get its score"
        scoreBoard.finishMatch(match)
        scoreBoard.getScore(match)

        then: "Match is removed from the scoreboard"
        def exception = thrown(Exception)
        exception.message == 'Match finished or is yet to start'
    }

    def "Get a summary of the matches in progress"() {
        given: "A scoreboard"
        def scoreBoard = new ScoreBoard()

        and: "A list of matches"
        def matchPairs = [['Mexico': 'Canada'], ['Spain': 'Brazil'], ['Germany': 'France'], ['Uruguay': 'Italy'],
                          ['Argentina': 'Australia']]

        when: "Each match is started"
        matchPairs.forEach {
            it.each {
                homeTeam, awayTeam -> scoreBoard.startMatch(homeTeam, awayTeam)
            }
        }

        and: "Scores are updated"
        def scores = [[0: 5], [10: 2], [2: 2], [6: 6], [3: 1]]

        scoreBoard.matches.eachWithIndex { match, index ->
            {
                scores.get(index).each {
                    homeTeamGoals, awayTeamGoals -> scoreBoard.updateScore(match, homeTeamGoals, awayTeamGoals)
                }
            }
        }

        then: "Get the summary of the matches in progress"
        scoreBoard.summary ==
                '1. Uruguay 6 - Italy 6\n' +
                '2. Spain 10 - Brazil 2\n' +
                '3. Mexico 0 - Canada 5\n' +
                '4. Argentina 3 - Australia 1\n' +
                '5. Germany 2 - France 2'
    }

    def "Do not allow a team which is part of a match in progress to start a new match"() {
        given: "A scoreboard"
        def scoreboard = new ScoreBoard()

        and: "Start a match"
        scoreboard.startMatch('Japan', 'Australia')

        when: "Start second match"
        scoreboard.startMatch('Norway', 'japan')

        then: "An exception is thrown"
        def exception = thrown(Exception)
        exception.message == 'japan is part of a match in progress'
    }

    def "Do not allow a match to be updated when negative number of goals is supplied"() {
        given: "A scoreboard"
        def scoreboard = new ScoreBoard()

        and: "Start a match"
        def match = scoreboard.startMatch('Brazil', 'Mexico')

        when: "Update the score with invalid values"
        scoreboard.updateScore(match, -5, 2)

        then: "An exception is thrown"
        def exception = thrown(Exception)
        exception.message == 'The number of goals for a team cannot be negative'
    }

    def "Do not allow team names to be null or blank"() {
        given: "A scoreboard"
        def scoreboard = new ScoreBoard()

        when: "Start a match"
        scoreboard.startMatch(homeTeam, awayTeam)

        then: "An exception is thrown"
        def exception = thrown(Exception)
        exception.message == message

        where:
        homeTeam  | awayTeam | message
        'England' | null     | 'Away team cannot be null'
        ''        | 'Canada' | 'Home team cannot be blank'
    }
}
