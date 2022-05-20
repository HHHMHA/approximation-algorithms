package org.thekiddos.transformers;

import org.junit.jupiter.api.Test;
import org.thekiddos.datastructures.Edge;
import org.thekiddos.datastructures.Graph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MSTPrimTest {

    @Test
    void transform() {
        // example from clrs
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

        var tree = new MSTPrim( graph, 0 ).transform();
        assertEquals(
                List.of(
                        new Edge( 0, 1, 4 ),
                        new Edge( 0, 2, 8 )
                ),
                tree.getOutEdges( 0 )
        );

        assertEquals(
                List.of(
                        new Edge( 1, 0, 4 )
                ),
                tree.getOutEdges( 1 )
        );

        assertEquals(
                List.of(
                        new Edge( 2, 0, 8 ),
                        new Edge( 2, 5, 1 )
                ),
                tree.getOutEdges( 2 )
        );

        assertEquals(
                List.of(
                        new Edge( 3, 8, 4 ),
                        new Edge( 3, 4, 2 ),
                        new Edge( 3, 6, 7 )
                ),
                tree.getOutEdges( 3 )
        );

        assertEquals(
                List.of(
                        new Edge( 4, 3, 2 )
                ),
                tree.getOutEdges( 4 )
        );

        assertEquals(
                List.of(
                        new Edge( 5, 2, 1 ),
                        new Edge( 5, 8, 2 )
                ),
                tree.getOutEdges( 5 )
        );

        assertEquals(
                List.of(
                        new Edge( 6, 3, 7 ),
                        new Edge( 6, 7, 9 )
                ),
                tree.getOutEdges( 6 )
        );

        assertEquals(
                List.of(
                        new Edge( 7, 6, 9 )
                ),
                tree.getOutEdges( 7 )
        );

        assertEquals(
                List.of(
                        new Edge( 8, 5, 2 ),
                        new Edge( 8, 3, 4 )
                ),
                tree.getOutEdges( 8 )
        );
    }
}
