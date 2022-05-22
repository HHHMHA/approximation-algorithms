package org.thekiddos.operators;

import org.junit.jupiter.api.Test;
import org.thekiddos.datastructures.Graph;

import static org.junit.jupiter.api.Assertions.*;

class ApproxVertexCoverTest {
    @Test
    void operate() {
        // Not a real test just to make sure it runs fine
        Graph graph = new Graph();
        graph.addVertices( 9 );

        graph.addBidirectionalEdge( 0, 1, 4 );
        graph.addBidirectionalEdge( 0, 2, 8 );

        graph.addBidirectionalEdge( 1, 2, 11 );
        graph.addBidirectionalEdge( 1, 3, 8 );

        graph.addBidirectionalEdge( 2, 4, 7 );
        graph.addBidirectionalEdge( 2, 5, 1 );

        graph.addBidirectionalEdge( 3, 4, 2 );
        graph.addBidirectionalEdge( 3, 6, 7 );
        graph.addBidirectionalEdge( 3, 8, 4 );

        graph.addBidirectionalEdge( 4, 5, 6 );

        graph.addBidirectionalEdge( 5, 8, 2 );

        graph.addBidirectionalEdge( 6, 7, 9 );
        graph.addBidirectionalEdge( 6, 8, 14 );

        graph.addBidirectionalEdge( 7, 8, 10 );

        var vertices = new ApproxVertexCover( graph, 0 ).operate();
        System.out.println( vertices );
    }
}
