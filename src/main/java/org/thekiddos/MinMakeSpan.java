package org.thekiddos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

@AllArgsConstructor
@Getter
@Setter
public class MinMakeSpan {
    private List<Integer> processTimes;
    private int numberOfMachines;

    public List<List<Integer>> solve() {
        var solution = new ArrayList<List<Integer>>( numberOfMachines );
        int numberOfJobs = processTimes.size();

        for ( int j = 0; j < numberOfMachines; ++j )
            solution.add( new ArrayList<>() );

        for ( int i = 0; i < numberOfJobs; ++i ) {
            int j = getMinLoadedMachine( solution );
            solution.get( j ).add( i );
        }

        return solution;
    }

    private int getMinLoadedMachine( ArrayList<List<Integer>> solution ) {
        return IntStream.range( 0, solution.size() ).boxed().reduce(
                ( s1, s2 ) -> solution.get( s1 ).stream().mapToInt( value -> processTimes.get( value ) ).sum() <=
                        solution.get( s2 ).stream().mapToInt( value -> processTimes.get( value ) ).sum() ? s1 : s2
        ).orElse( 0 );
    }
}
