# live-odds-service
This is a simple library implementation of the **Live Football World Cup Score Board**.

## Usage
The methods defined in the `com.sushanthko.exercises.liveodds.service.Scoreboard` class allow to perform various actions on the scoreboard.
```java
// Initialize a scoreboard object
Scoreboard scoreboard = new Scoreboard();

// Starts a match and returns the started match
Match match = scoreboard.startMatch('homeTeam', 'awayTeam');

// Returns 'homeTeam 0 - awayTeam 0'
scoreboard.getScore(match);

// Updates the score
scoreboard.updateScore(match, 1, 0);

// Returns 'homeTeam 1 - awayTeam 0'
scoreboard.getScore(match);

// Returns '1. homeTeam 1 - awayTeam 0'
scoreboard.getSummary();

// Ends the match 
scoreboard.finishMatch(match);

// Throws exception with the message 'Match finished or is yet to start'
scoreboard.getScore(match);
```

## Running tests
1. Clone the repository

    ```shell
    git clone git@github.com:sushanthko/live-odds-service.git
    ```

2. Run tests in the project directory
   
   **Requirements**: Java 21

    ```shell
    cd live-odds-service
   ./gradlew test
    ```