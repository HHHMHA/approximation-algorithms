package org.thekiddos.operators;

import org.thekiddos.datastructures.Graph;
import org.thekiddos.transformers.MSTPrim;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModifiedChristofides extends GraphOrderedVerticesOperator {
    public ModifiedChristofides( Graph graph, int source ) {
        super( graph, source );
    }

    @Override
    protected void runOperator( List<Integer> result, int source ) {
        var tree = new MSTPrim( getGraph(), source ).transform();
        var oddVertices = getOddDegreeVertices( tree );
        var newEdges = new GreedyMWPM( getGraph(), oddVertices ).operate();
        for ( var edge : newEdges) {
            tree.addEdge( edge.getSource(), edge.getDestination(), edge.getWeight() );
        }
        Set<Integer> set = new HashSet<>( new EulerCycle( tree, source ).operate() );
        result.addAll( set );
    }

    private List<Integer> getOddDegreeVertices( Graph tree ) {
        List<Integer> results = new ArrayList<>();
        for ( int i = 0; i < tree.size(); ++i )
            if ( tree.getOutEdges( i ).size() % 2 == 1 )
                results.add( i );
        return results;
    }
}
