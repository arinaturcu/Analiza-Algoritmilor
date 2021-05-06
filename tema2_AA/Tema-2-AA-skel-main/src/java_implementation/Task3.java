// Copyright 2020
// Author: Matei Simtinică

import java.io.*;
import java.util.*;

/**
 * Task3
 * This being an optimization problem, the solve method's logic has to work differently.
 * You have to search for the minimum number of arrests by successively querying the oracle.
 * Hint: it might be easier to reduce the current task to a previously solved task
 */
public class Task3 extends Task {
    String task2InFilename;
    String task2OutFilename;
    int familiesNumber;
    int relationshipsNumber;
    boolean[][] graph;
    // list that contains all families that shouldn't be arrested
    List<Integer> toNotBeArrested;

    /**
     * The problem is reduced to the one solved at task 2 by building the complement graph.
     * Then task 2 solver is called for detecting the biggest clique in the complement graph
     * that will represent the families that shouldn't be arrested.
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void solve() throws IOException, InterruptedException {
        task2InFilename = inFilename + "_t2";
        task2OutFilename = outFilename + "_t2";
        Task2 task2Solver = new Task2();
        task2Solver.addFiles(task2InFilename, oracleInFilename, oracleOutFilename, task2OutFilename);
        readProblemData();

        // implement a way of successively querying the oracle (using Task2) about various arrest numbers until you
        // find the minimum
        toNotBeArrested = new LinkedList<>();
        buildComplement(graph);

        for (int i = familiesNumber; i > 0; --i) {
            reduceToTask2(i);
            task2Solver.solve();

            if (task2Solver.hasSolution()) {
                extractAnswerFromTask2();
                break;
            }
        }
        writeAnswer();
    }

    /**
     * Builds the complement graph
     * @param graph initial graph
     */
    private void buildComplement(boolean[][] graph) {
        for (int i = 1; i <= familiesNumber; ++i) {
            for (int j = i + 1; j <= familiesNumber; ++j) {
                graph[i][j] = !graph[i][j];
                graph[j][i] = !graph[j][i];
            }
        }
    }

    @Override
    public void readProblemData() throws IOException {
        // read the problem input (inFilename) and store the data in the object's attributes
        BufferedReader reader = new BufferedReader(new FileReader(inFilename));

        String[] line = reader.readLine().split(" ");
        familiesNumber = Integer.parseInt(line[0]);
        relationshipsNumber = Integer.parseInt(line[1]);

        graph = new boolean[familiesNumber + 1][familiesNumber + 1];
        for (int i = 0; i < relationshipsNumber; ++i) {
            String[] edge = reader.readLine().split(" ");
            int src = Integer.parseInt(edge[0]);
            int dest = Integer.parseInt(edge[1]);

            graph[src][dest] = true;
            graph[dest][src] = true;
        }
    }

    public void reduceToTask2(int cliqueSize) throws IOException {
        // reduce the current problem to Task2
        FileWriter fileWriter = new FileWriter(task2InFilename);
        fileWriter.write(familiesNumber + " "
                + (familiesNumber*(familiesNumber - 1) / 2 - relationshipsNumber + " ")
                + cliqueSize + "\n");

        for (int i = 1; i <= familiesNumber; ++i) {
            for (int j = i + 1; j <= familiesNumber; ++j) {
                if (graph[i][j]) {
                    fileWriter.write(i + " " + j + "\n");
                }
            }
        }

        fileWriter.close();
    }

    public void extractAnswerFromTask2() throws IOException {
        // extract the current problem's answer from Task2's answer
        BufferedReader reader = new BufferedReader(new FileReader(task2OutFilename));
        reader.readLine();

        String[] v = reader.readLine().split(" ");
        for (String s : v) {
            toNotBeArrested.add(Integer.parseInt(s));
        }
    }

    @Override
    public void writeAnswer() throws IOException {
        // write the answer to the current problem (outFilename)
        FileWriter fileWriter = new FileWriter(outFilename);
        for (int i = 1; i <= familiesNumber; ++i) {
            if (!toNotBeArrested.contains(i)) {
                fileWriter.write(i + " ");
            }
        }

        fileWriter.close();
    }
}
