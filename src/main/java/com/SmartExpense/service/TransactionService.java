package com.SmartExpense.service;

import com.SmartExpense.model.Transaction;
import com.SmartExpense.model.TransactionType;
import com.SmartExpense.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.YearMonth;

public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository){
        this.repository = repository;
    }

    // Add a transaction
    public void addTransaction(Transaction transaction){
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if(repository.findById(transaction.getId()) != null){
            throw new IllegalArgumentException("Transaction ID Already Exists");
        }
        repository.save(transaction);
    }

    // Display All Transactions
    public void displayTransactions(){
        List<Transaction> transactions = repository.findAll();
        for(Transaction transaction : transactions){
            System.out.println(
                    transaction.getDescription()
                            + " | ₹"
                    + transaction.getAmount() +" | "
                    + transaction.getType()
            );
        }
    }

    // Calculate Total Income
    public BigDecimal calculateTotalIncome(){
        List<Transaction> transactions = repository.findAll();
        BigDecimal totalIncome = BigDecimal.ZERO;
        for(Transaction transaction : transactions){
            if(transaction.getType() == TransactionType.INCOME){
                totalIncome = totalIncome.add(transaction.getAmount());
            }
        }
        return totalIncome ;
    }

    // Calculate Total Expense
    public BigDecimal calculateTotalExpense(){
        List<Transaction> transactions = repository.findAll();
        BigDecimal totalExpense = BigDecimal.ZERO;
        for(Transaction transaction : transactions){
            if(transaction.getType() == TransactionType.EXPENSE){
                totalExpense = totalExpense.add(transaction.getAmount());
            }
        }
        return totalExpense ;
    }

    // Calculate remaining Balance
    public BigDecimal calculateBalance(){
        return calculateTotalIncome().subtract(calculateTotalExpense());
    }

    // Calculate the size of ArrayList
    public int size(){
        List<Transaction> transactions = repository.findAll();
        return transactions.size();
    }

    // Filter Transaction By Type
    public void displayTransactionByType (TransactionType type){
        List<Transaction> transactions = repository.findAll();
       for(Transaction transaction : transactions){
           if(transaction.getType()==type){
               System.out.println(
                       transaction.getDescription()+" | ₹"+transaction.getAmount()+" | "+transaction.getType()
               );
           }
       }
    }

    // Get Transaction By Type
    public List<Transaction> getTransactionByType(TransactionType type){
        List<Transaction> transactions = repository.findAll();
        List<Transaction> result = new ArrayList<>();
        for(Transaction transaction : transactions){
            if(transaction.getType() == type){
                result.add(transaction);
            }
        }
        return result;
    }

    // Filter Transaction By Category
    public void displayTransactionByCategory (String category){
        List<Transaction> transactions = repository.findAll();
        for(Transaction transaction : transactions){
            if(transaction.getCategory().equalsIgnoreCase(category)){
                System.out.println(
                        transaction.getDescription()
                        +" | ₹"
                        + transaction.getAmount()
                        + " | "
                        + transaction.getCategory()
                );
            }
        }
    }

    // Get Transaction By Category
    public List<Transaction> getTransactionByCategory(String category){
        List<Transaction> transactions = repository.findAll();
        List<Transaction>result = new ArrayList<>();
        for(Transaction transaction : transactions){
            if(transaction.getCategory().equalsIgnoreCase(category)){
                result.add(transaction);
            }
        }
        return result;
    }

    // Search by Description
    public void searchTransaction(String keyword){
        List<Transaction> transactions = repository.findAll();
        for(Transaction transaction : transactions){
            if(transaction.getDescription().toLowerCase().contains(keyword.toLowerCase())){
                System.out.println(transaction.getDescription()+ "| ₹"+transaction.getAmount()+" | "+transaction.getType());
            }
        }
    }

    // Calculate Monthly Income
    public BigDecimal calculateMonthlyIncome(YearMonth month){
        List<Transaction> transactions = repository.findAll();
        BigDecimal totalIncome = BigDecimal.ZERO;
        for(Transaction transaction : transactions){
            YearMonth transactionMonth = YearMonth.from(transaction.getDate());
            if(transactionMonth.equals(month) && transaction.getType() == TransactionType.INCOME){
                totalIncome = totalIncome.add(transaction.getAmount());
            }
        }
        return totalIncome ;
    }

    // Calculate Monthly Expenses
    public BigDecimal calculateMonthlyExpenses(YearMonth month){
        List<Transaction> transactions = repository.findAll();
        BigDecimal totalExpense = BigDecimal.ZERO;
        for(Transaction transaction : transactions){
            YearMonth transactionMonth = YearMonth.from(transaction.getDate());
            if(transactionMonth.equals(month) && transaction.getType() == TransactionType.EXPENSE){
              totalExpense =  totalExpense.add(transaction.getAmount());
            }
        }
        return totalExpense;
    }

    // Calculate Monthly Balance
    public BigDecimal calculateMonthlyBalance(YearMonth month){
        return calculateMonthlyIncome(month).subtract(calculateMonthlyExpenses(month));
    }

    // Calculate Monthly Report (With Existing Methods)
    public void displayMonthlyReport(YearMonth month){
        System.out.println("\n ======= MONTHLY FINANCIAL REPORT =======");
        System.out.println("Month: "+month);
        System.out.println("Total Income: ₹"+calculateMonthlyIncome(month));
        System.out.println("Total Expense: ₹"+calculateMonthlyExpenses(month));
        System.out.println("Monthly Balance: ₹"+calculateMonthlyBalance(month));
    }

    // Monthly Report Filter
    public List<Transaction> getTransactionByMonth(YearMonth month){
        List<Transaction> transactions = repository.findAll();
        List <Transaction> result = new ArrayList<>();
        for(Transaction transaction : transactions){
            YearMonth transactionMonth = YearMonth.from(transaction.getDate());
            if(transactionMonth.equals(month) && (transaction.getType()==TransactionType.EXPENSE || transaction.getType() == TransactionType.INCOME)){
                result.add(transaction);
            }
        }
        return result;
    }

    // Find Transaction by ID
    public Transaction findTransactionById(long id){
        Transaction result = repository.findById(id);
        return result;
    }

    // Delete a Transaction by ID
    public boolean deleteTransaction(long id){
        boolean answer = repository.deleteById(id);
        return answer;
    }

    // Updation Method
    public boolean updateTransaction(long id, String description, BigDecimal amount, TransactionType type, String category, LocalDate date){
        Transaction transaction = findTransactionById(id);
        if(transaction == null){
            return false;
        }
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setType(type);
        transaction.setDate(date);
        return true;
    }

    // Find Highest Expense
    public Transaction findHighestExpense(){
        List<Transaction> transactions = repository.findAll();
        Transaction highest = null;
        for(Transaction transaction : transactions){
            if(transaction.getType()==TransactionType.EXPENSE){
                if(highest == null || transaction.getAmount().compareTo(highest.getAmount())>0){
                    highest = transaction;
                }
            }
        }
        return highest;
    }

    // Finding Lowest Expense
    public Transaction findLowestExpense(){
        List<Transaction> transactions = repository.findAll();
        Transaction lowest = null;
        for(Transaction transaction : transactions){
            if(transaction.getType()== TransactionType.EXPENSE){
                if(lowest == null || transaction.getAmount().compareTo(lowest.getAmount())<0){
                    lowest = transaction;
                }
            }
        }
        return lowest;
    }

    public boolean hasTransaction(long id){
        List<Transaction> transactions = repository.findAll();
        for (Transaction transaction : transactions){
            if(transaction.getId() == id){
                return true;
            }
        }
        return false;
    }

    public int countTransactionByType(TransactionType type){
        List<Transaction> transactions = repository.findAll();
        int count = 0;
        for(Transaction transaction : transactions){
            if(transaction.getType()==type){
                count++;
            }
        }
        return count;
    }


}
