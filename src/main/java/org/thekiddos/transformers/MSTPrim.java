package org.thekiddos.transformers;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.thekiddos.datastructures.Edge;
import org.thekiddos.datastructures.Graph;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

@AllArgsConstructor
public class MSTPrim {
    private final Graph graph;
    @Setter
    private int source;

    public Graph transform() {
        // TODO: validate source and connected graph
        return runMSTPrim();
    }

    private Graph runMSTPrim() {
        Graph tree = buildEmptyGraph();
        Queue<Edge> queue = new PriorityQueue<>();
        Set<Integer> visited = new HashSet<>();

        visited.add( source );
        expandEdge( source, queue, visited );

        while ( !queue.isEmpty() ) {
            var edge = queue.poll();
            var edgeDestination = edge.getDestination();
            if ( visited.contains( edgeDestination ) )
                continue;
            visited.add( edgeDestination );
            tree.addBidirectionalEdge( edge.getSource(), edgeDestination, edge.getWeight() );
            expandEdge( edgeDestination, queue, visited );
        }

        return tree;
    }

    private void expandEdge( int edgeSource, Queue<Edge> queue, Set<Integer> visited ) {
        var edges = graph.getOutEdges( edgeSource );
        for ( var edge : edges ) {
            if ( !visited.contains( edge.getDestination() ) )
                queue.add( edge );
        }
    }

    private Graph buildEmptyGraph() {
        Graph result = new Graph();
        for ( int i = 0; i < graph.size(); ++i )
            result.addVertex();
        return result;
    }
}
