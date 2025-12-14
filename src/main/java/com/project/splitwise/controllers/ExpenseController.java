package com.project.splitwise.controllers;

import com.project.splitwise.dtos.*;
import com.project.splitwise.services.ExpenseService;
import com.project.splitwise.strategies.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ExpenseController {
    private ExpenseService expenseService;
    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public SettleUpUserResponseDto settleUpUser(SettleUpUserRequestDto request){
        SettleUpUserResponseDto response = new SettleUpUserResponseDto();
        try{
            List<Transaction> transactions = expenseService.settleUpUser(request.getUserId());
            response.setTransactions(transactions);
            response.setStatus(ResponseStatusType.SUCCESS);
            response.setMessage("Transactions created successfully..");

        }catch (Exception e){
            response.setStatus(ResponseStatusType.FAILURE);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    public SettleUpGroupResponseDto settleUpGroup(SettleUpGroupRequestDto request){
        return null;
    }
}
