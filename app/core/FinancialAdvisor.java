package app.core;

import app.utils.AppLogger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinancialAdvisor {

    public static void analyzeSpending(List<String[]> expenses) {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet to analyze.");
            return;
        }

        double totalSpent = 0.0;
        Map<String, Double> categoryTotals = new HashMap<>();

        for (String[] exp : expenses) {
            String category = exp[0];
            double amount = Double.parseDouble(exp[1]);
            totalSpent += amount;
            categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
        }

        System.out.println("\n--- 🤖 Smart Financial Insights ---");
        System.out.println("Total Lifetime Spending: $" + String.format("%.2f", totalSpent));

        // Find highest spending category
        String highestCategory = "";
        double maxAmount = -1.0;
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.println("📁 Category [" + entry.getKey() + "]: $" + String.format("%.2f", entry.getValue()));
            if (entry.getValue() > maxAmount) {
                maxAmount = entry.getValue();
                highestCategory = entry.getKey();
            }
        }

        if (!highestCategory.isEmpty()) {
            double percentage = (maxAmount / totalSpent) * 100;
            System.out.println("\n💡 AI Financial Suggestion:");
            System.out.println("-> Your highest expense category is '" + highestCategory + 
                               "' accounting for " + String.format("%.1f", percentage) + "% of your total spending.");
            if (percentage > 40.0) {
                System.out.println("⚠️ Warning: You are spending a large portion on '" + highestCategory + 
                                   "'. Consider setting a stricter monthly cap here.");
            } else {
                System.out.println("✅ Great job keeping your spending balanced across categories!");
            }
        }
        System.out.println("-----------------------------------\n");
        AppLogger.logInfo("Generated financial insight analysis for " + expenses.size() + " expenses.");
    }
}
