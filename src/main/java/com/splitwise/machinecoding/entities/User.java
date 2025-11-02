package com.splitwise.machinecoding.entities;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface User {
    String id();
    String name();
    Set<String> groups();
    boolean onAddToGroup(String groupId);
    boolean onRemoveFromGroup(String groupId);
    void setName(String name);
    BalanceSheet balanceSheet();
    List<Transaction> transactions();
    boolean addTransaction(Transaction transaction);
}
