package org.thekiddos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter @Getter
public class FirstFitBinPacking {
    private int binWeight;
    private List<Integer> itemsWeights;

    public FirstFitBinPacking( int binWeight, List<Integer> itemsWeights ) {
        this.binWeight = binWeight;
        this.itemsWeights = new ArrayList<>( itemsWeights );
    }

    public int solve() {
        List<Integer> bins = new ArrayList<>();

        itemsWeights.sort( Collections.reverseOrder() );

        for ( var item : itemsWeights ) {
            addToBins( bins, item );
        }

        return bins.size();
    }

    private void addToBins( List<Integer> bins, Integer item ) {
        for ( int i = 0; i < bins.size(); ++i ) {
            var binCapacity = bins.get( i );
            if ( binCapacity >= item ) {
                bins.set( i, binCapacity - item );
                return;
            }
        }

        if ( binWeight < item )
            return;

        bins.add( binWeight - item );
    }
}
