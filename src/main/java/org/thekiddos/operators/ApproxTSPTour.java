package org.thekiddos.operators;

import org.thekiddos.datastructures.Graph;
import org.thekiddos.transformers.MSTPrim;

import java.util.List;

public class ApproxTSPTour extends GraphOrderedVerticesOperator {
    public ApproxTSPTour( Graph graph, int source ) {
        super( graph, source );
    }

    @Override
    protected void runOperator( List<Integer> result, int source ) {
        var tree = new MSTPrim( getGraph(), source ).transform();
        result.addAll( new PreOrderDFS( tree, source ).operate() );
    }
}
