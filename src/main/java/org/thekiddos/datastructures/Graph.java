package org.thekiddos.datastructures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Graph implements Cloneable {
    private final List<List<Edge>> adjacencyList = new ArrayList<>();

    public void addVertex() {
        adjacencyList.add( new ArrayList<>() );
    }

    public void addVertices( int number ) {
        for ( int i = 0; i < number; ++i )
            adjacencyList.add( new ArrayList<>() );
    }

    public void addEdge( int source, int destination, int weight ) throws IllegalArgumentException {
        validateVertices();
        var sourceOutEdges = getOutEdges( source );
        sourceOutEdges.add( new Edge( source, destination, weight ) );
    }

    public List<Edge> getOutEdges( int vertexIndex ) throws IndexOutOfBoundsException {
        return adjacencyList.get( vertexIndex );
    }

    private void validateVertices( int... vertices ) throws IllegalArgumentException {
        // TODO: we can simply check largest vertex and compare with size
        // TODO: use Validators with context instead
        for ( int vertex : vertices ) {
            try {
                // get will check if vertex exists here since it will throw an exception
                adjacencyList.get( vertex );
            } catch ( IndexOutOfBoundsException e ) {
                throw new IllegalArgumentException( "Vertex " + vertex + " doesn't exist" );
            }
        }
    }

    public void addBidirectionalEdge( int source, int destination, int weight ) throws IllegalArgumentException {
        addEdge( source, destination, weight );
        addEdge( destination, source, weight );
    }

    public int size() {
        return adjacencyList.size();
    }

    @Override
    public Graph clone() {
        Graph result = new Graph();
        result.addVertices( this.size() );

        for ( int i = 0 ; i < this.size(); ++i ) {
            this.getOutEdges( i ).forEach( e -> result.addEdge( e.getSource(), e.getDestination(), e.getWeight() ) );
        }

        return result;
    }

    public List<Edge> getEdges() {
        return adjacencyList.stream().flatMap( Collection::stream ).collect( Collectors.toList() );
    }
}
