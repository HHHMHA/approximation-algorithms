package org.thekiddos;

import lombok.AllArgsConstructor;
import org.thekiddos.datastructures.Edge;
import org.thekiddos.datastructures.Graph;
import org.thekiddos.operators.ChainDecomposition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GraphConnectivityClassifier {
    private Graph graph;

    public String classify() {
        var chains = new ChainDecomposition( graph ).operate();
        var allEdges = new ArrayList<Edge>();
        for ( int i = 0; i < graph.size(); ++i )
            allEdges.addAll( graph.getOutEdges( i ) );

        for ( var chain : chains ) {
            allEdges.removeAll( chain );
            allEdges.removeAll( chain.stream().map( e -> new Edge( e.getDestination(), e.getSource(), e.getWeight() ) ).collect( Collectors.toList()) );
        }

        if ( !allEdges.isEmpty() ) return "not 2-edge-connected";

        if ( is2EdgeConnectedOnly( chains ) ) return "2-edge-connected but not 2-connected";

        return "2-connected";
    }

    private boolean is2EdgeConnectedOnly( List<List<Edge>> chains ) {
        if ( chains.size() > 1 ) {
            for ( int i = 1; i < chains.size(); ++i ) {
                if ( isCycle( chains.get( i ) ) )
                    return true;
            }
        }
        return false;
    }

    private boolean isCycle( List<Edge> chain ) {
        return chain.get( 0 ).getSource() == chain.get( chain.size() - 1 ).getDestination();
    }
}
