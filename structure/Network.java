package org.optaplanner.examples.cneat.structure;

import org.optaplanner.examples.common.domain.AbstractPersistable;

import java.util.List;

public class Network extends AbstractPersistable {

    private List<Node> nodeList;
    private List<Connection> connectionList;

    private double fitness;

    // getter and setter
    public List<Node> getNodeList() {
        return nodeList;
    }
    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }
    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    public double getFitness() {
        return fitness;
    }
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

}
