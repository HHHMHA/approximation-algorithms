package org.thekiddos.operators;

import org.thekiddos.datastructures.Edge;
import org.thekiddos.datastructures.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class EulerCycle extends GraphOrderedVerticesOperator {

    public EulerCycle( Graph graph, int source ) {
        super( graph, source );
    }

    @Override
    protected void runOperator( List<Integer> result, int vertex ) {
        List<Edge> visited = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        stack.add( vertex );

        while ( !stack.isEmpty() ) {
            int v = stack.pop();
            result.add( v );
            var edge = getGraph().getOutEdges( v ).stream().filter( e -> !visited.contains( e ) && !result.contains( e.getDestination() ) ).findFirst().orElse( null );
            if ( edge == null ) {
                edge = getGraph().getOutEdges( v ).stream().filter( e -> !visited.contains( e ) ).findFirst().orElse( null );
            }
            if ( edge != null ) {
                visited.add( edge );
                visited.add( new Edge( edge.getDestination(), edge.getSource(), edge.getWeight() ) );
                stack.add( edge.getDestination() );
            }
        }

    }
}
