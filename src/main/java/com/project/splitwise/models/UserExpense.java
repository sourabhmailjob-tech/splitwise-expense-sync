package com.project.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserExpense extends BaseModel{
    private int amount;
    @Enumerated(EnumType.ORDINAL)
    private UserExpenseType userExpenseType;
    @ManyToOne
    private User user;
    @ManyToOne
    private Expense expense;
}
