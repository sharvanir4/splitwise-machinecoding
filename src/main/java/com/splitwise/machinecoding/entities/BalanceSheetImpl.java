package com.splitwise.machinecoding.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BalanceSheetImpl implements BalanceSheet {

    private final String userId;
    private final Map<String, Double> balanceAmount;

    public BalanceSheetImpl(String userId) {
        this.userId = userId;
        this.balanceAmount = new ConcurrentHashMap<>();
    }

    @Override
    public String userId() {
        return this.userId;
    }

    @Override
    public Map<String, Double> balanceAmount() {
        return new HashMap<>(this.balanceAmount);
    }

    @Override
    public synchronized boolean updateBalance(String userId, double amountInPaisa) {
        balanceAmount.put(userId, balanceAmount.getOrDefault(userId, 0.0) + amountInPaisa);
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BalanceSheet for user " + userId + "\n");
        for (Map.Entry<String, Double> entry : balanceAmount.entrySet()) {
            if (entry.getValue() >= 0.0) {
                sb.append(entry.getKey()).append(" owes").append(entry.getValue()).append("\n");
            } else {
                sb.append(entry.getKey()).append(" is owed").append(entry.getValue()).append("\n");
            }
        }
        return sb.toString();
    }
}
