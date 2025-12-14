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

@Component("heapBasedSettleUpStrategy")
public class HeapBasedSettleUpStrategy implements SettleUpStrategy{
    @Override
    public List<Transaction> settle(List<Expense> expenses) {
        HashMap<User, Integer> outStandingAmountMap = new HashMap<>();
        List<Transaction> transactions = new ArrayList<>();
        //Loop through all expenses and userExpenses for each expense and calculate the final outstanding amount for user
        for(Expense expense : expenses){
            for(UserExpense userExpense: expense.getUserExpenses()){
                User user = userExpense.getUser();
                int currentOutStandingAmount = outStandingAmountMap.getOrDefault(user,0);
                outStandingAmountMap.put(user, getUpdatedOutStandingAmount(currentOutStandingAmount, userExpense));
            }
        }


        //MaxHeap -> Contains all the users with positive balance

        PriorityQueue<Map.Entry<User, Integer>> maxHeap = new PriorityQueue<>(
                (a,b)-> Integer.compare(b.getValue(), a.getValue())
        );
        //MeanHeap -> Contains all the users with negative balance
        PriorityQueue<Map.Entry<User, Integer>> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue)
        );


        //Populate the heaps using the values from map
        for(Map.Entry<User, Integer> entry : outStandingAmountMap.entrySet()){
            if(entry.getValue() <0){
                minHeap.add(entry);
            }else if(entry.getValue() >0){
                maxHeap.add(entry);
            }
        }

        //Calculate the transactions until heaps become empty
        List<Transaction> transactionList = new ArrayList<>();
        while(!minHeap.isEmpty()){
            //removing min from minHeap and max from maxHeap
            Map.Entry<User, Integer> maxHasToPay = minHeap.poll();
            Map.Entry<User, Integer> maxWillgetPaid = maxHeap.poll();

            Transaction t = new Transaction();
            t.setFrom(maxHasToPay.getKey()) ;
            t.setTo(maxWillgetPaid.getKey());
            t.setAmount(Math.min(Math.abs(maxHasToPay.getValue()), maxWillgetPaid.getValue()));

            transactionList.add(t);

            int newBalance = maxHasToPay.getValue() + maxWillgetPaid.getValue();
            if(newBalance <0){
                //means person who had to pay was grater in value, so goes back to min heap with new updated balance
                maxHasToPay.setValue(newBalance);
                minHeap.add(maxHasToPay);
            }else if(newBalance>0){
                //means person who will get paid was grater in value, so goes back to max heap with new updated balance
                maxWillgetPaid.setValue(newBalance);
                maxHeap.add(maxWillgetPaid);
            }
        }

        return transactionList;
    }

    private int getUpdatedOutStandingAmount(int currentOutStandingAmount, UserExpense userExpense){
        if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID)){
            currentOutStandingAmount = currentOutStandingAmount+userExpense.getAmount();
        }else{
            currentOutStandingAmount = currentOutStandingAmount-userExpense.getAmount();
        }
        return currentOutStandingAmount;
    }


}

/*
    Expense
        paid -> A=> 500 , B=> 500
        hadToPay -> A=> 250, B=> 250, C=> 250 D=> 250

 */



/*
    1. Find for each person their total paid and total hadToPay amounts.
    2. For each person find total outstanding amounts.
    3. Create 2 Heaps:
        A. Mean Heap -> All negative balances [Dabit / hadToPay amounts]
        B. Max Heap -> All positive balances [Credit / Will get paid amounts]
    4. Get the minimum from min heap and maximum from max heap and do a transaction to settle the amount.
    5. After the transaction whoever has the outstanding balance 0, don't add them to heap
        and other person's updated balance back in their respective heap. If both becomes 0, don't add either of them.
    6.Keep doing untill both the heaps are empty.
    7. Keep the track of all  transactions being done.
 */