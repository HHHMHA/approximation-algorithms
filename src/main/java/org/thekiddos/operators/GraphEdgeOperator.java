package org.thekiddos.operators;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.thekiddos.datastructures.Edge;
import org.thekiddos.datastructures.Graph;

import java.util.ArrayList;
import java.util.List;


// TODO: use generics to make operate just returns a List of custom types
@AllArgsConstructor
public abstract class GraphEdgeOperator {
    @Getter( AccessLevel.PROTECTED )
    private final Graph graph;

    public List<Edge> operate() {
        List<Edge> result = new ArrayList<>();
        runOperator( result );
        return result;
    }

    protected abstract void runOperator( List<Edge> result );
}
