package com.SmartExpense.service;

// jUnit is just a java testing framework.

import com.SmartExpense.model.Transaction;
import com.SmartExpense.model.TransactionType;
import com.SmartExpense.repository.InMemoryTransactionRepository;
import com.SmartExpense.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionServiceTest {

    @Test
    void calculateBalanceShouldBeCorrect () {

        // Arrange
        TransactionRepository repository = new InMemoryTransactionRepository();

        TransactionService service = new TransactionService(repository);

        Transaction income = new Transaction(1,"Salary",new BigDecimal("30000"),TransactionType.INCOME,"Salary",LocalDate.of(2026,1,1));
        Transaction expense = new Transaction(2,"Food",new BigDecimal("5000"),TransactionType.EXPENSE,"Food",LocalDate.of(2026,1,3));

        service.addTransaction(income);
        service.addTransaction(expense);

        // Act
        BigDecimal actual = service.calculateBalance();

        // Assert
        BigDecimal expected = new BigDecimal("25000");
        assertEquals(expected,actual);

    }

    @Test
    void negativeAmountShouldBeRejected(){

        assertThrows(IllegalArgumentException.class,() -> new Transaction(3,"Bus Fee",new BigDecimal("-4800"), TransactionType.EXPENSE,"Travel",LocalDate.of(2026,1,5)));

    }

    @Test
    void balanceShouldBeZeroWhenThereAreNoTransaction() {
        TransactionRepository repository = new InMemoryTransactionRepository();

        TransactionService service = new TransactionService(repository);

        try {
            Transaction income = null;
            Transaction expense = null;
        }
        catch(IllegalArgumentException o) {
            Transaction income = null;
            Transaction expense = null;
            try {
                income = new Transaction(101, "Salary", BigDecimal.ZERO, TransactionType.INCOME, "Salary", LocalDate.of(2026, 5, 15));
                expense = new Transaction(201, "Food", BigDecimal.ZERO, TransactionType.EXPENSE, "Food", LocalDate.of(2026, 5, 16));
            } catch (IllegalArgumentException e) {
                service.addTransaction(income);
                service.addTransaction(expense);
            }
        }
        finally {
            System.out.println("\n Warning: Transaction/Amount Must be greater than Zero...");
            System.out.println("Total Income: "+service.calculateTotalIncome());
            System.out.println("Total Expense: "+service.calculateTotalExpense());
            System.out.println("Total Balance: "+service.calculateBalance());
        }
    }

    @Test
    void duplicateTransactionIDShouldBeRejected(){

        TransactionRepository repository = new InMemoryTransactionRepository();

        TransactionService service = new TransactionService(repository);

        // Method : 1


        Transaction income = new Transaction(1,"Salary",new BigDecimal("30000"),TransactionType.INCOME,"Salary",LocalDate.of(2026,1,1));
        Transaction expense = new Transaction(1,"Food",new BigDecimal("5000"),TransactionType.EXPENSE,"Food",LocalDate.of(2026,1,3));

        try{
            service.addTransaction(income);
            service.addTransaction(expense);
        } catch (IllegalArgumentException o){
            System.out.println("WARNING: Duplicate Transaction ID Are Not Allowed");
            return;
        }


        // Method - 2

/*

        Transaction income = new Transaction(1,"Salary",new BigDecimal("30000"),TransactionType.INCOME,"Salary",LocalDate.of(2026,1,1));
        Transaction expense = new Transaction(1,"Food",new BigDecimal("5000"),TransactionType.EXPENSE,"Food",LocalDate.of(2026,1,3));
        if(income.getId() == expense.getId()){
            System.out.println("Duplicate ID are not Allowed...");
            return ;
        }
        else{
            service.addTransaction(income);
            service.addTransaction(expense);
            System.out.println("Total Income: "+ service.calculateTotalIncome()+" | "+"Total Expense: "+ service.calculateTotalExpense());
            System.out.println("Available Balance: "+service.calculateBalance());
        }

*/

        // Method - 3

        /*

        Transaction income = new Transaction(1,"Salary",new BigDecimal("30000"),TransactionType.INCOME,"Salary",LocalDate.of(2026,1,1));
        Transaction expense = new Transaction(1,"Food",new BigDecimal("5000"),TransactionType.EXPENSE,"Food",LocalDate.of(2026,1,3));

        if(income.getId() != expense.getId()){
            service.addTransaction(income);
            service.addTransaction(expense);
        }else {
            assertThrows(IllegalArgumentException.class,() -> expense.getId(),"Duplicate ID are not Allowed...");
        }

      */

    }


}
