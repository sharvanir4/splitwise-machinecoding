package com.splitwise.machinecoding.entities;

import java.util.Map;

public interface BalanceSheet {
    String userId();                             // The user whose balance this sheet represents
    Map<String, Double> balanceAmount();          // Maps other userId to net amount (positive = owed to this user, negative = owed by this user)
    boolean updateBalance(String userId, double amountInPaisa);  // Updates the balance for a given user by adding the amount (positive or negative)
}
