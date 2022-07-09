package org.thekiddos.operators;

import lombok.Getter;
import org.thekiddos.datastructures.Graph;

import java.util.*;

public class TargetBFS extends GraphOrderedVerticesOperator {
    @Getter
    private final int destination;

    public TargetBFS( Graph graph, int source, int destination ) {
        super( graph, source );
        this.destination = destination;
    }

    @Override
    protected void runOperator( List<Integer> result, int source ) {
        Queue<Integer> queue = new ArrayDeque<>();

        final int NO_PARENT = -1;
        int[] parents = new int[ getGraph().size() ];
        Arrays.fill( parents, NO_PARENT );
        parents[ source ] = source;

        queue.add( source );

        while ( !queue.isEmpty() ) {
            var vertex = queue.poll();
            if ( vertex == destination ) {
                buildPath( parents, result );
                return;
            }

            for ( var edge : getGraph().getOutEdges( vertex ) ) {
                var to = edge.getDestination();
                if ( parents[ to ] != NO_PARENT ) {
                    continue;
                }

                parents[ to ] = vertex;
                queue.add( to );
            }
        }
    }

    private void buildPath( int[] parents, List<Integer> result ) {
        int currentVertex = destination;
        while ( parents[ currentVertex ] != currentVertex ) {
            result.add( 0, currentVertex );
            currentVertex = parents[ currentVertex ];
        }
        result.add( 0, currentVertex );
    }
}
