package com.splitwise.machinecoding.entities;

import com.splitwise.machinecoding.constants.SplitType;
import com.splitwise.machinecoding.strategy.SplitStrategyProvider;

import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExpenseImpl implements Expense {

    private long amountInPaisa;
    private String description;
    private final String id;
    private final Instant timestamp;
    private final String paidBy;
    private final Set<String> participants;
    private Map<String, Double> splitPerUser;
    private final SplitType splitType;

    private ExpenseImpl(Builder builder) {
        this.id = builder.id;
        this.timestamp = builder.timestamp;
        this.amountInPaisa = builder.amountInPaisa;
        this.description = builder.description;
        this.paidBy = builder.paidBy;
        this.splitType = builder.splitType;
        this.participants = new HashSet<>(builder.participants);

        if (this.splitType == SplitType.EQUAL_SPLIT) {
            //For equal split, splits map can be null or empty
            this.splitPerUser = SplitStrategyProvider
                    .getStrategy(splitType, amountInPaisa, participants, Map.of())
                    .calculateSplits();
        } else {
            if (builder.splits == null || builder.splits.isEmpty()) {
                throw new IllegalArgumentException("Splits map cannot be null or empty for split type: " + this.splitType);
            }
            this.splitPerUser = SplitStrategyProvider
                    .getStrategy(splitType, amountInPaisa, participants, builder.splits)
                    .calculateSplits();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Expense id= ").append(this.id).append("\n");
        sb.append("Expense amount= ").append(this.amountInPaisa).append("\n");
        sb.append("Expense description= ").append(this.description).append("\n");
        sb.append("Expense paidBy= ").append(this.paidBy).append("\n");
        sb.append("Expense splitType= ").append(this.splitType).append("\n");
        sb.append("Expense participants= ").append(this.participants).append("\n");
        sb.append("Expense splitPerUser= ").append(this.splitPerUser).append("\n");
        return sb.toString();
    }

    @Override
    public long amountInPaisa() { return amountInPaisa; }
    @Override
    public String id() { return id; }
    @Override
    public String description() { return description; }
    @Override
    public Set<String> participants() { return new HashSet<>(participants); }
    @Override
    public String paidBy() { return paidBy; }
    @Override
    public SplitType splitType() { return splitType; }
    @Override
    public Map<String, Double> splitPerUser() { return splitPerUser; }
    @Override
    public Instant timestamp() { return timestamp; }

    public static class Builder {
        private String id;
        private Instant timestamp;
        private long amountInPaisa;
        private String description;
        private String paidBy;
        private SplitType splitType;
        private Set<String> participants = new HashSet<>();
        private Map<String, Double> splits;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTimestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setAmountInPaisa(long amountInPaisa) {
            this.amountInPaisa = amountInPaisa;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPaidBy(String paidBy) {
            this.paidBy = paidBy;
            return this;
        }

        public Builder setSplitType(SplitType splitType) {
            this.splitType = splitType;
            return this;
        }

        public Builder setParticipants(Set<String> participantsInput) {
            this.participants = new HashSet<>(participantsInput);
            return this;
        }

        public Builder setSplits(Map<String, Double> splits) {
            this.splits = splits;
            return this;
        }

        public ExpenseImpl build() {
            // validations
            if (id == null || id.isEmpty()) {
                throw new IllegalArgumentException("Expense id cannot be null or empty");
            }
            if (timestamp == null) {
                throw new IllegalArgumentException("Timestamp cannot be null");
            }
            if (amountInPaisa <= 0) {
                throw new IllegalArgumentException("Amount in paisa must be greater than zero");
            }
            if (paidBy == null || paidBy.isEmpty()) {
                throw new IllegalArgumentException("PaidBy cannot be null or empty");
            }
            if (splitType == null) {
                throw new IllegalArgumentException("SplitType cannot be null");
            }
            if (participants == null || participants.isEmpty()) {
                throw new IllegalArgumentException("Participants cannot be null or empty");
            }
            if (splitType != SplitType.EQUAL_SPLIT && (splits == null || splits.isEmpty())) {
                throw new IllegalArgumentException("Splits map cannot be null or empty for split type: " + splitType);
            }

            return new ExpenseImpl(this);
        }
    }
}
