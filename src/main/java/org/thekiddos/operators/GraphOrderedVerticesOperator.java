package org.thekiddos.operators;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.thekiddos.datastructures.Graph;

import java.util.ArrayList;
import java.util.List;


// TODO: use generics to make operate just returns a List of custom types
@AllArgsConstructor
public abstract class GraphOrderedVerticesOperator {
    @Getter( AccessLevel.PROTECTED )
    private final Graph graph;
    @Setter
    private int source;

    public List<Integer> operate() {
        List<Integer> result = new ArrayList<>();
        runOperator( result, source );
        return result;
    }

    protected abstract void runOperator( List<Integer> result, int source );
}
