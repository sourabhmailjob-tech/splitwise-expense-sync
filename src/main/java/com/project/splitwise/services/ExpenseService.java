package com.project.splitwise.services;

import com.project.splitwise.exceptions.GroupDoesNotExistException;
import com.project.splitwise.exceptions.UserDoesNotExistException;
import com.project.splitwise.models.Expense;
import com.project.splitwise.models.Group;
import com.project.splitwise.models.User;
import com.project.splitwise.models.UserExpense;
import com.project.splitwise.repositories.ExpenseRepository;
import com.project.splitwise.repositories.GroupRepository;
import com.project.splitwise.repositories.UserExpenseRepository;
import com.project.splitwise.repositories.UserRepository;
import com.project.splitwise.strategies.Transaction;
import com.project.splitwise.strategies.settleupStrategies.SettleUpStrategy;
import com.project.splitwise.strategies.settleupStrategies.TwoSetSettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private UserRepository userRepository;
    private UserExpenseRepository userExpenseRepository;
    private SettleUpStrategy settleUpStrategy;
    private GroupRepository groupRepository;
    private ExpenseRepository expenseRepository;
    @Autowired
    public ExpenseService(UserRepository userRepository,
                          UserExpenseRepository userExpenseRepository,
                          @Qualifier("twoSetSettleUpStrategy") SettleUpStrategy settleUpStrategy,
                          GroupRepository groupRepository,
                          ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.userExpenseRepository = userExpenseRepository;
        this.settleUpStrategy = settleUpStrategy;
        this.groupRepository = groupRepository;
        this.expenseRepository = expenseRepository;
    }

    public List<Transaction> settleUpUser(String userId) throws UserDoesNotExistException {
        if(!Util.isValidId(userId)){
            throw new UserDoesNotExistException();
        }

        Long id = Long.parseLong(userId);
        Optional<User> userOptional = userRepository.findById(id);

        List<UserExpense> userExpenses = userExpenseRepository.findAllByUser(userOptional.get());
        List<Expense> expensesInvolvingUser = new ArrayList<>();
        for(UserExpense userExpense: userExpenses){
            expensesInvolvingUser.add(userExpense.getExpense());
        }

        List<Transaction> transactions = settleUpStrategy.settle(expensesInvolvingUser);
        List<Transaction> filteredTransactions = new ArrayList<>();

        for(Transaction transaction : transactions){
            if(transaction.getFrom().equals(userOptional.get()) || transaction.getTo().equals(userOptional.get())){
                filteredTransactions.add(transaction);
            }
        }

        return filteredTransactions;
    }


    public List<Transaction> settleUpGroup(String groupId) throws GroupDoesNotExistException {
        if(!Util.isValidId(groupId)){
            throw new GroupDoesNotExistException();
        }

        Long existingGroupId = Long.parseLong(groupId);
        Optional<Group> groupOptional = groupRepository.findById(existingGroupId);
        if(groupOptional.isEmpty()){
            throw new GroupDoesNotExistException();
        }
        
        List<Expense> expenses = expenseRepository.findAllByGroups(groupOptional.get());
        List<Transaction> transactions = settleUpStrategy.settle(expenses);


        return transactions;
    }


}
