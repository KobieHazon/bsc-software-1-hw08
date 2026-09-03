package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.util.Comparator;

import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;

class RankedWordComparator implements Comparator<RankedWord> {
    private final rankType comparisonType;

    RankedWordComparator(rankType comparisonType) {
        this.comparisonType = comparisonType;
    }

    @Override
    public int compare(RankedWord left, RankedWord right) {
        int rankComparison = Integer.compare(left.getRankByType(comparisonType), right.getRankByType(comparisonType));
        if (rankComparison != 0) {
            return rankComparison;
        }
        return left.getWord().compareTo(right.getWord());
    }
}
