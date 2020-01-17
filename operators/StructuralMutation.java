package org.optaplanner.examples.cneat.operators;

import org.optaplanner.examples.cneat.structure.Connection;
import org.optaplanner.examples.cneat.structure.Network;
import org.optaplanner.examples.cneat.structure.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.optaplanner.examples.cneat.algorithm.Fitness.fitness;
import static org.optaplanner.examples.cneat.algorithm.Initializer.newConnection;
import static org.optaplanner.examples.cneat.algorithm.Initializer.newNode;
import static org.optaplanner.examples.cneat.operators.Update.updateNetwork;

public class StructuralMutation {


    // ************ 1. Node结构变异操作 ************
    public static void structuralMutationOfNode(Network network, List<double[]> inputsList, List<double[]> outputsList) {
        double fitness = network.getFitness();
        List<Connection> connectionList = network.getConnectionList();
        List<Node> nodeList = network.getNodeList();

        if(Math.random() > 0.5) {             // 增
            if(nodeList.size() > 60) {        // 节点总数不能太多
                return;
            }
            long maxNodeId = nodeList.get(nodeList.size() - 1).getId();                  // 目前最大的node Id
            long maxConnectionId = connectionList.get(connectionList.size() - 1).getId();// 目前最大的connection Id
            Node newNode = newNode(maxNodeId + 1, "hidden", "null");// 新建node

            // 新增一个node, 插入原本的一个connection(变成两个connection)
            // a. 随机挑一个connection
            Connection randomConnection = connectionList.get((int) (Math.random() * connectionList.size()));
            Node startNode = randomConnection.getStartNode();
            Node endNode = randomConnection.getEndNode();
            // b. 再新建两个connection, 从新node出发
            Connection newConnection_1 = newConnection(maxConnectionId + 1, startNode, newNode);
            Connection newConnection_2 = newConnection(maxConnectionId + 2, newNode, endNode);
            // c. 更新list
            randomConnection.setActive(false);
            connectionList.add(newConnection_1);
            connectionList.add(newConnection_2);
            updateNetwork(network);                                                      // 根据新的connectionList更新network
            double newFitness = fitness(network, inputsList, outputsList);
            network.setFitness(newFitness);

            if(newFitness > fitness) {                    // 若适应度值反而升高, 再变回去
                randomConnection.setActive(true);
                connectionList.remove(newConnection_1);
                connectionList.remove(newConnection_2);
                updateNetwork(network);
                network.setFitness(fitness);
            }
        } else {                              // 减
            Node randomNode = nodeList.get((int) (Math.random() * nodeList.size()));     // 随机点
            if(randomNode.getLayerType().equals("hidden") && connectionList.size() > 2) {// 可删的情况(hidden Node 或 connection >2)
                /*for(int i = connectionList.size() - 1 ; i >= 0 ; i --) {
                    Connection connection = connectionList.get(i);
                    if(connection.getStartNode().getId() == randomNode.getId() || connection.getEndNode().getId() == randomNode.getId()) {
                        connectionList.remove(i);
                    }
                }*/
                Set<Connection> toDeleteSet = new HashSet<>();
                toDeleteSet.addAll(randomNode.getInConnectionSet());
                toDeleteSet.addAll(randomNode.getOutConnectionSet());
                connectionList.removeAll(toDeleteSet);
                updateNetwork(network);                                                 // 根据新的connectionList更新network
                double newFitness = fitness(network, inputsList, outputsList);
                network.setFitness(newFitness);

                if(newFitness > fitness) {               // 若适应度值反而升高, 再变回去
                    connectionList.addAll(toDeleteSet);
                    updateNetwork(network);
                    network.setFitness(fitness);
                }
            }
        }
        /* function ends */
    }


    // ************ 2. Connection结构变异操作 (增/减一个connection) ************
    public static void structuralMutationOfConnection(Network network, List<double[]> inputsList, List<double[]> outputsList) {
        double fitness = network.getFitness();
        List<Connection> connectionList = network.getConnectionList();
        List<Node> nodeList = network.getNodeList();

        if(Math.random() > 0.5) {                                                        // 增
            long maxId = connectionList.get(connectionList.size() - 1).getId();          // 目前最大的Id
            Node startNode = nodeList.get((int) (Math.random() * nodeList.size()));
            Node endNode = nodeList.get((int) (Math.random() * nodeList.size()));
            if(startNode != endNode) {
                Connection connection = newConnection(maxId + 1, startNode, endNode);
                connectionList.add(connection);                                         // (!!!!!不会产生新node)
                updateNetwork(network);                                                 // 根据新的connectionList更新network
                double newFitness = fitness(network, inputsList, outputsList);
                network.setFitness(newFitness);

                if(newFitness > fitness) {                                              // 若适应度值反而升高, 再变回去
                    connectionList.remove(connection);
                    updateNetwork(network);
                    network.setFitness(fitness);
                }
            }
        } else {                                                                        // 减
            if(connectionList.size() > 2) {                                             // 不能太少
                int random = (int) (Math.random() * connectionList.size());
                Connection removeConnection = connectionList.get(random);
                connectionList.remove(random);                                          // 删除connection
                updateNetwork(network);                                                 // 根据变异后的connectionList更新network
                double newFitness = fitness(network, inputsList, outputsList);
                network.setFitness(newFitness);

                if(newFitness > fitness) {                                              // 若适应度值反而升高, 再变回去
                    connectionList.add(removeConnection);
                    updateNetwork(network);
                    network.setFitness(fitness);
                }
            }
        }
        /* function ends */
    }


/* class ends */
}
