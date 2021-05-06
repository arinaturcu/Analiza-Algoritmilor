cmake_minimum_required(VERSION 3.17)
project(untitled)

set(CMAKE_CXX_STANDARD 14)

add_executable(untitled coloring_greedy.cpp coloring.cpp coloring_greedy.h coloring_backtracking.cpp coloring_backtracking.h)