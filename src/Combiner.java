
import java.util.*;
//ادغام مینترمهایی که فقط در یک بیت تفاوت دارن
public class Combiner {
    private final List<String>[] groups;
    private final Set<String> combinedTerms;
    private final Set<String> uncombinedTerms;

    public Combiner(List<String>[] groups) {
        this.groups = groups;
        this.combinedTerms = new HashSet<>();
        this.uncombinedTerms = new HashSet<>();
    }
    //وظیفه ادغام با این متود
    public List<String> combine() {
        Set<String> nextStageTerms = new HashSet<>();

        boolean[] marked = new boolean[groups.length];

        for (int i = 0; i < groups.length - 1; i++) {
            List<String> groupA = groups[i];
            List<String> groupB = groups[i + 1];

            for (String a : groupA) {
                boolean isCombined = false;

                for (String b : groupB) {
                    String combined = tryCombine(a, b);
                    if (combined != null) {
                        isCombined = true;
                        combinedTerms.add(a);
                        combinedTerms.add(b);
                        nextStageTerms.add(combined);
                    }
                }

                if (!isCombined) {
                    uncombinedTerms.add(a);
                }
            }
        }

        // بررسی ترم‌های آخرین گروه که ممکنه ترکیب نشده باقی مونده باشن
        for (String term : groups[groups.length - 1]) {
            if (!combinedTerms.contains(term)) {
                uncombinedTerms.add(term);
            }
        }

        return new ArrayList<>(nextStageTerms);
    }
    //بررسی تعداد اختلاف مینترمها
    private String tryCombine(String a, String b) {
        int diffCount = 0;
        StringBuilder combined = new StringBuilder();

        for (int i = 0; i < a.length(); i++) {
            char c1 = a.charAt(i);
            char c2 = b.charAt(i);

            if (c1 == c2) {
                combined.append(c1);
            } else {
                diffCount++;
                combined.append('-');
            }

            if (diffCount > 1) {
                return null;
            }
        }

        return diffCount == 1 ? combined.toString() : null;
    }

    public Set<String> getUncombinedTerms() {
        return uncombinedTerms;
    }
}
