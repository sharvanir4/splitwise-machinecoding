package com.splitwise.machinecoding.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EqualSplitStrategy implements SplitStrategy {

    private final Set<String> participants;
    private final long totalAmount;

     public EqualSplitStrategy(long totalAmountInPaisa,
                               Set<String> participants) {
         this.participants = participants;
         this.totalAmount = totalAmountInPaisa;

     }

    @Override
    public Map<String, Double> calculateSplits() {
         Map<String, Double> splits = new HashMap<>();
         double amountPerParticipant = (double) totalAmount / splits.size();
         for (String participant : participants) {
             splits.put(participant, amountPerParticipant);
         }
         return splits;
    }
}
