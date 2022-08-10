package org.thekiddos;

import org.thekiddos.datastructures.Edge;
import org.thekiddos.datastructures.Graph;

import java.util.*;

public class MinSCSS {
    private final Graph graph;
    private int i = 0;
    private int r = 0;
    private VertexStatus[] visited;  // 0 unvisited, 1 active, 2 finished make enum later
    private int[] seq;
    Set<Edge> E = new HashSet<>();
    int[] p;
    Edge[] hbe;
    List<List<Integer>> T = new ArrayList<>();

    public MinSCSS( Graph graph ) {
        this.graph = graph;
        visited = new VertexStatus[ graph.size() ];
        Arrays.fill( visited, VertexStatus.UNVISITED );
        p = new int[ graph.size() ];
        seq = new int[ graph.size() ];
        hbe = new Edge[ graph.size() ];
    }

    // https://www.sciencedirect.com/science/article/abs/pii/S0020019002004763
    public Set<Edge> operate() {
        List<Integer> rootSet = new ArrayList<>();
        rootSet.add( r );
        T.add( rootSet );

        DFS( r );

        for ( int v = 0; v < graph.size(); ++v ) {
            if ( v == find( v ) && v != r ) {
                E.add( new Edge( p[ v ], v, 1 ) );
                E.add( hbe[ v ] );
            }
        }

        return E;
    }

    private void DFS( int v ) {
        visited[ v ] = VertexStatus.ACTIVE;
        ++i;
        seq[ v ] = i;
        hbe[ v ] = new Edge( v, v, 1 );

        for ( var edge : graph.getOutEdges( v ) ) {
            var w = edge.getDestination();
            if ( visited[ w ].equals( VertexStatus.UNVISITED ) ) {
                grow( v, w );
                p[ w ] = v;
                DFS( w );
            } else if ( visited[ find( w ) ].equals( VertexStatus.FINISHED ) ) {
                tryContract( edge );
            } else {
                var s = find( v );
                if ( seq[ find( w ) ] < seq[ find( hbe[ s ].getDestination() ) ] ) {
                    hbe[ s ] = edge;
                }
            }
        }

        visited[ v ] = VertexStatus.FINISHED;
        if ( v == find( v ) ) {
            tryContract( hbe[ v ] );
        }
    }

    private int find( int v ) {
        return T.stream().filter( l -> l.contains( v ) ).findFirst().orElse( List.of( r ) ).get( 0 );
    }

    private void grow( int v, int w ) {
        List<Integer> newSet = new ArrayList<>();
        newSet.get( w );
        T.add( newSet );
        p[ w ] = v;
    }

    private void link( int w ) {
        var wSet = T.stream().filter( l -> l.contains( w ) ).findFirst().orElse( List.of( r ) );
        T.remove( wSet );
        var v = p[ w ];
        var vSet = T.stream().filter( l -> l.contains( v ) ).findFirst().orElse( List.of( r ) );
        T.remove( vSet );

        for ( var vertex : wSet ) {
            if ( !vSet.contains( vertex ) )
                vSet.add( vertex );
        }

        T.add( vSet );
    }

    private void tryContract( Edge e ) {
        var v = find( e.getSource() );
        var w = find( e.getDestination() );

        if ( ( v == w || v == find( p[ w ] ) || w == find( p[ v ] ) ) ) return;

        E.add( e );
        while ( visited[ w ].equals( VertexStatus.FINISHED ) ) {
            E.add( hbe[ w ] );
            link( w );
            w = find( p[ w ] );
        }

        while ( v != w ) {
            if ( seq[ find( hbe[ w ].getDestination() ) ] > seq[ find( hbe[ v ].getDestination() ) ] ) {
                hbe[ w ] = hbe[ v ];
            }
            E.add( new Edge( p[ v ], v, 1 ) );
            link( v );
            v = find( p[ v ] );
        }

    }
}
