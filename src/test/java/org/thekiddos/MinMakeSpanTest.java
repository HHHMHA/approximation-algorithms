package org.thekiddos;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinMakeSpanTest {

    @Test
    void solve() {
        List<Integer> processTimes = List.of( 3, 2, 1, 4, 3 );
        int numberOfMachines = 3;

        assertEquals( List.of(
                List.of( 0 ),
                List.of( 1, 4 ),
                List.of( 2, 3 )
        ), new MinMakeSpan( processTimes, numberOfMachines ).solve() );
    }
}
