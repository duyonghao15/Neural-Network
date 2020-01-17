package org.optaplanner.examples.cneat.tools;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.examples.cneat.structure.Network;
import org.optaplanner.examples.cneat.structure.Node;
import org.optaplanner.examples.common.domain.AbstractPersistable;

import java.io.Serializable;
import java.util.Comparator;

public class FitnessComparatorUp implements Comparator<Network>, Serializable {

    @Override
    public int compare(Network a, Network b) {

        double aFitness = a.getFitness();
        double bFitness = b.getFitness();
        return new CompareToBuilder()
                .append(aFitness, bFitness)
                .toComparison();
    }



}
