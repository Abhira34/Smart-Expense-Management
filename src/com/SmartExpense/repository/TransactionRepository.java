package com.SmartExpense.repository;

import java.util.ArrayList;
import java.util.List;
import com.SmartExpense.model.Transaction;
import com.SmartExpense.model.TransactionType;


public class TransactionRepository {

    private final List <Transaction> transactions = new ArrayList<>();

    public List<Transaction> findAll(){
        return transactions;
    }
   //-------------------------------------------------------------------------------------------------------------------



    public void save (Transaction transaction){
        if(transaction == null){
            throw new IllegalArgumentException("Transaction Cannot Be Null");
        }
        transactions.add(transaction);
    }

    public Transaction findById(long id){
      for(Transaction transaction:transactions){
          if(transaction.getId() == id){
              return transaction;
          }
      }
      return null;
    }

    public boolean deleteById(long id){
        Transaction transaction = findById(id);
              if(transaction!=null){
                transactions.remove(transaction);
                return true;
              }
        return false;
    }


}
