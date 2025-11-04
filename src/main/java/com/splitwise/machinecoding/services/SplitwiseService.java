package com.splitwise.machinecoding.services;

import com.splitwise.machinecoding.entities.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SplitwiseService {

    private static SplitwiseService instance;
    private final Map<String, User> userMap;
    private final Map<String, Group> groupMap;

    private SplitwiseService() {
        userMap = new ConcurrentHashMap<>();
        groupMap = new ConcurrentHashMap<>();
    }

    public static SplitwiseService getInstance() {
        if (instance == null) {
            synchronized (SplitwiseService.class) {
                if (instance == null) {
                    instance = new SplitwiseService();
                }
            }
        }
        return instance;
    }

    //requirement 1: The system should allow users to create accounts
    public synchronized void createUser(String id, String name) {
        if (userMap.containsKey(id)) {
            throw new UnsupportedOperationException("User with id " + id + " already exists");
        }
        userMap.put(id, new UserImpl(id, name));
        System.out.println("Created user with name " + name + " id " + id);
    }

    //requirement 1: users can manage profile information
    //here we only change the name, given the id, anything else is out of scope
    public void changeUserName(String id, String name) {
        if(!userMap.containsKey(id)) {
            System.out.println("User with id " + id + " does not exist");
        }
        userMap.get(id).setName(name);
    }

    //requirement 2: Users should be able to create groups
    public synchronized void createGroup(String id, String name, String description) {
        if (groupMap.containsKey(id)) {
            throw new UnsupportedOperationException("Group with id " + id + " already exists");
        }
        groupMap.put(id, new GroupImpl(id, name, description)); //TODO: createdBy
        System.out.println("Created group with name " + name + " id " + id + " and description " + description);
    }

    //out of scope: users can modify group data like name and description

    //requirement 2: users should be able to add other users to the groups
    public synchronized void addUserToGroup(String userId, String groupId) {
        if(!groupMap.containsKey(groupId)) {
            throw new UnsupportedOperationException("Group with id " + groupId + " does not exist");
        }
        Group group = groupMap.get(groupId);
        boolean added = group.addToGroup(userId);
        if (added) {
            User user = userMap.get(userId);
            System.out.println("Added user " + userId + " " + user.name() + " to group " + groupId);
            user.onAddToGroup(groupId);
        }
    }

    //requirement 2 extension: can remove user from group
    public synchronized void removeUserFromGroup(String userId, String groupId) {
        if(!groupMap.containsKey(groupId)) {
            throw new UnsupportedOperationException("Group with id " + groupId + " does not exist");
        }
        Group group = groupMap.get(groupId);
        boolean removed = group.removeFromGroup(userId);
        if (removed) {
            User user = userMap.get(userId);
            System.out.println("Removed user " + userId + " " + user.name() + " from group " + groupId);
            user.onRemoveFromGroup(groupId);
        }
    }

    //out of scope: having admin users in group. in this iteration everyone is admin

    //requirement 3: Users should be able to add expenses within a group, specifying the amount, description, and participants
    public synchronized void addExpenseToGroup(String groupId, ExpenseImpl.Builder expenseBuilder) {
        Instant time = Instant.now();
        if(!groupMap.containsKey(groupId)) {
            throw new UnsupportedOperationException("Group with id " + groupId + " does not exist");
        }
        Expense expense = expenseBuilder.build();
        Group group = groupMap.get(groupId);
        group.addExpense(expense);
        //update user balances
        for(String participant: expense.participants()) {
            if (!participant.equals(expense.paidBy())) {
                userMap.get(participant).balanceSheet().updateBalance(
                        expense.paidBy(), expense.splitPerUser().get(participant));
                userMap.get(expense.paidBy()).balanceSheet().updateBalance(
                        participant, -1.0*expense.splitPerUser().get(participant));
            }
        }
    }

    //requirement 5: Users should be able to view their individual balances with other users
    public double getBalance(String userId1, String userId2) { //get balance of user 1 with user 2
        User user1 = userMap.get(userId1);
        if (!user1.balanceSheet().balanceAmount().containsKey(userId2)) {
            System.out.println("User " + userId1 + " does not have balance with " + userId2);
        }
        return user1.balanceSheet().balanceAmount().get(userId2);
    }

    //requirement 5: users should be able to settle up balances with others
    //here user id 1 pays user id 2 the amount, so we settle accordingly
    public synchronized void settleUserBalance(String userId1, String userId2, double amount) {
        User user1 = userMap.get(userId1);
        if (!user1.balanceSheet().balanceAmount().containsKey(userId2)) {
            throw new IllegalArgumentException("User " + userId1 + " does not have balance with " + userId2);
        }
        User user2 = userMap.get(userId2);
        user2.balanceSheet().updateBalance(userId1, amount);
        user1.balanceSheet().updateBalance(userId2, -1.0*amount);
        Instant time = Instant.now();
        user2.addTransaction(new TransactionImpl(userId1, userId2, amount, time));
        user1.addTransaction(new TransactionImpl(userId1, userId2, amount, time));
    }

    //Requirement 7: Users should be able to view their transaction history
    public void viewTransactionHistory(String userId1) {
        User user1 = userMap.get(userId1);
        System.out.println("View transaction history for user " + userId1 + "\n");
        for(Transaction transaction: user1.transactions()) {
            System.out.println("Paisa " + transaction.amount() + " paid " + ((transaction.userFrom() == userId1) ?
                    (" to " + transaction.userTo()) : ("by " + transaction.userFrom())));
        }
    }

    //Requirement 7: users can view group expenses
    public void viewGroupExpenses(String userId, String groupId) {
        if(!groupMap.containsKey(groupId)) {
            throw new UnsupportedOperationException("Group with id " + groupId + " does not exist");
        }
        Group group = groupMap.get(groupId);
        if(!group.members().contains(userId)) {
            throw new UnsupportedOperationException("User " + userId + " is not a member of " + groupId);
        }
        for(Expense expense: group.expenses()) {
            System.out.println(expense.toString());
        }
    }

}
