package org.thekiddos.operators;

import org.thekiddos.datastructures.Edge;
import org.thekiddos.datastructures.Graph;
import org.thekiddos.utils.Simplex;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApproxMinWeightVertexCover extends GraphOrderedVerticesOperator {
    private List<Integer> weights;

    public ApproxMinWeightVertexCover( Graph graph, int source, List<Integer> weights ) {
        super( graph, source );
        this.weights = weights;
    }

    // TODO: we are violating some principles here since we force source argument and we don't need it
    @Override
    protected void runOperator( List<Integer> result, int source ) {
        String[] equations = getEquations();
        String objective = getObjective();
        Simplex.input( weights.size(), objective, false, equations );
        var solution = Simplex.solve();
        var selectedVertices = solution.stream().map( value -> (int) Math.round( value ) ).collect( Collectors.toList() );
        for ( int i = 0; i < selectedVertices.size(); ++i )
            if ( selectedVertices.get( i ) == 1 )
                result.add( i );
    }

    private String getObjective() {
        StringBuilder objective = new StringBuilder();
        for ( Integer weight : weights ) objective.append( "+" ).append( weight );
        return objective.toString();
    }

    private String[] getEquations() {
        List<String> equations = new ArrayList<>();

        var g = getGraph();
        for ( int i = 0; i < g.size(); ++i ) {
            StringBuilder equationBuilder = new StringBuilder();
            for ( int j = 0; j < g.size(); ++j ) {
                String value = i == j ? "+1" : "+0";
                equationBuilder.append( value );
            }
            equations.add( equationBuilder + "<=+1" );
            equations.add( equationBuilder + ">=+0" );
        }

        List<Edge> visited = new ArrayList<>();
        for ( int i = 0; i < g.size(); ++i ) {
            for ( var edge : g.getOutEdges( i ) ) {
                int destination = edge.getDestination();
                if ( visited.contains( edge ) )
                    continue;
                visited.add( edge );
                visited.add( new Edge( destination, i, edge.getWeight() ) );

                StringBuilder equationBuilder = new StringBuilder();
                for ( int j = 0; j < g.size(); ++j ) {
                    String value = i == j || destination == j ? "+1" : "+0";
                    equationBuilder.append( value );
                }
                equationBuilder.append( ">=+1" );
                equations.add( equationBuilder.toString() );
            }
        }

        return equations.toArray( String[]::new );
    }
}
