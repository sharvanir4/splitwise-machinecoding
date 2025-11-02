package com.splitwise.machinecoding.entities;

import java.time.Instant;

public interface Transaction {
    /* transaction will be:
    1. if user has paid for some expense
    2. if user has settled up with other user
    */
    String userFrom();
    String userTo();
    double amount();
    Instant timestamp();
}
