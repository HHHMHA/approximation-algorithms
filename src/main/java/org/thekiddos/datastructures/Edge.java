package org.thekiddos.datastructures;

import lombok.*;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public final class Edge implements Comparable<Edge> {
    @NonNull
    private int source;
    @NonNull
    private int destination;
    @Setter
    @NonNull
    private int weight;
    @Setter
    EdgeType type = EdgeType.NONE;

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Edge edge = (Edge) o;
        return source == edge.source && destination == edge.destination && weight == edge.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash( source, destination, weight );
    }

    @Override
    public int compareTo( Edge o ) {
        return Integer.compare( weight, o.weight );
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", destination=" + destination +
                ", weight=" + weight +
                ", type=" + type +
                '}';
    }
}
