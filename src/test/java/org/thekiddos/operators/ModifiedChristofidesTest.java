package org.thekiddos.operators;

import org.junit.jupiter.api.Test;
import org.thekiddos.datastructures.Graph;

import static org.junit.jupiter.api.Assertions.*;

class ModifiedChristofidesTest {
    @Test
    void operate() {
        Graph graph = new Graph();
        graph.addVertices( 4 );

        graph.addBidirectionalEdge( 0, 1, 4 );
        graph.addBidirectionalEdge( 0, 2, 8 );
        graph.addBidirectionalEdge( 0, 3, 8 );

        graph.addBidirectionalEdge( 1, 2, 11 );
        graph.addBidirectionalEdge( 1, 3, 8 );

        graph.addBidirectionalEdge( 2, 3, 7 );

        var vertices = new ModifiedChristofides( graph, 0 ).operate();
        System.out.println(vertices);
    }

}
