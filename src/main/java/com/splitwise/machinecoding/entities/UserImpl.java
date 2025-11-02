package com.splitwise.machinecoding.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserImpl implements User{

    private String id;
    private volatile String name;
    private Set<String> groups;
    private final BalanceSheet balanceSheet;
    private final List<Transaction> transactions;

    public UserImpl(String id, String name) {
        this.id = id;
        this.name = name;
        this.groups = ConcurrentHashMap.newKeySet();
        this.balanceSheet = new BalanceSheetImpl(id);
        this.transactions = new CopyOnWriteArrayList<>();
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
    public Set<String> groups() {
        return new HashSet<>(groups);
    }

    @Override
    public boolean onAddToGroup(String groupId) {
        boolean added = groups.add(groupId);
        if (!added) {
            System.out.println("User " + id +
                    " " + name + " already belongs to group id: " + groupId);
        }
        return added;
    }

    @Override
    public boolean onRemoveFromGroup(String groupId) {
        boolean removed = groups.remove(groupId);
        if (!removed) {
            throw new UnsupportedOperationException("User " + id +
                    " " + name + " is not in group id: " + groupId);
        }
        return removed;
    }

    @Override
    public synchronized void setName(String name) {
        this.name = name;
        System.out.println("User with id: " + id + " is now called " + name);
    }

    @Override
    public synchronized BalanceSheet balanceSheet() {
        return this.balanceSheet;
    }

    @Override
    public List<Transaction> transactions() {
        return new ArrayList<>(transactions);
    }

    @Override
    public boolean addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        return true;
    }
}
