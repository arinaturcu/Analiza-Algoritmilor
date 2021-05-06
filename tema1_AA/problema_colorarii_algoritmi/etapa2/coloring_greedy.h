//
// Created by arina on 12/1/20.
//

#ifndef UNTITLED_COLORING_GREEDY_H
#define UNTITLED_COLORING_GREEDY_H
#include <bits/stdc++.h>
#define NUM_MAX 101
using namespace std;

int smallest_available_color(int graph[NUM_MAX][NUM_MAX], int v, int colors[], int nodes_number);

int find_chromatic_number_greedy(int graph[NUM_MAX][NUM_MAX], int nodes_number, int colors[]);

#endif //UNTITLED_COLORING_GREEDY_H
