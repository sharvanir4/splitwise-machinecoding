package com.splitwise.machinecoding.entities;

import com.splitwise.machinecoding.constants.SplitType;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

public interface Expense {
    long amountInPaisa();
    String id();
    String description();
    Set<String> participants();
    String paidBy();
    SplitType splitType();
    Map<String, Double> splitPerUser();
    Instant timestamp();
    //out of scope: adding/removing participants and recalculating splits due to this
}