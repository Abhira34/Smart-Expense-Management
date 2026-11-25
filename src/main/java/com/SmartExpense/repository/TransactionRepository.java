package com.SmartExpense.repository;

import java.util.List;
import com.SmartExpense.model.Transaction;


public interface TransactionRepository {

    void save(Transaction transactions);

    Transaction findById(long id);

    List<Transaction> findAll();

    boolean deleteById(long id);


}
