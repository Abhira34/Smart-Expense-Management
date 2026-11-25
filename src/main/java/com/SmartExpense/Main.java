package com.SmartExpense;

import com.SmartExpense.model.Transaction;
import com.SmartExpense.model.TransactionType;
import com.SmartExpense.repository.InMemoryTransactionRepository;
import com.SmartExpense.repository.TransactionRepository;
import com.SmartExpense.service.TransactionService;

import java.math.BigDecimal;

import java.time.LocalDate;

import java.util.List;

import java.time.YearMonth;

public class Main {
    static void main(String[] args) {

        // Create the Repository
        TransactionRepository repository = new InMemoryTransactionRepository();

        // Create the Service
        TransactionService service = new TransactionService(repository);

        // Create Transactions
        Transaction salary = new Transaction(
                1,
                "Monthly Salary",
                new BigDecimal("40000.00"),
                TransactionType.INCOME,
                "Salary",
                LocalDate.of(2026, 10, 1)
        );

        Transaction groceries = new Transaction(
                2,
                "Groceries",
                new BigDecimal("2000.00"),
                TransactionType.EXPENSE,
                "Food",
                LocalDate.of(2026, 10, 5)
        );
        Transaction busFee = new Transaction(
                3,
                "Bus Pass",
                new BigDecimal("800.00"),
                TransactionType.EXPENSE,
                "Travel",
                LocalDate.of(2026, 10, 3)
        );
        Transaction freelance = new Transaction(
                4,
                "Freelance Work",
                new BigDecimal("5000.00"),
                TransactionType.INCOME,
                "Salary",
                LocalDate.of(2026, 10, 20)
        );
        Transaction electricity = new Transaction(
                5,
                "Electricty Bill",
                new BigDecimal("1500.00"),
                TransactionType.EXPENSE,
                "Bills",
                LocalDate.of(2026, 10, 15)
        );
        Transaction recharge = new Transaction(
                6,
                "Mobile Recharge",
                new BigDecimal("350.00"),
                TransactionType.EXPENSE,
                "Bills",
                LocalDate.of(2026, 10, 17)
        );
        Transaction tuition = new Transaction(
                7,
                "Part-Time Tutoring",
                new BigDecimal("2300.00"),
                TransactionType.INCOME,
                "Salary",
                LocalDate.of(2026, 10, 15)
        );
        Transaction course = new Transaction(
                8,
                "Online Course",
                new BigDecimal("1200.00"),
                TransactionType.EXPENSE,
                "Learning",
                LocalDate.of(2026,10,29)
        ) ;
        Transaction project = new Transaction(
                9,
                "Freelance Project",
                new BigDecimal("3000.00"),
                TransactionType.INCOME,
                "Salary",
                LocalDate.of(2026,9,15)
        );

        try{
            Transaction invalidExpense = new Transaction(
                    10,
                    " ",
                    new BigDecimal("500.00"),
                    TransactionType.EXPENSE,
                    "Food",
                    LocalDate.of(2026,10,27)
            );
            service.addTransaction(invalidExpense);
        }
        catch (IllegalArgumentException e){
            System.out.println("Transaction rejected: "+ e.getMessage());
        }

        try {
            Transaction validExpense = new Transaction(
                    11,
                    "Lunch",
                    new BigDecimal("150.00"),
                    TransactionType.EXPENSE,
                    "Food",
                    LocalDate.of(2026,10,20)
            );
            service.addTransaction(validExpense);
            System.out.println("Transaction Added Successfully...!!!");
        }
        catch (IllegalArgumentException o){
            System.out.println("Transaction Rejected :"+o.getMessage());
        }

        try {
            Transaction validExpense_2 = new Transaction(
                    12,
                    "Internet Bill",
                    new BigDecimal("699.00"),
                    TransactionType.EXPENSE,
                    "Bills",
                    LocalDate.of(2026,9,5)
            );
            service.addTransaction(validExpense_2);
            System.out.println("Transaction Added Successfully!!!");
        }
        catch(IllegalArgumentException o){
            System.out.println("Transaction Rejected: "+o.getMessage());
        }



        // Add transaction through the Service
        service.addTransaction(salary);
        service.addTransaction(groceries);
        service.addTransaction(busFee);
        service.addTransaction(freelance);
        service.addTransaction(electricity);
        service.addTransaction(recharge);
        service.addTransaction(tuition);
        service.addTransaction(course);
        service.addTransaction(project);

        // Display all transactions
        System.out.println("=======  ALL TRANSACTION =======");
        service.displayTransactions();

        // Display Financial Summery
        System.out.println("\n ====== FINANCIAL SUMMERY ======");

        System.out.println(
                "Total Income: ₹" + service.calculateTotalIncome()
        );
        System.out.println(
                "Total Expense: ₹" + service.calculateTotalExpense()
        );
        System.out.println(
                "Remaining Balance: ₹" + service.calculateBalance()
        );

        // Get Total Size
        System.out.println("Total Transaction: "+service.size());

        // Filter By Category
        System.out.println("\n ====== Filter By Category ======");
        service.displayTransactionByCategory("Food");
        service.displayTransactionByCategory("travel");

        // Filter By Type
        System.out.println("\n ====== Filter By Type ======");
        service.displayTransactionByType(TransactionType.INCOME);

        // SEARCH RESULT
        System.out.println("\n ====== SEARCH RESULT ======");
        service.searchTransaction("recharge");
        service.searchTransaction("bill");

        // Get Transaction By Type (EXPENSES)
        List<Transaction> expense = service.getTransactionByType(TransactionType.EXPENSE);
        System.out.println("\n ===== Returned Transaction By Type (Expenses) =====");
        for(Transaction transaction : expense){
            System.out.println(
                    transaction.getDescription()
                    + " | ₹"
                    + transaction.getAmount()
            );
        }

        // Get Transaction By Category
        List<Transaction> Spending = service.getTransactionByCategory("salary");
        System.out.println("\n ===== Returned Transaction By Category =====");
        for(Transaction transaction : Spending){
            System.out.println(
                    transaction.getDescription()
                    +" | ₹"
                    + transaction.getAmount()
                    + " | "
                    + transaction.getCategory()
                    + " | "
                    + transaction.getType()
            );
        }

        // Display Monthly Financial Report
        service.displayMonthlyReport(YearMonth.of(2026,10));
        service.displayMonthlyReport(YearMonth.of(2026,9));

        // Filter Monthly Financial Report
        System.out.println("\n ======= Get Transaction By Month =======");
        List<Transaction> monthlyReport = service.getTransactionByMonth(YearMonth.of(2026,9));
        for(Transaction transaction : monthlyReport){
            System.out.println(
                    transaction.getDescription()+
                            " | "
                    + transaction.getAmount()
                    + " | ₹"
                    + transaction.getType()
            );
        }
        if(monthlyReport.size() %2 == 0 ){
            System.out.println("           ------------           ");
        }

        // Transaction Found By ID
        Transaction found = service.findTransactionById(101);
        if(found != null){
            System.out.println("\n ======TRANSACTION FOUND=======");
            System.out.println("ID: "+found.getId());
            System.out.println("Description: "+found.getDescription());
            System.out.println("Amount: ₹"+found.getAmount());
            System.out.println("Type: "+found.getType());
            System.out.println("Category: "+found.getCategory());
            System.out.println("Date: "+found.getDate());
        }
        else {
            System.out.println("\n Transaction Not Found!!!!");
        }

        // Test Deletion Method
/*

        System.out.println("\n======= Deleation Method =======");
        boolean delete = service.deleteTransaction(3);
        if(delete){
            System.out.println("Transaction Deleted Successfully....");
        }
        else{
            System.out.println("Transaction Not Found!!!!");
        }

*/

        // Test Update Method

        boolean updated = service.updateTransaction(999,"Groceries",new BigDecimal("2500.00"),TransactionType.EXPENSE,"Food",LocalDate.of(2026,10,5));
        if(updated){
            System.out.println("\nTransaction updated successfully!!!!");
        }
        else{
            System.out.println("\nTransaction not found!!!!");
        }

        Transaction updatedTransaction = service.findTransactionById(2);
        if(updatedTransaction != null){
            System.out.println(updatedTransaction.getDescription()+" | ₹"+updatedTransaction.getAmount()+" | "+updatedTransaction.getType());
        }

        // Test hasTransaction Method
        boolean r1 = service.hasTransaction(888);
        System.out.println("Has Transaction: "+r1);

        // Test Count Transaction By Type Method
        int r2 = service.countTransactionByType(TransactionType.EXPENSE);
        System.out.println("Total Count of This Transaction Type: "+r2);









    }
}
