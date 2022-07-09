package org.thekiddos.operators;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.thekiddos.datastructures.Edge;
import org.thekiddos.datastructures.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class GreedyPathVertexDisjoint {
    @Getter(AccessLevel.PROTECTED)
    private final Graph graph;
    @Getter(AccessLevel.PROTECTED)
    private final List<Entry<Integer, Integer>> commodities;

    public GreedyPathVertexDisjoint( Graph graph, List<Entry<Integer, Integer>> commodities ) {
        this.graph = graph;
        this.commodities = new ArrayList<>( commodities );
    }

    public List<List<Integer>> operate() {
        List<List<Integer>> result = new ArrayList<>();
        Graph reducedGraph = reduceGraph( graph, List.of() );

        while ( !commodities.isEmpty() ) {
            var commodity = commodities.remove( 0 );
            var path = new TargetBFS( reducedGraph, commodity.getKey(), commodity.getValue() ).operate();
            if ( path.isEmpty() ) {
                return result;
            }
            result.add( path );
            reducedGraph = reduceGraph( reducedGraph, path );
        }

        return result;
    }

    private Graph reduceGraph( Graph graph, List<Integer> path ) {
        Graph result = new Graph();
        result.addVertices( getGraph().size() );
        var removedEdges = buildEdges( path );

        for ( int i = 0; i < result.size(); ++i ) {
            for ( var edge : graph.getOutEdges( i ) ) {
                if ( !removedEdges.contains( edge ) || path.isEmpty() ) {
                    result.addEdge( edge.getSource(), edge.getDestination(), 1 );
                }
            }
        }

        return result;
    }

    private List<Edge> buildEdges( List<Integer> path ) {
        // TODO: make BFS return List of edges instead so we reserve weights and remove this
        List<Edge> result = new ArrayList<>();
        for ( int i = 1; i < path.size(); ++i ) {
            result.add( new Edge( path.get( i - 1 ), path.get( i ), 1 ) );
        }
        return result;
    }

}
