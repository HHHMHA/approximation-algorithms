package org.thekiddos.operators;

import org.thekiddos.datastructures.Edge;
import org.thekiddos.datastructures.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ApproxVertexCover extends GraphOrderedVerticesOperator {
    public ApproxVertexCover( Graph graph, int source ) {
        super( graph, source );
    }

    // TODO: we are violating some principles here since we force source argument and we don't need it
    @Override
    protected void runOperator( List<Integer> result, int source ) {
        List<Edge> edges = getGraphEdges( getGraph() );
        while ( !edges.isEmpty() ) {
            var edge = getRandomEdge( edges );
            result.add( edge.getSource() );
            result.add( edge.getDestination() );
            var incidentEdges = getIncidentEdges( edges, edge );
            edges.removeAll( incidentEdges );
        }
    }

    private List<Edge> getIncidentEdges( List<Edge> edges, Edge source ) {
        return edges.stream().filter(
                edge -> edge.getSource() == source.getSource() ||
                        edge.getSource() == source.getDestination() ||
                        edge.getDestination() == source.getSource() ||
                        edge.getDestination() == source.getDestination()
        ).collect( Collectors.toList() );
    }

    private Edge getRandomEdge( List<Edge> edges ) {
        return edges.get( ThreadLocalRandom.current().nextInt( 0, edges.size() ) );
    }

    // TODO: move to graph class
    private List<Edge> getGraphEdges( Graph graph ) {
        List<Edge> edges = new ArrayList<>();
        for ( int i = 0; i < graph.size(); ++i ) {
            // avoid adding bidi edge
            graph.getOutEdges( i )
                    .stream()
                    .filter(
                            edge -> !edges.contains( new Edge( edge.getDestination(), edge.getSource(), edge.getWeight() ) )
                    ).forEach( edges::add );
        }
        return edges;
    }
}
