package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	public static final int UNRANKED_CONST = 0;
	private HashMap<String, HashMapHistogram<String>> filesHistogram;
	private HashMap<String, HashMap<String, Integer>> ranksInFiles;

	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 */
  	public void indexDirectory(String folderPath) {
		//This code iterates over all the files in the folder. add your code wherever is needed
  		
		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		filesHistogram = new HashMap<String, HashMapHistogram<String>>();
		ranksInFiles = new HashMap<String, HashMap<String, Integer>>();
		for (File file : listFiles) {
			// for every file in the folder
			if (file.isFile()) {
				HashMapHistogram<String> fileHistogram = new HashMapHistogram<String>();
				try {
					fileHistogram.addAll(FileUtils.readAllTokens(file));
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
				filesHistogram.put(file.getName(), fileHistogram);
				int index = 1;
				for (String str: fileHistogram) {
					if (ranksInFiles.containsKey(str)) {
						ranksInFiles.get(str).put(file.getName(), index++);
					}
					else {
						HashMap<String, Integer> wordRanks = new HashMap<String, Integer>();
						wordRanks.put(file.getName(), index++);
						ranksInFiles.put(str, wordRanks);
					}
				}
			}
		}
	}
	
  	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getCountInFile(String filename, String word) throws FileIndexException{
		if (!filesHistogram.containsKey(filename)) {
			throw new FileIndexException("Filename not found in initialized index");
		}
		return filesHistogram.get(filename).getCountForItem(word.toLowerCase());
			
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getRankForWordInFile(String filename, String word) throws FileIndexException{
		if (!ranksInFiles.containsKey(word.toLowerCase()) || !ranksInFiles.get(word).containsKey(filename)) {
			throw new FileIndexException("Word or file not found in initialized index");
		}
		return ranksInFiles.get(word.toLowerCase()).get(filename); //replace this with the actual returned value

	}
	
	/*
	 * @pre: the index is initialized
	 * @pre word is not null
	 */
	public int getAverageRankForWord(String word){
		RankedWord wordRank = new RankedWord(word.toLowerCase(), ranksInFiles.get(word.toLowerCase()));
		return wordRank.getRankByType(RankedWord.rankType.average);
	}
	
	
	public List<String> getWordsWithAverageRankLowerThenK(int k){
		List<String> words = new ArrayList<String>();
		for (RankedWord word: getWordsLowerByParamter(RankedWord.rankType.average, k)) {
			words.add(word.getWord());
		}
		return words;
	}
	
	public List<String> getWordsBelowMinRank(int k){
		List<String> words = new ArrayList<String>();
		for (RankedWord word: getWordsLowerByParamter(RankedWord.rankType.min, k)) {
			words.add(word.getWord());
		}
		return words;
	}
	
	public List<String> getWordsAboveMaxRank(int k){
		List<String> words = new ArrayList<String>();
		for (RankedWord word: getWordsLowerByParamter(RankedWord.rankType.max, k)) {
			words.add(word.getWord());
		}
		return words;
	}
	
	public List<RankedWord> getWordsLowerByParamter(RankedWord.rankType rT, int k) {
		List<RankedWord> lowerRanks = new ArrayList<RankedWord>();
		for (String word: ranksInFiles.keySet()) {
			RankedWord wordRank = new RankedWord(word, ranksInFiles.get(word));
			if (wordRank.getRankByType(rT) <= k) {
				lowerRanks.add(wordRank);
			}
		}
		java.util.Collections.sort(lowerRanks, new RankedWordComparator(rT));
		return lowerRanks;
	}

}
