package com.splitwise.machinecoding.strategy;

import com.splitwise.machinecoding.constants.SplitType;

import java.util.Map;
import java.util.Set;

public class SplitStrategyProvider {

    //Requirement 6: The system should support different split methods, such as equal split, percentage split, and exact amounts
    public static SplitStrategy getStrategy(SplitType splitType, long amountInPaisa,
                                            Set<String> participants, Map<String, Double> splits) {
        switch (splitType) {
            case EQUAL_SPLIT:
                return new EqualSplitStrategy(amountInPaisa, participants);
            case PERCENTAGE_SPLIT:
                return new PercentageSplitStrategy(amountInPaisa, splits);
            case EXACT_SPLIT:
                return new ExactSplitStrategy(amountInPaisa, participants, splits);
            default:
               throw new IllegalArgumentException("Unsupported SplitType: " + splitType);
        }
    }

}
