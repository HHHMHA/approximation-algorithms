package org.thekiddos;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FirstFitBinPackingTest {

    @Test
    void solve() {
        int binWeight = 5;
        List<Integer> items = List.of( 3, 2, 5, 5, 1, 1, 2, 2 );

        assertEquals( 5, new FirstFitBinPacking( binWeight, items ).solve() );
    }
}
