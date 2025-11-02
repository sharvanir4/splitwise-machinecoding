package com.splitwise.machinecoding.entities;

import java.util.List;
import java.util.Set;

public interface Group {
    String id();
    String name();
    String description();
    Set<String> members();
    boolean addToGroup(String memberId);
    boolean removeFromGroup(String memberId);
    boolean addExpense(Expense expense);
    List<Expense> expenses();
}
