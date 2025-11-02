package com.splitwise.machinecoding.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PercentageSplitStrategy implements SplitStrategy {

    private final long amountInPaisa;
    private final Map<String, Double> percentageSplits;

    public PercentageSplitStrategy(long amountInPaisa, Map<String, Double> percentageSplits) {
        double totalPercentage = 0;
        for(Double percentage : percentageSplits.values()) {
            totalPercentage += percentage;
        }
        if (totalPercentage != 100.0)
            throw new IllegalArgumentException("Total percentage should be 100%");
        this.amountInPaisa = amountInPaisa;
        this.percentageSplits = percentageSplits;
    }

    @Override
    public Map<String, Double> calculateSplits() {
        Map<String, Double> splits = new HashMap<>();
        for(String participant : percentageSplits.keySet()) {
            splits.put(participant, (percentageSplits.get(participant) * amountInPaisa) / 100.0);
        }
        return splits;
    }
}
