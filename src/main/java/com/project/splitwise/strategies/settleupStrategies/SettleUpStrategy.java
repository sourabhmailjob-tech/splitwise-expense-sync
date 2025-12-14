package com.project.splitwise.strategies.settleupStrategies;

import com.project.splitwise.models.Expense;
import com.project.splitwise.strategies.Transaction;

import java.util.List;

public interface SettleUpStrategy {
    List<Transaction> settle (List<Expense> expenses);
}
