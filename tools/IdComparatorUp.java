package org.optaplanner.examples.cneat.tools;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.examples.cneat.structure.Node;
import org.optaplanner.examples.common.domain.AbstractPersistable;

import java.io.Serializable;
import java.util.Comparator;

public class IdComparatorUp implements Comparator<AbstractPersistable>, Serializable {

    @Override
    public int compare(AbstractPersistable a, AbstractPersistable b) {

        long aId = a.getId();
        long bId = b.getId();
        return new CompareToBuilder()
                .append(aId, bId)                 // b(1) > a(0), 则a排在前面, b排在后面
                .toComparison();
    }



}
