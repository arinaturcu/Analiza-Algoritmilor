//
// Created by arina on 12/1/20.
//
#include "coloring_backtracking.h"

bool is_safe(int v, int graph[NUM_MAX][NUM_MAX], const int color[], int c, int nodes_number) {
    for (int i = 1; i <= nodes_number; ++i) {
        if (graph[v][i] == 1 && c == color[i]) {
            return false;
        }
    }

    return true;
}

bool coloring_util(int graph[NUM_MAX][NUM_MAX], int k, int color[], int v, int nodes_number) {
    if (v == nodes_number + 1) {
        return true;
    }

    // try different colors
    for (int c = 1; c <= k; ++c) {
        if (is_safe(v, graph, color, c, nodes_number)) {
            color[v] = c;

            if (coloring_util(graph, k, color, v + 1, nodes_number)) {
                return true;
            }
            color[v] = 0;
        }
    }

    return false;
}

int find_chromatic_number_backtracking(int graph[NUM_MAX][NUM_MAX], int nodes_number, int color[]) {
    for (int i = 1; i <= nodes_number; ++i) {
        if (coloring_util(graph, i, color, 1, nodes_number)) {
            return i;
        }
    }

    return 0;
}
