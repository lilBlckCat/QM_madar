import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputHandler {
    private int numVariables;
    private List<Integer> minterms;
    private List<Integer> dontCares;

    public void readInput() {
        Scanner scanner = new Scanner(System.in);

        // دریافت تعداد متغیرها( 1 تا 6)
        while (true) {
            System.out.print("تعداد متغییرهای تابع را(بین یک تا شیش )وارد کنید");
            numVariables = scanner.nextInt();
            scanner.nextLine(); // مصرف کردن باقی‌مانده‌ی خط
            if (numVariables >= 1 && numVariables <= 6) {
                break;
            } else {
                System.out.println("گفتم بین یک تا شیش بچ!");
            }
        }

        // دریافت مینترم‌ها
        System.out.print("مینترمهای تابع را وارد کنید و انها را با کاما از یکدیگر جدا کنید: ");
        String mintermLine = scanner.nextLine();
        minterms = parseInputList(mintermLine);

        // دریافت don't care ها (اختیاری)
        System.out.print("دونت کرها را وارد کنید و اگر ندارید غفط اینتر را بزنید. ");
        String dontCareLine = scanner.nextLine();
        if (dontCareLine.trim().isEmpty()) {
            dontCares = new ArrayList<>();
        } else {
            dontCares = parseInputList(dontCareLine);
        }
    }

    // تبدیل رشته‌ی ورودی به لیست اعداد صحیح
    private List<Integer> parseInputList(String input) {
        List<Integer> result = new ArrayList<>();
        String[] parts = input.split(",");
        for (String part : parts) {
            try {
                result.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number ignored: " + part);
            }
        }
        return result;
    }

    // متدهای دسترسی
    public int getNumVariables() {
        return numVariables;
    }

    public List<Integer> getMinterms() {
        return minterms;
    }

    public List<Integer> getDontCares() {
        return dontCares;
    }
}
