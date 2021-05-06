// Copyright 2020
// Author: Matei Simtinică

import java.io.*;
import java.util.Scanner;

/**
 * Task1
 * You have to implement 4 methods:
 * readProblemData         - read the problem input and store it however you see fit
 * formulateOracleQuestion - transform the current problem instance into a SAT instance and write the oracle input
 * decipherOracleAnswer    - transform the SAT answer back to the current problem's answer
 * writeAnswer             - write the current problem's answer
 */
public class Task1 extends Task {
    // define necessary variables and/or data structures
    private int familiesNumber;
    private int relationshipsNumber;
    private int spiesNumber;
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
        spiesNumber = Integer.parseInt(line[2]);

        graph = readAndConfigureGraphMatrix(reader, familiesNumber, relationshipsNumber);
        variables = setVariablesNames(familiesNumber, spiesNumber);
    }

    @Override
    public void formulateOracleQuestion() throws IOException {
        // transform the current problem into a SAT problem and write it (oracleInFilename) in a format
        // understood by the oracle
        FileWriter fileWriter = new FileWriter(oracleInFilename);

        fileWriter.write("p cnf ");
        /* Variables number */
        fileWriter.write(familiesNumber * spiesNumber + " ");
        /* Clauses number ( N + N * (K * (K - 1)/2) + M * K ) */
        fileWriter.write((familiesNumber + familiesNumber * (spiesNumber * (spiesNumber - 1)/2))
                + relationshipsNumber * spiesNumber + "\n");

        /* First set of clauses: a family can't have more than one spy.
         * ~(P_vi & P_vj) <=> ~P_vi | ~P_vj must be true, where P_vi is true
         * only if family v has the spy i
         */
        for (int i = 1; i <= familiesNumber; ++i) {
            for (int j = 1; j <= spiesNumber; ++j) {
                for (int k = j + 1; k <= spiesNumber; ++k) {
                    fileWriter.write(-variables[i][j] + " " + -variables[i][k] + " 0\n");
                }
            }
        }

        /* Second set of clauses: Every family must have at least one spy.
         * P_v1 | P_v2 | ... | P_vk must be true, where k is the total number of
         * spies and v is a family
         */
        for (int i = 1; i <= familiesNumber; ++i) {
            for (int j = 1; j <= spiesNumber; ++j) {
                fileWriter.write(variables[i][j] + " ");
            }
            fileWriter.write("0\n");
        }

        /* Third set of clauses: two families tha are friends can't
         * have the same spy.
         * ~(P_vi & P_u1) <=> ~P_vi | ~P_ui must be true, where families
         * v and u are friends and i is a spy.
         */
        for (int i = 1; i <= familiesNumber; ++i) {
            for (int j = i + 1; j <= familiesNumber; ++j) {
                if(graph[i][j]) {
                    for (int k = 1; k <= spiesNumber; ++k) {
                        fileWriter.write(-variables[i][k] + " " + -variables[j][k] + " 0\n");
                    }
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
        for (int i  = 0; i < n; ++i) {
            int variable = Integer.parseInt(v[i]);
            if (variable < 0) {
                variable = -variable;
                variables[(variable - 1) / spiesNumber + 1][(variable - 1) % spiesNumber + 1] = 0;
            } else {
                variables[(variable - 1) / spiesNumber + 1][(variable - 1) % spiesNumber + 1] = 1;
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

        for (int i = 1; i <= familiesNumber; ++i) {
            for (int j = 1; j <= spiesNumber; ++j) {
                if (variables[i][j] == 1) {
                    fileWriter.write(j + " ");
                    break;
                }
            }
        }

        fileWriter.close();
    }
}
