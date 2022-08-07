package org.thekiddos.operators;

import lombok.Getter;
import lombok.NonNull;
import org.thekiddos.datastructures.Edge;
import org.thekiddos.datastructures.EdgeType;
import org.thekiddos.datastructures.Graph;

import java.util.List;
import java.util.Stack;

public class EdgeClassifyDFS extends GraphEdgeOperator {
    @Getter
    private int source;

    public EdgeClassifyDFS( Graph graph, int source ) {
        super( graph );
        this.source = source;
    }

    @Override
    protected void runOperator( List<Edge> result ) {
        boolean[] visited = new boolean[ getGraph().size() ];

        DFS( source, result, visited );
    }

    void DFS( int v, List<Edge> result, boolean[] visited ) {
        visited[v] = true;

        for ( var edge : getGraph().getOutEdges( v ) ) {
            var end = edge.getDestination();
            Edge classifiedEdge = new Edge( v, end, edge.getWeight() );
            if ( visited[ end ] ) {
                if ( unclassifiedEdge( result, edge ) ) {
                    classifiedEdge.setType( EdgeType.BACK );
                    result.add( classifiedEdge );
                }
                continue;
            }
            if ( unclassifiedEdge( result, edge ) ) {
                classifiedEdge.setType( EdgeType.TREE );
                result.add( classifiedEdge );
            }
            DFS( end, result, visited );
        }
    }

    private boolean unclassifiedEdge( List<Edge> result, Edge edge ) {
        return !result.contains( edge ) && !result.contains( new Edge( edge.getDestination(), edge.getSource(), edge.getWeight() ) );
    }
}
