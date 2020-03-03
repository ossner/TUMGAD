# Array sequence
An array sequence is basically an ArrayList: 
an object consisting of an array and methods to modify the array.

The space in this array is dynamic
---
## Dynamic fields
The array is not restricted to a set amount of space.
Once a certain threshold is reached, a new array is copied with an increased/decreased size and the old values are 
allocated into the new array.

Given:

`β = growth factor`
`α = max. storage overhead`
`w = current array size`
`n = current nr. of elements in array`
`b = array (new Element[w])`

### 1. push(Element e)
If the array is full `(n == w)`, a new array is created with the increased size `β*w` and all the values from `b`
are then copied to the new array. Finally, the element e is added to the next free position in the array `(n+1)`.

Else if the array is not full, the element e is simply pushed to the array at position n+1.

- Example:
`β = 2` //the array size is doubled
`w = 4`
`n = 4`

| 0  | 1  | 2  | 3  |
|----|----|----|----|
| 15 | 18 | 20 | 45 |

__push element 14:__

| 0  | 1  | 2  | 3  | 4  | 5 | 6 | 7 |
|----|----|----|----|----|---|---|---|
| 15 | 18 | 20 | 45 | 14 |   |   |   |

### 2. pop():
If the array size after the removal of the last element results in `n <= w/α`, then a smaller array with the size
`w/β` is created and the values are copied in to the new array (excluding the last element that was popped).

Else if the array size is too small, the last element is simply removed.

- Example:
`β = 2` //the array size is doubled/halved\
`α = 3` //if n is a third of the current size of w, then the array is shortened\
`w = 12`
`n = 5` 

| 0  | 1  | 2  | 3  | 4  | 5 | 6 | 7 | 8 | 9 | 10 | 11 |
|----|----|----|----|----|---|---|---|---|---|----|----|
| 15 | 18 | 20 | 45 | 14 |   |   |   |   |   |    |    |

___pop():___

| 0  | 1  | 2  | 3  | 4 | 5 |
|----|----|----|----|---|---|
| 15 | 18 | 20 | 45 |   |   |