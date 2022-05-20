package org.thekiddos.operators;

import org.thekiddos.datastructures.Graph;

import java.util.List;

public class PreOrderDFS extends GraphOrderedVerticesOperator {

    public PreOrderDFS( Graph graph, int source ) {
        super( graph, source );
    }

    @Override
    protected void runOperator( List<Integer> result, int vertex ) {
        if ( result.contains( vertex ) )
            return;

        result.add( vertex );
        for ( var edge : getGraph().getOutEdges( vertex ) ) {
            if ( !result.contains( edge.getDestination() ) )
                runOperator( result, edge.getDestination() );
        }
    }
}
