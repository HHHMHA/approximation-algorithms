package org.thekiddos;

import org.junit.jupiter.api.Test;
import org.thekiddos.datastructures.Graph;
import org.thekiddos.operators.ChainDecomposition;

import static org.junit.jupiter.api.Assertions.*;

class GraphConnectivityClassifierTest {

    @Test
    void classify() {
        Graph g = new Graph();
        g.addVertices( 10 );

        g.addBidirectionalEdge( 0, 1, 1 );
        g.addBidirectionalEdge( 0, 2, 1 );
        g.addBidirectionalEdge( 0, 3, 1 );

        g.addBidirectionalEdge( 1, 2, 1 );
        g.addBidirectionalEdge( 1, 4, 1 );

        g.addBidirectionalEdge( 2, 3, 1 );
        g.addBidirectionalEdge( 2, 4, 1 );

        g.addBidirectionalEdge( 4, 5, 1 );
        g.addBidirectionalEdge( 4, 6, 1 );
        g.addBidirectionalEdge( 4, 8, 1 );

        g.addBidirectionalEdge( 5, 7, 1 );
        g.addBidirectionalEdge( 5, 9, 1 );

        g.addBidirectionalEdge( 6, 8, 1 );

        g.addBidirectionalEdge( 7, 9, 1 );

        var actual = new GraphConnectivityClassifier( g ).classify();
        assertEquals( "not 2-edge-connected", actual );
    }
}
