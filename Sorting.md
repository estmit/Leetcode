# Sorting

Takeaway: 

Since Java 7+, Objects are sorted using TimSort and primitives are sorted using QuickSort.

| Method | Input | Algorithm | Notes |
|---|---|---| --- |
| Collections#sort | Primitives | Dual-Pivot Quicksort (unstable) | Collections#sort delegates to Arrays#sort |
| Collections#sort | Objects    | TimSort (stable)                | Collections#sort delegates to Arrays#sort |
| Arrays#sort      | Primitives | Dual-Pivot Quicksort (unstable) ||
| Arrays#sort      | Objects    | TimSort (stable)                ||

TimSort is a MergeSort and InsertionSort hybrid sorting algorithm
> It is a stable, adaptive, iterative mergesort that requires far fewer than n log(n) comparisons when running on
> partially sorted arrays, while offering performance comparable to a traditional mergesort when run on random arrays.
> Like all proper mergesorts timsort is stable and runs in O(n log n) time (worst case). In the worst case, timsort
> requires temporary storage space for n/2 object references; in the best case, it requires only a small constant amount
> of space. Contrast this with the current implementation, which always requires extra space for n object references,
> and beats n log n only on nearly sorted lists.

https://stackoverflow.com/questions/15154158/why-collections-sort-uses-merge-sort-instead-of-quicksort
https://stackoverflow.com/a/32334442
https://svn.python.org/projects/python/trunk/Objects/listsort.txt