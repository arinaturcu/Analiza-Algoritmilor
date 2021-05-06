CC=g++
CFLAGS=-g

build: coloring

coloring:
	$(CC) $(FLAGS) coloring.cpp coloring_greedy.cpp coloring_backtracking.cpp -o coloring

run_tests:
	./test.sh

clean:
	rm -r coloring ./out_greedy/* ./out_backtracking/*
