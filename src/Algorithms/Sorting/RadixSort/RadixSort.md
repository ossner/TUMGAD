# RadixSort

RadixSort is a non-comparative, stable sorting algorithm. Comparisons are substituted by 
placing elements into so called buckets based on their radix (digits).

Due to other algorithms having to execute pair-wise key comparisons, they can **never**
exceed a best case of `O(n log n)`.  Under special circumstances (see below), RadixSort can
exceed this boundary.

## The algorithm
1. Linearly loop over the array
2. Take the digit of the element you want to consider and put the element in the 
designated bucket for that digit // LSD vs. MSD
3. Write back elements from the buckets into the array (in the order of the buckets they are in)
4. Increment/Decrement digit place
5. Repeat from 1. until the maximum number of digits has been reached // e.g. `4` if 
the highest number is `1111`

## LSD vs. MSD
LSD (least significant digit) first focuses on the last digit in the element.
e.g. in `1234` this would be `4`

MSD (most significant digit) is unsurprisingly the opposite of LSD where the first 
digit is considered first, hence 1 in the above example.

LSD radix sort is more common than MSD in most conventional implementations.

## Complexity
RadixSort is a special case when it comes to runtimes, since the time is 
dependent on the number of digits `k` that have to be considered.

| Best | Average | Worst | Stability |
|----|----|----|----|
| `Ω(nk)` | `Θ(nk)` | `O(nk)` | yes |
