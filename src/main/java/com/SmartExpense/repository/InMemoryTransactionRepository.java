package com.SmartExpense.repository;

import com.SmartExpense.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionRepository implements TransactionRepository{

    private final List<Transaction> transactions = new ArrayList<>();

    //-------------------------------------------------------------------------------------------------------------------

    @Override
    public List<Transaction> findAll(){
        return new ArrayList<>(transactions);
    }

    @Override
    public void save (Transaction transaction){
        if(transaction == null){
            throw new IllegalArgumentException("Transaction Cannot Be Null");
        }
        transactions.add(transaction);
    }

    @Override
    public Transaction findById(long id){
        for(Transaction transaction:transactions){
            if(transaction.getId() == id){
                return transaction;
            }
        }
        return null;
    }

    @Override
    public boolean deleteById(long id){
        Transaction transaction = findById(id);
        if(transaction != null){
            transactions.remove(transaction);
            return true;
        }
        return false;
    }



}
