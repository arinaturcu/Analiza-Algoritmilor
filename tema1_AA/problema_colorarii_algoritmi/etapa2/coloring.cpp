//
// Created by arina on 12/1/20.
//
#include <iostream>
#include <bits/stdc++.h>
#include "coloring_greedy.h"
#include "coloring_backtracking.h"

int main(int argc, char *argv[]) {
    int nodesNumber, edgesNumber;
    int source, destination;
    int chromaticNumber;
    int graph[NUM_MAX][NUM_MAX];

    cin >> nodesNumber >> edgesNumber;
    int *colors = new int[nodesNumber + 1];

    for (int i = 1; i <= nodesNumber; ++i) {
        for (int j = 1; j <= nodesNumber; ++j) {
            graph[i][j] = 0;
        }
    }

    for (int i = 0; i < edgesNumber; ++i) {
        cin >> source >> destination;
        graph[source][destination] = 1;
        graph[destination][source] = 1;
    }

    if (strcmp(argv[1], "backtracking") == 0) {
        chromaticNumber = find_chromatic_number_backtracking(graph, nodesNumber, colors);
    } else if (strcmp(argv[1], "greedy") == 0) {
        chromaticNumber = find_chromatic_number_greedy(graph, nodesNumber, colors);
    } else {
        cout << "Error: not a valid algorithm name" << endl;
        exit(1);
    }

    cout << chromaticNumber << endl;

    return 0;
}
