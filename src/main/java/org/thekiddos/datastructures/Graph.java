package org.thekiddos.datastructures;

import java.util.ArrayList;
import java.util.List;

public class Graph {
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
}
