package org.optaplanner.examples.cneat.algorithm;

import org.optaplanner.examples.cneat.structure.Network;

import java.util.List;

import static org.optaplanner.examples.cneat.algorithm.Fitness.fitness;
import static org.optaplanner.examples.cneat.operators.FunctionalMutation.functionalMutationOfConnection;
import static org.optaplanner.examples.cneat.operators.FunctionalMutation.functionalMutationOfNode;
import static org.optaplanner.examples.cneat.operators.NumericalMutation.numericalMutationOfConnection;
import static org.optaplanner.examples.cneat.operators.NumericalMutation.numericalMutationOfNode;
import static org.optaplanner.examples.cneat.operators.StructuralMutation.structuralMutationOfConnection;
import static org.optaplanner.examples.cneat.operators.StructuralMutation.structuralMutationOfNode;

public class LocalSearch {


    // ************ 1. 对个体进行局部搜索 ************
    public static void localSearch(Network network, List<double[]> inputsList, List<double[]> outputsList) {

        for(int i = 0 ; i < 50; i ++) {
            numericalMutationOfNode(network, inputsList, outputsList);
            numericalMutationOfConnection(network, inputsList, outputsList);
            functionalMutationOfNode(network, inputsList, outputsList);
            functionalMutationOfConnection(network, inputsList, outputsList);
        }

    }


    // ************ 2. 对种群进行局部搜索 ************
    public static void localSearch(List<Network> networkList, List<double[]> inputsList, List<double[]> outputsList) {

        for(Network network : networkList) {
            System.out.println("对 " + network + " 进行局部搜索 ......");
            localSearch(network, inputsList, outputsList);
        }
        fitness(networkList);
    }

}
