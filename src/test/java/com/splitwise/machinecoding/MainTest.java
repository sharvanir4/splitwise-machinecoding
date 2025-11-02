package com.splitwise.machinecoding;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MainTest {
    @Test
    public void testMain() {
        assertEquals(1, Main.testFunction());
    }
}
