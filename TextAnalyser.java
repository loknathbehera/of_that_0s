import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class TextAnalyzer {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        // 1. Read file
        String content = Files.readString(Paths.get("moby.txt"));

        // 2. Tokenize words (split by non-letters)
        String[] words = content.split("\\W+");

        // 3. Define stopwords
        Set<String> stopwords = Set.of(
            "in","on","at","for","with","by","about","against","between","into","through",
            "during","before","after","above","below","to","from","up","down","over","under",
            "again","further","then","once",
            "he","she","it","they","we","you","i","me","him","her","us","them",
            "and","or","but","if","because","as","until","while",
            "the","a","an",
            "is","was","were","be","been","being","am","are"
        );

        // 4. Count words
        Map<String, Integer> wordCount = new HashMap<>();
        for (String w : words) {
            String word = w.toLowerCase().trim();

            if (word.isEmpty()) continue;
            if (stopwords.contains(word)) continue;
            if (word.endsWith("'s")) word = word.substring(0, word.length() - 2);

            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        // 5. Total count
        int totalWords = wordCount.values().stream().mapToInt(Integer::intValue).sum();

        // 6. Top 5 words
        List<Map.Entry<String, Integer>> top5 =
            wordCount.entrySet().stream()
                     .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                     .limit(5)
                     .toList();

        // 7. Unique words (alphabetical)
        List<String> uniqueWords = new ArrayList<>(wordCount.keySet());
        Collections.sort(uniqueWords);
        List<String> top50Unique = uniqueWords.stream().limit(50).toList();

        long end = System.currentTimeMillis();
        long processingTime = (end - start) / 1000;

        // 8. Output
        System.out.println("Total Word Count (filtered): " + totalWords);
        System.out.println("\nTop 5 Words:");
        for (Map.Entry<String, Integer> e : top5) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
        System.out.println("\nTop 50 Unique Words:");
        top50Unique.forEach(System.out::println);

        System.out.println("\nProcessing Time: " + processingTime + "s");
    }
}
