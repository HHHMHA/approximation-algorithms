package org.thekiddos.operators;

import org.junit.jupiter.api.Test;
import org.thekiddos.datastructures.Graph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PreOrderDFSTest {

    @Test
    void operate() {
        Graph graph = new Graph();
        graph.addVertices( 9 );

        graph.addBidirectionalEdge( 0, 1, 4 );
        graph.addBidirectionalEdge( 0, 2, 8 );

        graph.addBidirectionalEdge( 2, 5, 1 );

        graph.addBidirectionalEdge( 3, 4, 2 );
        graph.addBidirectionalEdge( 3, 6, 7 );
        graph.addBidirectionalEdge( 3, 8, 4 );

        graph.addBidirectionalEdge( 5, 8, 2 );

        graph.addBidirectionalEdge( 6, 7, 9 );

        var vertices = new PreOrderDFS( graph, 0 ).operate();
        assertEquals( List.of(
                    0, 1, 2, 5, 8, 3, 4, 6, 7
                ),
                vertices
        );
    }
}
