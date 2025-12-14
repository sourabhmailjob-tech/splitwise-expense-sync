package com.project.splitwise.dtos;

import com.project.splitwise.strategies.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettleUpUserResponseDto extends BaseResponseDto {
    private List<Transaction> transactions;

    @Override
    public void printResponse() {
        super.printResponse();
        for(Transaction transaction: transactions){
            if(getStatus() != null && getMessage().equals(ResponseStatusType.SUCCESS)){
                if(transaction.getFrom() != null){
                    System.out.println("Amount to be received by : "+transaction.getFrom()+" about "+transaction.getAmount());
                }else{
                    System.out.println("Amount to be paid to : "+transaction.getTo()+" about "+transaction.getAmount());
                }
            }

        }
    }
}
