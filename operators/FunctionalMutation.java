package org.optaplanner.examples.cneat.operators;

import org.optaplanner.examples.cneat.structure.Connection;
import org.optaplanner.examples.cneat.structure.Network;
import org.optaplanner.examples.cneat.structure.Node;

import java.util.List;

import static org.optaplanner.examples.cneat.algorithm.Fitness.fitness;
import static org.optaplanner.examples.cneat.operators.Update.updateNetwork;

public class FunctionalMutation {


    // ************ 1. Node功能变异操作 ************
    public static void functionalMutationOfNode(Network network, List<double[]> inputsList, List<double[]> outputsList) {
        Node node = network.getNodeList().get((int) (Math.random() * network.getNodeList().size()));
        String activeFunction = node.getActivationFunction();
        double fitness = network.getFitness();

        int random = (int) (Math.random() * node.getActivationFunctionList().size());
        String newActiveFunction = node.getActivationFunctionList().get(random);
        node.setActivationFunction(newActiveFunction);
        updateNetwork(network);
        double newFitness = fitness(network, inputsList, outputsList);
        network.setFitness(newFitness);

        if(newFitness > fitness) {
            node.setActivationFunction(activeFunction);    // 若适应度值反而升高, 再变回去
            updateNetwork(network);
            network.setFitness(fitness);
        }
        /* function ends */
    }


    // ************ 2. Connection功能变异操作 ************
    public static void functionalMutationOfConnection(Network network, List<double[]> inputsList, List<double[]> outputsList) {
        Connection connection = network.getConnectionList().get((int) (Math.random() * network.getConnectionList().size()));
        double fitness = network.getFitness();

        connection.setActive(!connection.isActive());     // 反向
        updateNetwork(network);
        double newFitness = fitness(network, inputsList, outputsList);
        network.setFitness(newFitness);

        if(fitness(network, inputsList, outputsList) > fitness) {
            connection.setActive(!connection.isActive()); // 若适应度值反而升高, 再变回去
            updateNetwork(network);
            network.setFitness(fitness);
        }
        /* function ends */
    }


/* class ends */
}
