package app;

import app.core.ExpenseManager;
import app.core.FinancialAdvisor;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    private ExpenseManager expenseManager;
    private TableView<ExpenseRecord> table;
    private TextArea insightsArea;

    // Helper record class for JavaFX TableView mapping
    public static class ExpenseRecord {
        private final String category;
        private final double amount;
        private final String description;

        public ExpenseRecord(String category, double amount, String description) {
            this.category = category;
            this.amount = amount;
            this.description = description;
        }

        public String getCategory() { return category; }
        public double getAmount() { return amount; }
        public String getDescription() { return description; }
    }

    @Override
    public void start(Stage primaryStage) {
        expenseManager = new ExpenseManager();
        primaryStage.setTitle("Smart Expense Tracker & Financial Advisor (Desktop)");

        // Main Layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f6f8;");

        // Title Header
        Label titleLabel = new Label("📊 Smart Expense Tracker Dashboard");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Table View for Expenses
        table = new TableView<>();
        table.setPrefHeight(200);

        TableColumn<ExpenseRecord, String> catCol = new TableColumn<>("Category");
        catCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        catCol.setPrefWidth(120);

        TableColumn<ExpenseRecord, Double> amtCol = new TableColumn<>("Amount ($)");
        amtCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amtCol.setPrefWidth(100);

        TableColumn<ExpenseRecord, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descCol.setPrefWidth(230);

        table.getColumns().addAll(catCol, amtCol, descCol);
        loadTableData();

        // Input Form
        HBox form = new HBox(10);
        TextField catInput = new TextField();
        catInput.setPromptText("Category (e.g. Food)");
        TextField amtInput = new TextField();
        amtInput.setPromptText("Amount ($)");
        TextField descInput = new TextField();
        descInput.setPromptText("Description");

        Button addButton = new Button("Add Expense");
        addButton.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-weight: bold;");

        form.getChildren().addAll(catInput, amtInput, descInput, addButton);

        // AI Financial Insights Panel
        Label insightsLabel = new Label("🤖 AI Financial Advisor Insights:");
        insightsLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1976d2;");
        
        insightsArea = new TextArea();
        insightsArea.setEditable(false);
        insightsArea.setPrefHeight(120);
        insightsArea.setStyle("-fx-control-inner-background: #ffffff;");
        refreshInsights();

        // Button Action Logic
        addButton.setOnAction(e -> {
            try {
                String cat = catInput.getText().trim();
                double amt = Double.parseDouble(amtInput.getText().trim());
                String desc = descInput.getText().trim();

                if (!cat.isEmpty() && amt > 0) {
                    expenseManager.addExpense(cat, amt, desc);
                    loadTableData();
                    refreshInsights();
                    catInput.clear();
                    amtInput.clear();
                    descInput.clear();
                }
            } catch (NumberFormatException ex) {
                insightsArea.setText("⚠️ Error: Please enter a valid numeric amount.");
            }
        });

        root.getChildren().addAll(titleLabel, table, form, insightsLabel, insightsArea);

        Scene scene = new Scene(sceneSetup(root), 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane sceneSetup(VBox root) {
        return root;
    }

    private void loadTableData() {
        table.getItems().clear();
        List<String[]> rawExpenses = expenseManager.getExpenses();
        for (String[] exp : rawExpenses) {
            table.getItems().add(new ExpenseRecord(exp[0], Double.parseDouble(exp[1]), exp[2]));
        }
    }

    private void refreshInsights() {
        List<String[]> rawExpenses = expenseManager.getExpenses();
        if (rawExpenses.isEmpty()) {
            insightsArea.setText("No expenses recorded yet. Add an expense above to get AI financial advice!");
            return;
        }

        double total = 0;
        java.util.Map<String, Double> cats = new java.util.HashMap<>();
        for (String[] exp : rawExpenses) {
            double amt = Double.parseDouble(exp[1]);
            total += amt;
            cats.put(exp[0], cats.getOrDefault(exp[0], 0.0) + amt);
        }

        String highestCat = "";
        double maxAmt = -1;
        for (var entry : cats.entrySet()) {
            if (entry.getValue() > maxAmt) {
                maxAmt = entry.getValue();
                highestCat = entry.getKey();
            }
        }

        double pct = (maxAmt / total) * 100;
        StringBuilder sb = new StringBuilder();
        sb.append("• Total Lifetime Spending: $").append(String.format("%.2f", total)).append("\n");
        sb.append("• Highest Spending Category: ").append(highestCat).append(" (").append(String.format("%.1f", pct)).append("% of total)\n");
        
        if (pct > 40.0) {
            sb.append("⚠️ Warning: You are spending a high percentage on '").append(highestCat).append("'. Consider setting a budget cap.");
        } else {
            sb.append("✅ Great job! Your spending is well-balanced across categories.");
        }

        insightsArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
