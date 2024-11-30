import com.sushanthko.exercises.liveodds.ScoreBoard
import spock.lang.Specification

class ScoreboardSpecification extends Specification{
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
        thrown Exception
    }
}
