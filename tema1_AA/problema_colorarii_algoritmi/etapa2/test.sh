#!/bin/bash
# shellcheck disable=SC2027
calc(){ awk "BEGIN { print "$*" }"; }

echo "Running tests...";
echo ""
echo "============== GRAPH COLORING USING BACKTRACKING =============="
echo ""
time=$({ time ./coloring backtracking < ./in/test1 > ./out_backtracking/test1; } 2>&1| grep real | awk '{ print $2 }')
space=$( { valgrind ./coloring backtracking < ./in/test1 > ./out_backtracking/test1; } 2>&1 | grep allocated | awk '{ print $9 " " $10 " " $11 }')
if [[ $(diff ./out_backtracking/test1 ref/test1.ref ) ]]; then
    echo "Test 1.........................................FAILED"
else
    echo "Test 1.........................................PASSED"
fi
echo "time: $time"
echo "space: $space"

echo ""
time=$({ time ./coloring backtracking < ./in/test2 > ./out_backtracking/test2; } 2>&1| grep real | awk '{ print $2 }')
space=$( { valgrind ./coloring backtracking < ./in/test2 > ./out_backtracking/test2; } 2>&1 | grep allocated | awk '{ print $9 " " $10 " " $11 }')
if [[ $(diff ./out_backtracking/test2 ref/test2.ref ) ]]; then
    echo "Test 2.........................................FAILED"
else
    echo "Test 2.........................................PASSED"
fi
echo "time: $time"
echo "space: $space"

echo ""
time=$({ time ./coloring backtracking < ./in/test3 > ./out_backtracking/test3; } 2>&1| grep real | awk '{ print $2 }')
space=$( { valgrind ./coloring backtracking < ./in/test3 > ./out_backtracking/test3; } 2>&1 | grep allocated | awk '{ print $9 " " $10 " " $11 }')
if [[ $(diff ./out_backtracking/test3 ref/test3.ref ) ]]; then
    echo "Test 3.........................................FAILED"
else
    echo "Test 3.........................................PASSED"
fi
echo "time: $time"
echo "space: $space"

echo ""
time=$({ time ./coloring backtracking < ./in/test5 > ./out_backtracking/test5; } 2>&1| grep real | awk '{ print $2 }')
space=$( { valgrind ./coloring backtracking < ./in/test5 > ./out_backtracking/test5; } 2>&1 | grep allocated | awk '{ print $9 " " $10 " " $11 }')
if [[ $(diff ./out_backtracking/test5 ref/test5.ref ) ]]; then
    echo "Test 5.........................................FAILED"
else
    echo "Test 5.........................................PASSED"
fi
echo "time: $time"
echo "space: $space"

echo ""
time=$({ time ./coloring backtracking < ./in/test6 > ./out_backtracking/test6; } 2>&1| grep real | awk '{ print $2 }')
space=$( { valgrind ./coloring backtracking < ./in/test6 > ./out_backtracking/test6; } 2>&1 | grep allocated | awk '{ print $9 " " $10 " " $11 }')
if [[ $(diff ./out_backtracking/test6 ref/test6.ref ) ]]; then
    echo "Test 6.........................................FAILED"
else
    echo "Test 6.........................................PASSED"
fi
echo "time: $time"
echo "space: $space"

echo ""
time=$({ time ./coloring backtracking < ./in/test7 > ./out_backtracking/test7; } 2>&1| grep real | awk '{ print $2 }')
space=$( { valgrind ./coloring backtracking < ./in/test7 > ./out_backtracking/test7; } 2>&1 | grep allocated | awk '{ print $9 " " $10 " " $11 }')
if [[ $(diff ./out_backtracking/test7 ref/test7.ref ) ]]; then
    echo "Test 7.........................................FAILED"
else
    echo "Test 7.........................................PASSED"
fi
echo "time: $time"
echo "space: $space"

echo ""
time=$({ time ./coloring backtracking < ./in/test8 > ./out_backtracking/test8; } 2>&1| grep real | awk '{ print $2 }')
space=$( { valgrind ./coloring backtracking < ./in/test8 > ./out_backtracking/test8; } 2>&1 | grep allocated | awk '{ print $9 " " $10 " " $11 }')
if [[ $(diff ./out_backtracking/test8 ref/test8.ref ) ]]; then
    echo "Test 8.........................................FAILED"
else
    echo "Test 8.........................................PASSED"
fi
echo "time: $time"
echo "space: $space"

echo ""
echo ""
echo "============ GRAPH COLORING USING GREEDY ALGORITHM ============"

echo ""
time=$({ time ./coloring greedy < ./in/test1 > ./out_greedy/test1; } 2>&1| grep real | awk '{ print $2 }')
space=$( { valgrind ./coloring greedy < ./in/test1 > ./out_greedy/test1; } 2>&1 | grep allocated | awk '{ print $9 " " $10 " " $11 }')
out=$(cat out_greedy/test1)
ref=$(cat ref/test1.ref)
dif=$((out-ref))
rel_error=$(calc $dif/$ref)
if [[ $(diff ./out_greedy/test1 ref/test1.ref ) ]]; then
    echo "Test 1.....................RELATIVE ERROR $(echo "$rel_error*100" | bc)% (out: $out vs. ref: $ref)"
else
    echo "Test 1.....................PASSED"
fi
echo "time: $time"
echo "space: $space"

echo ""
time=$({ time ./coloring greedy < ./in/test2 > ./out_greedy/test2; } 2>&1| grep real | awk '{ print $2 }')
space=$( { valgrind ./coloring greedy < ./in/test2 > ./out_greedy/test2; } 2>&1 | grep allocated | awk '{ print $9 " " $10 " " $11 }')
out=$(cat out_greedy/test2)
ref=$(cat ref/test2.ref)
dif=$((out-ref))
rel_error=$(calc $dif/$ref)
if [[ $(diff ./out_greedy/test2 ref/test2.ref ) ]]; then
    echo "Test 2.....................RELATIVE ERROR $(echo "$rel_error*100" | bc)% (out: $out vs. ref: $ref)"
else
    echo "Test 2.....................PASSED"
fi
echo "time: $time"
echo "space: $space"

echo ""
time=$({ time ./coloring greedy< ./in/test3 > ./out_greedy/test3; } 2>&1| grep real | awk '{ print $2 }')
space=$( { valgrind ./coloring greedy < ./in/test3 > ./out_greedy/test3; } 2>&1 | grep allocated | awk '{ print $9 " " $10 " " $11 }')
out=$(cat out_greedy/test3)
ref=$(cat ref/test3.ref)
dif=$((out-ref))
rel_error=$(calc $dif/$ref)
if [[ $(diff ./out_greedy/test3 ref/test3.ref ) ]]; then
    echo "Test 3.....................RELATIVE ERROR $(echo "$rel_error*100" | bc)% (out: $out vs. ref: $ref)"
else
    echo "Test 3.....................PASSED"
fi
echo "time: $time"
echo "space: $space"

echo ""
time=$({ time ./coloring greedy < ./in/test5 > ./out_greedy/test5; } 2>&1| grep real | awk '{ print $2 }')
space=$( { valgrind ./coloring greedy < ./in/test5 > ./out_greedy/test5; } 2>&1 | grep allocated | awk '{ print $9 " " $10 " " $11 }')
out=$(cat out_greedy/test5)
ref=$(cat ref/test5.ref)
dif=$((out-ref))
rel_error=$(calc $dif/$ref)
if [[ $(diff ./out_greedy/test5 ref/test5.ref ) ]]; then
    echo "Test 5.....................RELATIVE ERROR $(echo "$rel_error*100" | bc)% (out: $out vs. ref: $ref)"
else
    echo "Test 5.....................PASSED"
fi
echo "time: $time"
echo "space: $space"

echo ""
time=$({ time ./coloring greedy < ./in/test6 > ./out_greedy/test6; } 2>&1| grep real | awk '{ print $2 }')
space=$( { valgrind ./coloring greedy < ./in/test6 > ./out_greedy/test6; } 2>&1 | grep allocated | awk '{ print $9 " " $10 " " $11 }')
out=$(cat out_greedy/test6)
ref=$(cat ref/test6.ref)
dif=$((out-ref))
rel_error=$(calc $dif/$ref)
if [[ $(diff ./out_greedy/test6 ref/test6.ref ) ]]; then
    echo "Test 6.....................RELATIVE ERROR $(echo "$rel_error*100" | bc)% (out: $out vs. ref: $ref)"
else
    echo "Test 6.....................PASSED"
fi
echo "time: $time"
echo "space: $space"

echo ""
time=$({ time ./coloring greedy < ./in/test7 > ./out_greedy/test7; } 2>&1| grep real | awk '{ print $2 }')
space=$( { valgrind ./coloring greedy < ./in/test7 > ./out_greedy/test7; } 2>&1 | grep allocated | awk '{ print $9 " " $10 " " $11 }')
out=$(cat out_greedy/test7)
ref=$(cat ref/test7.ref)
dif=$((out-ref))
rel_error=$(calc $dif/$ref)
if [[ $(diff ./out_greedy/test7 ref/test7.ref ) ]]; then
    echo "Test 7.....................RELATIVE ERROR $(echo "$rel_error*100" | bc)% (out: $out vs. ref: $ref)"
else
    echo "Test 7.....................PASSED"
fi
echo "time: $time"
echo "space: $space"

echo ""
time=$({ time ./coloring greedy < ./in/test8 > ./out_greedy/test8; } 2>&1| grep real | awk '{ print $2 }')
space=$( { valgrind ./coloring greedy < ./in/test8 > ./out_greedy/test8; } 2>&1 | grep allocated | awk '{ print $9 " " $10 " " $11 }')
out=$(cat out_greedy/test8)
ref=$(cat ref/test8.ref)
dif=$((out-ref))
rel_error=$(calc $dif/$ref)
if [[ $(diff ./out_greedy/test8 ref/test8.ref ) ]]; then
    echo "Test 8.....................RELATIVE ERROR $(echo "$rel_error*100" | bc)% (out: $out vs. ref: $ref)"
else
    echo "Test 8.....................PASSED"
fi
echo "time: $time"
echo "space: $space"
