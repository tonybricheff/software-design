import com.brichev.calc.Calculator;

public class Main {
    public static void main(String[] args) {
        var calculator = new Calculator();
        calculator.calculate("(30 + 2) / 8");
        calculator.calculate("(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5)");
    }
}
