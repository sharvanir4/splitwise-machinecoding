package com.splitwise.machinecoding.strategy;

import java.util.Map;

public interface SplitStrategy {
    Map<String, Double> calculateSplits();
}
