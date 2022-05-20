package org.thekiddos.operators;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.thekiddos.datastructures.Graph;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PreOrderDFS {
    private final Graph graph;
    @Setter
    private int source;

    public List<Integer> operate() {
        List<Integer> result = new ArrayList<>();
        runDFS( result, source );
        return result;
    }

    private void runDFS( List<Integer> result, int vertex ) {
        if ( result.contains( vertex ) )
            return;

        result.add( vertex );
        for ( var edge : graph.getOutEdges( vertex ) ) {
            if ( !result.contains( edge.getDestination() ) )
                runDFS( result, edge.getDestination() );
        }
    }
}
