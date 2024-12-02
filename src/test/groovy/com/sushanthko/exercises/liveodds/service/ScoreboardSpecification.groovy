package com.sushanthko.exercises.liveodds.service

import spock.lang.Specification

class ScoreboardSpecification extends Specification {
    def "Start a match and add it to the scoreboard"() {
        given: "Given a scoreboard"
        def scoredBoard = new ScoreBoard()

        when: "Start a match"
        def match = scoredBoard.startMatch('Home team', 'Away team')

        then: "Get the score"
        scoredBoard.getScore(match) == 'Home team 0 - Away team 0'

        when: "Update the score"
        scoredBoard.updateScore(match, 1, 0)

        then: "Get the updated score"
        scoredBoard.getScore(match) == 'Home team 1 - Away team 0'

        when: "Finish the match and try to get its score"
        scoredBoard.finishMatch(match)
        scoredBoard.getScore(match)

        then: "Match is removed from the scoreboard"
        def exception = thrown(Exception)
        exception.message == 'Match finished or is yet to start'
    }

    def "Get a summary of the matches in progress"() {
        given: "Given a scoreboard"
        def scoredBoard = new ScoreBoard()

        and: "A list of matches"
        def matches = [['Mexico': 'Canada'], ['Spain': 'Brazil'], ['Germany': 'France'], ['Uruguay': 'Italy'],
                       ['Argentina': 'Australia']]

        when: "Each match is started"
        matches.forEach {
            it.each {
                homeTeam, awayTeam -> scoredBoard.startMatch(homeTeam, awayTeam)
            }
        }

        and: "Scores are updated"
        def scores = [[0: 5], [10: 2], [2: 2], [6: 6], [3: 1]]

        scoredBoard.matches.eachWithIndex { match, index ->
            {
                scores.get(index).each {
                    homeTeamGoals, awayTeamGoals -> scoredBoard.updateScore(match, homeTeamGoals, awayTeamGoals)
                }
            }
        }

        then: "Get the summary of the matches in progress"
        scoredBoard.summary == '1. Uruguay 6 - Italy 6\n' +
                '2. Spain 10 - Brazil 2\n' +
                '3. Mexico 0 - Canada 5\n' +
                '4. Argentina 3 - Australia 1\n' +
                '5. Germany 2 - France 2'
    }
}
