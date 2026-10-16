# 📊 Smart Expense Tracker & Financial Advisor

Hey there! This is a desktop application I built to make personal expense tracking simple, visual, and intelligent. Instead of building just another command-line script, I wanted to create a native desktop app with a graphical user interface (GUI), local file persistence, automated logging, and a financial advisor that gives feedback on spending habits.

---

## ✨ What It Can Do

- **Interactive Desktop Dashboard:** Built using JavaFX, featuring a live-updating table and clean input forms to add and view transactions instantly.
- **Local Data Persistence:** Automatically saves and loads history securely using structured CSV files (`app_data/data/expenses.csv`).
- **Smart Financial Insights:** Analyzes spending distribution across categories, calculates highest spending percentages, and triggers warnings for overspending.
- **System Logging:** Records runtime events and operational history cleanly into (`app_data/logs/app.log`).
- **Automated CI/CD:** Connected with GitHub Actions so every push automatically runs Maven build verification.

---

## 🏗️ Project Architecture

- **core/ExpenseManager.java:** Handles reading and writing transactions to CSV securely.
- **core/FinancialAdvisor.java:** Contains spending analysis and budgeting logic.
- **utils/AppLogger.java:** Centralized file logging utility.
- **Main.java:** JavaFX GUI window layout, table, and buttons.
- **Launcher.java:** Thread-safe entry point to handle macOS UI threading.

---

## 🚀 How to Run

1. **Prerequisites:** Java 17+ and Apache Maven installed.
2. **Open in IntelliJ IDEA:** Clone the repository and open the folder as an existing Maven project.
3. **Launch the App:** Run `Launcher.java` or `Main.java` inside IntelliJ to open the desktop GUI window.
