package com.splitwise.machinecoding.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExactSplitStrategy implements SplitStrategy {

    private final long amountInPaisa;
    private final Set<String> participants;
    private final Map<String, Double> exactSplits;

    public ExactSplitStrategy(long amountInPaisa, Set<String> participants, Map<String, Double> exactSplits) {
        double total = exactSplits.values().stream().mapToDouble(Double::doubleValue).sum();
        if (Math.abs(total - amountInPaisa) > 0.01) // for floating point comparison
            throw new IllegalArgumentException("Exact splits given does not add up to the total amount");
        this.amountInPaisa = amountInPaisa;
        this.participants = participants;
        this.exactSplits = exactSplits;
    }

    @Override
    public Map<String, Double> calculateSplits() {
        return new HashMap<>(exactSplits);
    }
}
