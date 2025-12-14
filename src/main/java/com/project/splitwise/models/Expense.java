package com.project.splitwise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Expense extends BaseModel{
    private String description;
    private int amount;
    @ManyToOne
    private User createdBy;
    @ManyToOne
    private Group group;
    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;
    @OneToMany
    private List<UserExpense> userExpenses;

}
