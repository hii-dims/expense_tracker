package app.core;

import app.utils.AppLogger;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private static final String DATA_DIR = "app_data/data";
    private static final String FILE_PATH = DATA_DIR + "/expenses.csv";
    private final List<String[]> expenses;

    public ExpenseManager() {
        this.expenses = new ArrayList<>();
        ensureDataDirectoryExists();
        loadExpenses();
    }

    private void ensureDataDirectoryExists() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void addExpense(String category, double amount, String description) {
        String record = category + "," + amount + "," + description;
        expenses.add(new String[]{category, String.valueOf(amount), description});
        saveToFile();
        AppLogger.logInfo("Added expense: " + category + " - $" + amount);
    }

    public List<String[]> getExpenses() {
        return expenses;
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (String[] exp : expenses) {
                writer.println(String.join(",", exp));
            }
        } catch (IOException e) {
            AppLogger.logError("Error saving expenses: " + e.getMessage());
        }
    }

    private void loadExpenses() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    expenses.add(parts);
                }
            }
            AppLogger.logInfo("Loaded " + expenses.size() + " expenses from storage.");
        } catch (IOException e) {
            AppLogger.logError("Error loading expenses: " + e.getMessage());
        }
    }
}
