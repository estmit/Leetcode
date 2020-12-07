import java.util.*;

public class GroupAnagrams {
    private static class Anagram {
        private int[] charCount = new int[26];

        public Anagram(String s) {
            for (int i = 0; i < s.length(); ++i) {
                charCount['z' - s.charAt(i)]++;
            }
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof Anagram)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            Anagram other = (Anagram)obj;
            return Arrays.equals(this.charCount, other.charCount);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(charCount);
        }
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0) {
            return Collections.emptyList();
        }
        // option 1 - sort each string and then insert into Map<String, List<String>>
//         Map<String, List<String>> groups = new HashMap<>();
//         for (String s : strs) {
//             String sorted = sort(s);
//             groups.computeIfAbsent(sorted, k -> new ArrayList<>()).add(s);
//         }
//         return new ArrayList<>(groups.values());

        // option 2 - take advantage of the fact that str is lower-case ascii (address space=26)
        // instead of sorting, write string into a int[26] array where el = count
        // comparison = compare 2 arrays
        // but how to keep them grouped? you can't hash arrays out of the box
        Map<Anagram, List<String>> groups = new HashMap<>();
        for (String s : strs) {
            Anagram key = new Anagram(s);
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(groups.values());
    }

    private String sort(String input) {
        if (input.isEmpty()) {
            return input;
        }

        char[] chars = input.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
