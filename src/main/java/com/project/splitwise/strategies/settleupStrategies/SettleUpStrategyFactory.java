package com.project.splitwise.strategies.settleupStrategies;

import com.project.splitwise.models.Expense;

import java.util.List;

public class SettleUpStrategyFactory {
    public static SettleUpStrategy getSettleUpStrategy(StrategyCriteria criteria){
        return switch (criteria){
            case MAP ->new TwoSetSettleUpStrategy();
            case HEAP -> new HeapBasedSettleUpStrategy();
        };
    }
}
