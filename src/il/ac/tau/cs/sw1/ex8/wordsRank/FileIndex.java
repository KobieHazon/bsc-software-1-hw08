package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;

public class FileIndex {
    public static final int UNRANKED_CONST = 0;

    private Map<String, HashMapHistogram<String>> filesHistogram = new HashMap<>();
    private Map<String, Map<String, Integer>> ranksInFiles = new HashMap<>();

    public void indexDirectory(String folderPath) {
        File folder = new File(folderPath);
        File[] listedFiles = folder.listFiles(File::isFile);
        if (listedFiles == null) {
            throw new IllegalArgumentException("folder must be a readable directory: " + folderPath);
        }
        Arrays.sort(listedFiles, (left, right) -> left.getName().compareTo(right.getName()));

        filesHistogram = new HashMap<>();
        ranksInFiles = new HashMap<>();
        for (File file : listedFiles) {
            HashMapHistogram<String> fileHistogram = new HashMapHistogram<>();
            try {
                fileHistogram.addAll(FileUtils.readAllTokens(file));
            } catch (IOException e) {
                throw new IllegalArgumentException("failed reading file: " + file, e);
            }
            filesHistogram.put(file.getName(), fileHistogram);
            indexRanks(file.getName(), fileHistogram);
        }
    }

    public int getCountInFile(String filename, String word) throws FileIndexException {
        ensureFileExists(filename);
        return filesHistogram.get(filename).getCountForItem(normalize(word));
    }

    public int getRankForWordInFile(String filename, String word) throws FileIndexException {
        ensureFileExists(filename);
        String normalizedWord = normalize(word);
        Map<String, Integer> wordRanks = ranksInFiles.get(normalizedWord);
        if (wordRanks == null || !wordRanks.containsKey(filename)) {
            throw new FileIndexException("Word or file not found in initialized index");
        }
        return wordRanks.get(filename);
    }

    public int getAverageRankForWord(String word) {
        Map<String, Integer> wordRanks = ranksInFiles.get(normalize(word));
        if (wordRanks == null) {
            return UNRANKED_CONST;
        }
        return new RankedWord(normalize(word), wordRanks).getRankByType(RankedWord.rankType.average);
    }

    public List<String> getWordsWithAverageRankLowerThenK(int k) {
        return getWordsByRankThreshold(RankedWord.rankType.average, k);
    }

    public List<String> getWordsBelowMinRank(int k) {
        return getWordsByRankThreshold(RankedWord.rankType.min, k);
    }

    public List<String> getWordsAboveMaxRank(int k) {
        return getWordsByRankThreshold(RankedWord.rankType.max, k);
    }

    public List<RankedWord> getWordsLowerByParamter(RankedWord.rankType rankType, int k) {
        List<RankedWord> lowerRanks = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : ranksInFiles.entrySet()) {
            RankedWord wordRank = new RankedWord(entry.getKey(), entry.getValue());
            if (wordRank.getRankByType(rankType) <= k) {
                lowerRanks.add(wordRank);
            }
        }
        lowerRanks.sort(new RankedWordComparator(rankType));
        return lowerRanks;
    }

    private void indexRanks(String filename, HashMapHistogram<String> fileHistogram) {
        int rank = 1;
        for (String word : fileHistogram) {
            ranksInFiles.computeIfAbsent(word, ignored -> new HashMap<>()).put(filename, rank);
            rank++;
        }
    }

    private List<String> getWordsByRankThreshold(RankedWord.rankType rankType, int k) {
        List<String> words = new ArrayList<>();
        for (RankedWord word : getWordsLowerByParamter(rankType, k)) {
            words.add(word.getWord());
        }
        return Collections.unmodifiableList(words);
    }

    private void ensureFileExists(String filename) throws FileIndexException {
        if (!filesHistogram.containsKey(filename)) {
            throw new FileIndexException("Filename not found in initialized index");
        }
    }

    private String normalize(String word) {
        if (word == null) {
            throw new IllegalArgumentException("word must not be null");
        }
        return word.toLowerCase(Locale.ROOT);
    }
}
