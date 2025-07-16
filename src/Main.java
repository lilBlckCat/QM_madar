import java.util.*;
public class Main {
    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        inputHandler.readInput();

        List<Integer> allTerms = new ArrayList<>();
        allTerms.addAll(inputHandler.getMinterms());
        allTerms.addAll(inputHandler.getDontCares());

        QMEngine engine = new QMEngine(inputHandler.getNumVariables(), allTerms);
        Set<String> primeImplicants = engine.findPrimeImplicants();

        PrimeChart chart = new PrimeChart(
                primeImplicants,
                inputHandler.getMinterms(),
                inputHandler.getNumVariables()
        );

        List<String> finalImplicants = chart.getEssentialPrimeImplicants();
        String booleanExpression = chart.toBooleanExpression(finalImplicants);

        System.out.println("\nFinal Simplified Boolean Expression:");
        System.out.println(booleanExpression);
        }
    }
