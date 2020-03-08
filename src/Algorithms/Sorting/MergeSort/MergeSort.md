# MergeSort
Mergesort is an efficient, stable and comparison-based algorithm.
Like QuickSort, MergeSort is based on the divide and conquer principle.

Most MergeSort implementations are recursive and seperate the input
sequence with later merge-back of the sub-sequences.

## The Algorithm
1. Divide the unsorted sequence into 2 sub-sequences
2. Repeat 1 until every sub-sequence contains only one element
3. Merge the sequences into bigger ones while sorting them
4. Repeat 3 until you have arrived at the inital length of the sequence

## Complexity
| Best | Average | Worst | Stability |
|----|----|----|----|
| `Ω(n log(n))` | `Θ(n log(n))` | `O(n log(n))` | yes |