package org.thekiddos.operators;

import org.junit.jupiter.api.Test;
import org.thekiddos.datastructures.Graph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EulerCycleTest {
    @Test
    void operate() {
        Graph graph = new Graph();
        graph.addVertices( 3 );
        graph.addBidirectionalEdge( 0, 1, 1 );
        graph.addBidirectionalEdge( 0, 1, 2 );
        graph.addBidirectionalEdge( 1, 2, 2 );
        graph.addBidirectionalEdge( 1, 2, 1 );

        var result = new EulerCycle( graph, 0 ).operate();
        assertEquals( List.of( 0, 1, 2, 1, 0 ), result );
    }

}
