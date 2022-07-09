package org.thekiddos.operators;

import org.junit.jupiter.api.Test;
import org.thekiddos.datastructures.Graph;

import java.util.AbstractMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GreedyPathVertexDisjointTest {

    @Test
    void operate() {
        Graph g = new Graph();
        g.addVertices( 4 );

        g.addEdge( 0, 1, 2 );
        g.addEdge( 1, 2, 2 );
        g.addEdge( 2, 3, 2 );
        g.addEdge( 3, 0, 2 );

        var actual = new GreedyPathVertexDisjoint( g, List.of(
                new AbstractMap.SimpleEntry<>( 0, 2 ),
                new AbstractMap.SimpleEntry<>( 2, 3 )
        ) ).operate();
        assertEquals( List.of(
                List.of( 0, 1, 2 ),
                List.of( 2, 3 )
        ), actual );
    }
}
