package com.splitwise.machinecoding.entities;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class GroupImpl implements Group {

    private final String id;
    private String name;
    private String description;
    private Set<String> members;
    private final List<Expense> expenses;

    public GroupImpl(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.members = ConcurrentHashMap.newKeySet();
        this.expenses = new CopyOnWriteArrayList<>();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public Set<String> members() {
        return new HashSet<>(members);
    }

    @Override
    public boolean addToGroup(String memberId) {
        boolean isAdded = members.add(memberId);
        if (!isAdded) {
            System.out.println("Member " + memberId + " is already part of the group");
            return false;
        }
        return true;
    }

    @Override
    public boolean removeFromGroup(String memberId) {
        boolean isRemoved = members.remove(memberId);
        if (!isRemoved) {
            System.out.println("Member " + memberId + " is not part of the group, cannot remove");
            return false;
        }
        return true;
    }

    @Override
    public boolean addExpense(Expense expense) {
        expenses.add(expense);
        return true;
    }

    @Override
    public List<Expense> expenses() {
        return new ArrayList<>(expenses);
    }
}
