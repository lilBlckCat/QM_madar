import java.util.*;

public class PrimeChart {
    private final Set<String> primeImplicants;
    private final List<Integer> minterms;
    private final int numVars;

    public PrimeChart(Set<String> primeImplicants, List<Integer> minterms, int numVars) {
        this.primeImplicants = primeImplicants;
        this.minterms = minterms;
        this.numVars = numVars;
    }
    //پیدا کردن EPIها
    public List<String> getEssentialPrimeImplicants() {
        Map<String, Set<Integer>> coverageMap = new HashMap<>();

        // Map: implicant → covered minterms
        for (String implicant : primeImplicants) {
            Set<Integer> covered = new HashSet<>();
            for (int minterm : minterms) {
                String bin = toBinary(minterm);
                if (matches(implicant, bin)) {
                    covered.add(minterm);
                }
            }
            if (!covered.isEmpty()) {
                coverageMap.put(implicant, covered);
            }
        }

        List<String> essentialImplicants = new ArrayList<>();
        Set<Integer> coveredMinterms = new HashSet<>();

        // پیدا کردن EPI ها
        for (int minterm : minterms) {
            int count = 0;
            String onlyCovering = null;
            for (String implicant : coverageMap.keySet()) {
                if (coverageMap.get(implicant).contains(minterm)) {
                    count++;
                    onlyCovering = implicant;
                }
            }
            if (count == 1 && onlyCovering != null) {
                if (!essentialImplicants.contains(onlyCovering)) {
                    essentialImplicants.add(onlyCovering);
                    coveredMinterms.addAll(coverageMap.get(onlyCovering));
                }
            }
        }

        // اضافه کردن غیر ضروری‌ها (در صورت نیاز برای پوشش کامل)
        while (coveredMinterms.size() < minterms.size()) {
            String best = null;
            int maxCover = 0;
            for (String implicant : coverageMap.keySet()) {
                if (essentialImplicants.contains(implicant)) continue;

                Set<Integer> uncovered = new HashSet<>(coverageMap.get(implicant));
                uncovered.removeAll(coveredMinterms);
                if (uncovered.size() > maxCover) {
                    maxCover = uncovered.size();
                    best = implicant;
                }
            }
            if (best != null) {
                essentialImplicants.add(best);
                coveredMinterms.addAll(coverageMap.get(best));
            } else {
                break;
            }
        }

        return essentialImplicants;
    }
    //برگردوندن عبارت بولی
    public String toBooleanExpression(List<String> implicants) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < implicants.size(); i++) {
            String term = implicants.get(i);
            for (int j = 0; j < term.length(); j++) {
                char c = term.charAt(j);
                if (c == '-') continue;
                sb.append((char) ('A' + j));
                if (c == '0') sb.append("'");
            }
            if (i != implicants.size() - 1) sb.append(" + ");
        }
        return sb.toString();
    }
    //بررسی پوشش دهی یک مینترم خاص توسط یک ایمپلیکنت
    private boolean matches(String implicant, String binary) {
        for (int i = 0; i < implicant.length(); i++) {
            if (implicant.charAt(i) != '-' && implicant.charAt(i) != binary.charAt(i)) {
                return false;
            }
        }
        return true;
    }
    //تبدیل عدد دهدهی به رشته باینری *با طول ثابت*
    private String toBinary(int number) {
        String bin = Integer.toBinaryString(number);
        while (bin.length() < numVars) {
            bin = "0" + bin;
        }
        return bin;
    }
}
