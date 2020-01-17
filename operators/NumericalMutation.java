package org.optaplanner.examples.cneat.operators;

import org.optaplanner.examples.cneat.structure.Connection;
import org.optaplanner.examples.cneat.structure.Network;
import org.optaplanner.examples.cneat.structure.Node;

import java.util.List;
import java.util.Random;

import static org.optaplanner.examples.cneat.algorithm.Fitness.fitness;
import static org.optaplanner.examples.cneat.operators.Update.updateNetwork;

public class NumericalMutation {


    // ************ 1. Node数值变异操作 ************
    public static void numericalMutationOfNode(Network network, List<double[]> inputsList, List<double[]> outputsList) {
        Node node = network.getNodeList().get((int) (Math.random() * network.getNodeList().size()));
        double biasValue = node.getBiasValue();
        double meanValue = node.getMeanValue();
        double fitness = network.getFitness();

        Random r = new Random();
        double newBiasValue = Math.sqrt(node.getVariance()) * r.nextGaussian() + node.getMeanValue();
        node.setBiasValue(newBiasValue);
        node.setMeanValue(newBiasValue);                // 重新以新的值为均值
        updateNetwork(network);
        double newFitness = fitness(network, inputsList, outputsList);
        network.setFitness(newFitness);

        if(newFitness > fitness) {
            node.setBiasValue(biasValue);               // 若适应度值反而升高, 再变回去
            node.setMeanValue(meanValue);
            updateNetwork(network);
            network.setFitness(fitness);
        }
        /* function ends */
    }


    // ************ 2. connection数值变异操作 ************
    public static void numericalMutationOfConnection(Network network, List<double[]> inputsList, List<double[]> outputsList) {
        Connection connection = network.getConnectionList().get((int) (Math.random() * network.getConnectionList().size()));
        double weight = connection.getWeight();
        double meanValue = connection.getMeanValue();
        double fitness = network.getFitness();

        Random r = new Random();
        double newWeight = Math.sqrt(connection.getVariance()) * r.nextGaussian() + connection.getMeanValue();
        connection.setWeight(newWeight);
        connection.setMeanValue(newWeight);             // 重新以新的值为均值
        updateNetwork(network);
        double newFitness = fitness(network, inputsList, outputsList);
        network.setFitness(newFitness);

        if(newFitness > fitness) {
            connection.setWeight(weight);               // 若适应度值反而升高, 再变回去
            connection.setMeanValue(meanValue);
            updateNetwork(network);
            network.setFitness(fitness);
        }
        /* function ends */
    }


/* class ends */
}
