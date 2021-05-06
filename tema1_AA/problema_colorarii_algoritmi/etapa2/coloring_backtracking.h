//
// Created by arina on 12/2/20.
//

#ifndef COLORING_BACKTRACKING_H
#define COLORING_BACKTRACKING_H
#define NUM_MAX 101

bool is_safe(int v, int graph[NUM_MAX][NUM_MAX], const int color[], int c, int nodes_number);

bool coloring_util(int graph[NUM_MAX][NUM_MAX], int k, int color[], int v, int nodes_number);

int find_chromatic_number_backtracking(int graph[NUM_MAX][NUM_MAX], int nodes_number, int color[]);

#endif //COLORING_BACKTRACKING_H
