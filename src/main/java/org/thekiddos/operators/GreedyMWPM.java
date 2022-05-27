package org.thekiddos.operators;

import org.thekiddos.datastructures.Edge;
import org.thekiddos.datastructures.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GreedyMWPM extends GraphEdgeOperator {
    public GreedyMWPM( Graph graph, List<Integer> vertices ) {
        // TODO: reject vertices if it's not even
        super( graph, vertices );
    }

    @Override
    protected void runOperator( List<Edge> result ) {
        // Instead of greedy we will select two random vertices and just add them
        var vertices = new ArrayList<>( getVertices() );
        while ( !vertices.isEmpty() ) {
            int v1 = popRandomVertex( vertices );
            int v2 = popRandomVertex( vertices );
            var edge = getEdge( v1, v2 );
            var reversedEdge = new Edge( v2, v1, edge.getWeight() );
            result.add( edge );
            result.add( reversedEdge );
        }
    }

    private Edge getEdge( int v1, int v2 ) {
        return getGraph().getOutEdges( v1 ).stream().filter( edge -> edge.getDestination() == v2 ).findFirst().orElse( null );
    }

    private int popRandomVertex( List<Integer> list ) {
        return list.remove( ThreadLocalRandom.current().nextInt( 0, list.size() ) );
    }

}
