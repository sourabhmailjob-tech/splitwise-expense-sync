package com.project.splitwise.strategies.settleupStrategies;

import com.project.splitwise.models.Expense;
import com.project.splitwise.models.User;
import com.project.splitwise.models.UserExpense;
import com.project.splitwise.models.UserExpenseType;
import com.project.splitwise.repositories.UserExpenseRepository;
import com.project.splitwise.strategies.Transaction;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("twoSetSettleUpStrategy")
public class TwoSetSettleUpStrategy implements SettleUpStrategy{
    @Autowired
    private UserExpenseRepository userExpenseRepository;



    @Override
    public List<Transaction> settle(List<Expense> expenses) {
        // for all expenses that I have to settle, who paid how much and who had to pay how much
        List<UserExpense> allUserExpensesForTheseExpenses = userExpenseRepository.findAllByExpenseIn(expenses);

        //I went through all of the expenses and found out who has paid how much extra or less
        HashMap<User,Integer> moneyMap = new HashMap<>();
        for(UserExpense userExpense: allUserExpensesForTheseExpenses){
            User user = userExpense.getUser();
            int currentPaid = 0;
            if(moneyMap.containsKey(user)){
                currentPaid = moneyMap.get(user);
            }

            if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID)){
                moneyMap.put(user, currentPaid+userExpense.getAmount());
            }else{
                moneyMap.put(user, currentPaid-userExpense.getAmount());
            }
        }

        TreeSet<Pair<User, Integer>> extraPaid = new TreeSet<>();
        TreeSet<Pair<User, Integer>> lessPaid = new TreeSet<>();

        for(Map.Entry<User, Integer> userAmount : moneyMap.entrySet()){
            if(userAmount.getValue() < 0){
                lessPaid.add(new Pair<>(userAmount.getKey(), userAmount.getValue()));
            }else{
                extraPaid.add(new Pair<>(userAmount.getKey(), userAmount.getValue()));
            }
        }

        List<Transaction> transactions = new ArrayList<>();
        while(!lessPaid.isEmpty()){
            Pair<User, Integer> lessPaidPair = lessPaid.pollFirst(); // get and remove the first value
            Pair<User, Integer> extraPaidPair = extraPaid.pollFirst();

            Transaction t = new Transaction();
            t.setFrom(lessPaidPair.a);
            t.setTo(extraPaidPair.a);
            if(Math.abs(lessPaidPair.b) <extraPaidPair.b ){ //Dhms:-100 , Prateek:200
                t.setAmount(Math.abs(lessPaidPair.b));
                if(!(extraPaidPair.b-Math.abs(lessPaidPair.b) ==0)){
                    extraPaid.add(new Pair<>(extraPaidPair.a, extraPaidPair.b-Math.abs(lessPaidPair.b)));
                }

            }else{//Dhms:-200 , Prateek:100
                t.setAmount(Math.abs(extraPaidPair.b));
                if(!(lessPaidPair.b+extraPaidPair.b ==0) ){
                    lessPaid.add(new Pair<>(lessPaidPair.a,  lessPaidPair.b+extraPaidPair.b));
                }

            }

            transactions.add(t);
        }

        return transactions;
    }
}
