package org.thekiddos.operators;

import org.junit.jupiter.api.Test;
import org.thekiddos.datastructures.Edge;
import org.thekiddos.datastructures.Graph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChainDecompositionTest {

    @Test
    void operate() {
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

        var actual = new ChainDecomposition( g ).operate();
        assertEquals( List.of(
                        List.of( new Edge( 0, 2, 1 ), new Edge( 2, 1, 1 ), new Edge( 1, 0, 1 ) ),
                        List.of( new Edge( 0, 3, 1 ), new Edge( 3, 2, 1 ) ),
                        List.of( new Edge( 1, 4, 1 ), new Edge( 4, 2, 1 ) ),
                        List.of( new Edge( 4, 8, 1 ), new Edge( 8, 6, 1 ), new Edge( 6, 4, 1 ) ),
                        List.of( new Edge( 5, 9, 1 ), new Edge( 9, 7, 1 ), new Edge( 7, 5, 1 ) )
                    ),
                actual
        );
    }
}
