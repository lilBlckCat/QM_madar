import java.util.*;

public class QMEngine {
    private final int numVariables;
    private final List<Integer> allTerms;

    public QMEngine(int numVariables, List<Integer> allTerms) {
        this.numVariables = numVariables;
        this.allTerms = allTerms;
    }
    //پیدا کردنPIها
    public Set<String> findPrimeImplicants() {
        Set<String> primeImplicants = new HashSet<>();

        // مرحله اول: تبدیل به باینری و گروه‌بندی
        TermGrouper grouper = new TermGrouper(numVariables);
        grouper.groupTerms(allTerms);

        List<String> currentTerms = new ArrayList<>();
        for (List<String> group : grouper.getGroups()) {
            currentTerms.addAll(group);
        }

        boolean continueCombining = true;

        while (continueCombining) {
            TermGrouper tempGrouper = new TermGrouper(numVariables);
            for (String term : currentTerms) {
                int ones = countOnes(term);
                tempGrouper.getGroups()[ones].add(term);
            }

            Combiner combiner = new Combiner(tempGrouper.getGroups());
            List<String> combined = combiner.combine();

            // ذخیره‌ی ترم‌هایی که ترکیب نشدن
            primeImplicants.addAll(combiner.getUncombinedTerms());

            // اگر دیگه ترم جدیدی تولید نشد، متوقف شو
            if (combined.isEmpty()) {
                continueCombining = false;
            } else {
                currentTerms = combined;
            }
        }

        return primeImplicants;
    }
    //شمردن تعداد بیتهای 1
    private int countOnes(String binary) {
        int count = 0;
        for (char c : binary.toCharArray()) {
            if (c == '1') count++;
        }
        return count;
    }
}


