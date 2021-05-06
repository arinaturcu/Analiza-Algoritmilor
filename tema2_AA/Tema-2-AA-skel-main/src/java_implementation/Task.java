// Copyright 2020
// Author: Matei Simtinică

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * This is the abstract base class for all tasks that have to be implemented.
 */
public abstract class Task {
    String inFilename;
    String oracleInFilename;
    String oracleOutFilename;
    String outFilename;

    public abstract void solve() throws IOException, InterruptedException;

    public abstract void readProblemData() throws IOException;

    public void formulateOracleQuestion() throws IOException {}

    public void decipherOracleAnswer() throws IOException {}

    public abstract void writeAnswer() throws IOException;

    /**
     * Stores the files paths as class attributes.
     *
     * @param inFilename         the file containing the problem input
     * @param oracleInFilename   the file containing the oracle input
     * @param oracleOutFilename  the file containing the oracle output
     * @param outFilename        the file containing the problem output
     */
    public void addFiles(String inFilename, String oracleInFilename, String oracleOutFilename, String outFilename) {
        this.inFilename = inFilename;
        this.oracleInFilename = oracleInFilename;
        this.oracleOutFilename = oracleOutFilename;
        this.outFilename = outFilename;
    }

    /**
     * Asks the oracle for an answer to the formulated question.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void askOracle() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.redirectErrorStream(true);
        builder.command("python3", "sat_oracle.py", oracleInFilename, oracleOutFilename);
        Process process = builder.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String buffer;
        StringBuilder output = new StringBuilder();

        while ((buffer = in.readLine()) != null) {
            output.append(buffer).append("\n");
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.err.println("Error encountered while running oracle");
            System.err.println(output.toString());
            System.exit(-1);
        }
    }

    public boolean[][] readAndConfigureGraphMatrix(BufferedReader reader, int nodes,
                                                   int edges) throws IOException {
        boolean[][] graph = new boolean[nodes + 1][nodes + 1];
        for (int i = 0; i < edges; ++i) {
            String[] edge = reader.readLine().split(" ");
            int src = Integer.parseInt(edge[0]);
            int dest = Integer.parseInt(edge[1]);

            graph[src][dest] = true;
            graph[dest][src] = true;
        }

        return graph;
    }

    public int[][] setVariablesNames(int rows, int columns) {
        int[][] variablesNames = new int[rows + 1][columns + 1];
        int count = 1;
        for (int i = 1; i <= rows; ++i) {
            for (int j = 1; j <= columns; ++j) {
                variablesNames[i][j] = count++;
            }
        }

        return variablesNames;
    }
}
