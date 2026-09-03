package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class FileUtils {
    public static List<String> readAllTokens(File file) throws IOException {
        List<String> tokens = new LinkedList<>();
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\\s+");
                for (String token : split) {
                    String cleanToken = token.replaceFirst("^\\p{Punct}*", "")
                            .replaceFirst("\\p{Punct}*$", "")
                            .toLowerCase()
                            .trim();
                    if (!cleanToken.isEmpty()) {
                        tokens.add(cleanToken);
                    }
                }
            }
        }
        return tokens;
    }
}
