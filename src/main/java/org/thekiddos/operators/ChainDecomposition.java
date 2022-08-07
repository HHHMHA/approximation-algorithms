package org.thekiddos.operators;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.thekiddos.datastructures.Edge;
import org.thekiddos.datastructures.EdgeType;
import org.thekiddos.datastructures.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ChainDecomposition {
    @Getter(AccessLevel.PROTECTED)
    private final Graph graph;

    public List<List<Edge>> operate() {
        var root = 0;
        var vertices = new PreOrderDFS( graph, root ).operate();

        if ( vertices.size() != graph.size() ) {
            return List.of(); // Not connected
        }

        var backEdges = new EdgeClassifyDFS( graph, root ).operate().stream()
                .filter( e -> e.getType().equals( EdgeType.BACK ) )
                .map( e -> new Edge( e.getDestination(), e.getSource(), e.getWeight() ) )  // We need the right orientation like the paper
                .sorted( Comparator.comparingInt( e -> vertices.indexOf( e.getSource() ) ) )  // Sort by dfi like the paper
                .collect( Collectors.toList() );

        boolean[] visited = new boolean[ vertices.size() ];
        List<List<Edge>> chains = new ArrayList<>();
        for ( var e : backEdges ) {
            chains.add( findChain( e, visited ) );
        }
        return chains;
    }

    private List<Edge> findChain( Edge backEdge, boolean[] visited ) {
        List<Edge> chain = new ArrayList<>();
        visited[ backEdge.getSource() ] = true;
        DFS( backEdge, chain, visited );
        return chain;
    }

    private boolean DFS( Edge edge, List<Edge> result, boolean[] visited ) {
        if ( !result.contains( edge ) && !result.contains( new Edge( edge.getDestination(), edge.getSource(), edge.getWeight() ) ) )
            result.add( edge );

        visited[ edge.getDestination() ] = true;

        for ( var nextEdge : getGraph().getOutEdges( edge.getDestination() ) ) {

            if ( visited[ nextEdge.getDestination() ] && nextEdge.getDestination() != edge.getSource() ) {
                result.add( nextEdge );
                return true;
            }

            if ( visited[ nextEdge.getDestination() ] )
                continue;

            var chainDone = DFS( nextEdge, result, visited );
            if ( chainDone )
                return true;
        }

        return false;
    }
}
