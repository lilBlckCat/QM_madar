import java.util.*;
//این کلاسهای برای دسته بندی مینترمهاست.
public class TermGrouper {
    private final int numVariables;
    private final List<String>[] groups;

    @SuppressWarnings("unchecked")
    public TermGrouper(int numVariables) {
        this.numVariables = numVariables;
        this.groups = new ArrayList[numVariables + 1]; // حداکثر تعداد 1‌ها = numVariables
        for (int i = 0; i <= numVariables; i++) {
            groups[i] = new ArrayList<>();
        }
    }
    //گروه بندی بر اساس تعداد بیت 1
    public void groupTerms(List<Integer> terms) {
        for (int term : terms) {
            String binary = toBinary(term);
            int onesCount = countOnes(binary);
            groups[onesCount].add(binary);
        }
    }

    private String toBinary(int value) {
        String binary = Integer.toBinaryString(value);
        // پر کردن با صفر از چپ تا طول numVariables
        while (binary.length() < numVariables) {
            binary = "0" + binary;
        }
        return binary;
    }

    private int countOnes(String binary) {
        int count = 0;
        for (char bit : binary.toCharArray()) {
            if (bit == '1')
                count++;
        }
        return count;
    }

    public List<String>[] getGroups() {
        return groups;
    }

    // برای تست
    public void printGroups() {
        for (int i = 0; i < groups.length; i++) {
            System.out.println("Group " + i + ": " + groups[i]);
        }
    }
}

