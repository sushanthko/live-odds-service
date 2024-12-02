package com.sushanthko.exercises.liveodds.util;

import com.sushanthko.exercises.liveodds.domain.Match;

import java.util.Comparator;

public class MatchComparator implements Comparator<Match> {

    @Override
    public int compare(Match match1, Match match2) {
        if (match1 == null) {
            return -1;
        } else if (match2 == null) {
            return 1;
        } else if (match1 == match2) {
            return 0;
        }

        return match1.getTotalScore() > match2.getTotalScore() ? -1 : 1;
    }
}
