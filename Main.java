import app.core.ExpenseManager;
import app.core.FinancialAdvisor;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Smart Expense Tracker...");
        ExpenseManager manager = new ExpenseManager();

        // Sample data insertion (if empty)
        if (manager.getExpenses().isEmpty()) {
            manager.addExpense("Food", 45.50, "Grocery run");
            manager.addExpense("Transport", 15.00, "Bus ticket");
            manager.addExpense("Shopping", 120.00, "New shoes");
        }

        // Display insights
        FinancialAdvisor.analyzeSpending(manager.getExpenses());
    }
}
