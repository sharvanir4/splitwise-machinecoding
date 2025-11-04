package com.splitwise.machinecoding;

import com.splitwise.machinecoding.constants.SplitType;
import com.splitwise.machinecoding.entities.ExpenseImpl;
import com.splitwise.machinecoding.services.SplitwiseService;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class Demo {

    public static void main(String[] args) {
        SplitwiseService splitwiseService = SplitwiseService.getInstance();
        //create user accounts
        splitwiseService.createUser("user1", "User One");
        splitwiseService.createUser("user2", "User Two");
        splitwiseService.createUser("user3", "User Three");
        splitwiseService.createUser("user4", "User Four");
        splitwiseService.createUser("user5", "User Five");

        //create group
        splitwiseService.createGroup("group1", "Group One", "for demo purposes - 1");
        splitwiseService.addUserToGroup("user1", "group1");
        splitwiseService.addUserToGroup("user2", "group1");
        splitwiseService.addUserToGroup("user3", "group1");
        splitwiseService.addUserToGroup("user4", "group1");
        splitwiseService.addUserToGroup("user5", "group1");

        //add expense to group
        splitwiseService.addExpenseToGroup("group1", new ExpenseImpl.Builder().setAmountInPaisa(500000) //500 rupees
                .setId(UUID.randomUUID().toString())
                .setDescription("Expense One")
                .setPaidBy("user1")
                .setParticipants(Set.of("user1", "user2", "user3", "user4", "user5"))
                .setSplitType(SplitType.EQUAL_SPLIT)
                .setTimestamp(Instant.now()));

        //get all expenses in group
        splitwiseService.viewGroupExpenses("user1", "group1");

        //view all user balance sheet with user 1
        System.out.println(splitwiseService.getBalance("user1", "user2"));
        System.out.println(splitwiseService.getBalance("user1", "user3"));
        System.out.println(splitwiseService.getBalance("user1", "user4"));
        System.out.println(splitwiseService.getBalance("user1", "user5"));

        //user2 settle up with user 1 fully
        splitwiseService.settleUserBalance("user2", "user1", 100000);
        System.out.println(splitwiseService.getBalance("user2", "user1"));
        //get their transactions
        splitwiseService.viewTransactionHistory("user1");
        splitwiseService.viewTransactionHistory("user2");

        //user3 partially settle up with user1
        splitwiseService.settleUserBalance("user3", "user1", 50000);
        System.out.println(splitwiseService.getBalance("user3", "user1"));
        splitwiseService.viewTransactionHistory("user3");
        splitwiseService.viewTransactionHistory("user1");

    }
}
