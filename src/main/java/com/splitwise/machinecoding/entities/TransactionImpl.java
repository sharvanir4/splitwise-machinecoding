package com.splitwise.machinecoding.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.Instant;

@AllArgsConstructor
public class TransactionImpl implements Transaction {

    private final String from;
    private final String to;
    private final double amount;
    private final Instant timestamp;

    @Override
    public String userFrom() {
        return from;
    }

    @Override
    public String userTo() {
        return to;
    }

    @Override
    public double amount() {
        return amount;
    }

    @Override
    public Instant timestamp() {
        return timestamp;
    }
}
