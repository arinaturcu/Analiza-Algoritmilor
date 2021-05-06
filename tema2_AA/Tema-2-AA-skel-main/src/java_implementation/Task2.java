// Copyright 2020
// Author: Matei Simtinică

import java.io.*;
import java.util.Scanner;

/**
 * Task2
 * You have to implement 4 methods:
 * readProblemData         - read the problem input and store it however you see fit
 * formulateOracleQuestion - transform the current problem instance into a SAT instance and write the oracle input
 * decipherOracleAnswer    - transform the SAT answer back to the current problem's answer
 * writeAnswer             - write the current problem's answer
 */
public class Task2 extends Task {
    // define necessary variables and/or data structures
    private int familiesNumber;
    private int relationshipsNumber;
    private int cliqueSize;
    private boolean hasSolution;

    /*
     * This matrix holds (at first) the names of all the variables.
     * When reading the oracle answer, every variable name will be
     * replaces with the specific value.
     */
    private int[][] variables;
    private boolean[][] graph;

    @Override
    public void solve() throws IOException, InterruptedException {
        readProblemData();
        formulateOracleQuestion();
        askOracle();
        decipherOracleAnswer();
        writeAnswer();
    }

    @Override
    public void readProblemData() throws IOException {
        // read the problem input (inFilename) and store the data in the object's attributes
        BufferedReader reader = new BufferedReader(new FileReader(inFilename));

        String[] line = reader.readLine().split(" ");
        familiesNumber = Integer.parseInt(line[0]);
        relationshipsNumber = Integer.parseInt(line[1]);
        cliqueSize = Integer.parseInt(line[2]);

        graph = readAndConfigureGraphMatrix(reader, familiesNumber, relationshipsNumber);
        variables = setVariablesNames(cliqueSize, familiesNumber);
    }

    @Override
    public void formulateOracleQuestion() throws IOException {
        // transform the current problem into a SAT problem and write it (oracleInFilename) in a format
        // understood by the oracle
        FileWriter fileWriter = new FileWriter(oracleInFilename);

        fileWriter.write("p cnf ");
        /* Variables number */
        fileWriter.write(cliqueSize * familiesNumber + " ");
        /* Clauses number */
        fileWriter.write(cliqueSize
                + (familiesNumber*(familiesNumber - 1)/2 - relationshipsNumber) * cliqueSize*(cliqueSize - 1)
                + familiesNumber*(cliqueSize*(cliqueSize - 1))/2
                + cliqueSize*(familiesNumber*(familiesNumber - 1))/2
                + "\n");

        /*
         * First set of clauses: an index from the clique must represent at least
         * one family.
         * X_i1 | X_i2 | ... | X_in must be true, n is number of families and i is
         * index in the clique
         */
        for (int i = 1; i <= cliqueSize; ++i) {
            for (int n = 1; n <= familiesNumber; ++n) {
                fileWriter.write(variables[i][n] + " ");
            }
            fileWriter.write("0\n");
        }

        /*
         * Second set of clauses: two families that aren't in a relationship
         * can't be both in the clique
         */
        for (int i = 1; i <= familiesNumber; ++i) {
            for (int j = i + 1; j <= familiesNumber; ++j) {
                if (!graph[i][j]) {
                    for (int k = 1; k <= cliqueSize; ++k) {
                        for (int m = 1; m <= cliqueSize; ++m) {
                            if (k != m) {
                                fileWriter.write(-variables[k][i] + " " + -variables[m][j] + " 0\n");
                            }
                        }
                    }
                }
            }
        }

        /*
         * Third set of clauses: a family can't appear twice in a clique and
         * two different families can't be on the same index in a clique
         */
        for (int v = 1; v <= familiesNumber; ++v) {
            for (int i = 1; i <= cliqueSize; ++i) {
                for (int j = i + 1; j <= cliqueSize; ++j) {
                    fileWriter.write(-variables[i][v] + " " + -variables[j][v] + " 0\n");
                }
            }
        }

        for (int i = 1; i <= cliqueSize; ++i) {
            for (int v = 1; v <= familiesNumber; ++v) {
                for (int w = v + 1; w <= familiesNumber; ++w) {
                    fileWriter.write(-variables[i][v] + " " + -variables[i][w] + " 0\n");
                }
            }
        }

        fileWriter.close();
    }

    @Override
    public void decipherOracleAnswer() throws IOException {
        // extract the current problem's answer from the answer given by the oracle (oracleOutFilename)
        BufferedReader reader = new BufferedReader(new FileReader(oracleOutFilename));

        String response = reader.readLine();
        if (response.equals("False")) {
            hasSolution = false;
            return;
        }

        if (response.equals("True")) {
            hasSolution = true;
        }

        int n = Integer.parseInt(reader.readLine());
        String[] v = reader.readLine().split(" ");
        for (int i = 0; i < n; ++i) {
            int variable = Integer.parseInt(v[i]);
            if (variable < 0) {
                variable = -variable;
                variables[(variable - 1) / familiesNumber + 1][(variable - 1) % familiesNumber + 1] = 0;
            } else {
                variables[(variable - 1) / familiesNumber + 1][(variable - 1) % familiesNumber + 1] = 1;
            }
        }
    }

    @Override
    public void writeAnswer() throws IOException {
        // write the answer to the current problem (outFilename)
        FileWriter fileWriter = new FileWriter(outFilename);

        if (!hasSolution) {
            fileWriter.write("False\n");
            fileWriter.close();
            return;
        }

        fileWriter.write("True\n");
        for (int i = 1; i <= cliqueSize; ++i) {
            for (int j = 1; j <= familiesNumber; ++j) {
                if (variables[i][j] == 1) {
                    fileWriter.write(j + " ");
                    break;
                }
            }
        }

        fileWriter.close();
    }

    public boolean hasSolution() {
        return hasSolution;
    }
}
