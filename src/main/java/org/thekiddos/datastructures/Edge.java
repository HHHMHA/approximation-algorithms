package org.thekiddos.datastructures;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public final class Edge implements Comparable<Edge> {
    private int source;
    private int destination;
    @Setter
    private int weight;

    @Override
    public int compareTo( Edge o ) {
        return Integer.compare( weight, o.weight );
    }
}
