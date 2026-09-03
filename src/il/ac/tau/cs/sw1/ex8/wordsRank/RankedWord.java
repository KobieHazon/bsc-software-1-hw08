package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class RankedWord {
    enum rankType {average, min, max}

    private final String word;
    private final Map<String, Integer> ranksForFile;
    private final int average;
    private final int min;
    private final int max;

    RankedWord(String word, Map<String, Integer> ranks) {
        this.word = word;
        this.ranksForFile = new HashMap<>(ranks);
        this.min = Collections.min(ranksForFile.values());
        this.max = Collections.max(ranksForFile.values());
        int sum = 0;
        for (Integer rank : ranksForFile.values()) {
            sum += rank;
        }
        this.average = (int) Math.round(((double) sum) / ranksForFile.size());
    }

    String getWord() {
        return word;
    }

    Map<String, Integer> getRanksForFile() {
        return new HashMap<>(ranksForFile);
    }

    int getRankByType(rankType rankType) {
        switch (rankType) {
            case average:
                return average;
            case min:
                return min;
            case max:
            default:
                return max;
        }
    }

    @Override
    public String toString() {
        return "RankedWord [word=" + word + ", ranksForFile=" + ranksForFile + ", average=" + average
                + ", min=" + min + ", max=" + max + "]";
    }
}
