import java.io.File;
import java.util.*;
import java.util.Map.Entry;

public class Words {

    private static final List<Character> VOWELS = Arrays.asList('A', 'E', 'I', 'O', 'U');

    public static class Word {

        private final int line;
        private final String text;

        public Word(int line, String text) {
            this.line = line;
            this.text = text;
        }

        public int getLine() {
            return line;
        }

        public String getText() {
            return text;
        }

    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("É necessário especificar um arquivo.");
            System.exit(-1);

            return;
        }

        final String path = args[0];
        final File file = new File(path);

        if (!file.exists() || file.isDirectory()) {
            System.out.println("Arquivo inválido.");

            return;
        }

        final List<Word> words = getWords(file);
        final Map<Character, Integer> vowelCount = getVowelCount(words);
        final char oftenestVowel = getOftenestVowel(vowelCount);
        final int firstLiteral = fetchLiteral("ção", words);

        for (String line : new String[] {
                "Arquivo " + file.getName(),
                "Resultados:",
                "* Palavras: " + words.size(),
                "* Vogal mais encontrada: " + oftenestVowel,
                "* Primeira aparição de 'ção': linha " + firstLiteral
        }) {
            System.out.println(line);
        }
    }

    public static List<Word> getWords(File file) {
        final List<Word> words = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            int cursor = 1;

            while (scanner.hasNextLine()) {
                final int fileLine = cursor;
                final String line = scanner.nextLine();

                words.addAll(
                        Arrays.stream(line.split(" "))
                                .map((data) -> new Word(fileLine, data))
                                .toList()
                );

                cursor++;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return words;
    }

    public static Map<Character, Integer> getVowelCount(List<Word> words) {
        final Map<Character, Integer> count = new HashMap<>();

        for (Word word : words) {
            final String text = word.getText();
            final char[] characters = text.toCharArray();

            for (char fragment : characters) {
                final char upper = Character.toUpperCase(fragment);

                if (VOWELS.contains(upper)) {
                    count.put(upper, count.getOrDefault(fragment, 0) + 1);
                }
            }
        }

        return count;
    }

    public static char getOftenestVowel(Map<Character, Integer> vowels) {
        final LinkedList<Entry<Character, Integer>> entries = new LinkedList<>(vowels.entrySet());

        entries.sort(Entry.comparingByValue());

        return entries
                .getLast()
                .getKey();
    }

    public static int fetchLiteral(String literal, List<Word> words) {
        for (Word word : words) {
            final String text = word.getText();

            if (text.contains(literal)) {
                return word.getLine();
            }
        }

        return -1;
    }

}
