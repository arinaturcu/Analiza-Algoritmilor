#include <iostream>
#include "coloring_greedy.h"

int smallest_available_color(int graph[NUM_MAX][NUM_MAX], int v, int colors[NUM_MAX], int nodes_number) {
    int color = 1;

    while (true) {
        int safe = 1;
        for (int i = 1; i < v; ++i) {
            if (graph[v][i] == 1 && colors[i] == color) {
                safe = 0;
                break;
            }
        }

        if (safe) {
            colors[v] = color;
            break;
        } else {
            color++;
        }
    }

    return color;
}

int find_chromatic_number_greedy(int graph[NUM_MAX][NUM_MAX], int nodes_number, int colors[]) {
    int max_colors = 0;
    colors[1] = 1;

    for (int v = 2; v <= nodes_number; ++v) {
        colors[v] = smallest_available_color(graph, v, colors, nodes_number);
        max_colors = max(max_colors, colors[v]);
    }

    return max_colors;
}
