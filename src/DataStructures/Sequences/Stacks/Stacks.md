# Stacks
A stack follows the LIFO-principle: Last-in-first-out.
Similar to a stack of paper, the sheet of paper at the top of the stack is taken first.
When another sheet is added, it is placed on top of the stack.
Therefore, the element which was last added to the stack is the first one to be removed.

## Implementation
The implementation-details are similar to the implementation of dynamic list.
However, only the last element of the sequence can be popped or pushed

### pop()

| 0  | 1  | 2  | 3  | 4 | 5 |
|----|----|----|----|---|---|
| 15 | 18 | 20 | 45 |   |   |

| 0  | 1  | 2  | 3  | 4 | 5 |
|----|----|----|----|---|---|
| 15 | 18 | 20 |   |   |   |

### push(42)

| 0  | 1  | 2  | 3  | 4 | 5 |
|----|----|----|----|---|---|
| 15 | 18 | 20 | 45 |   |   |

| 0  | 1  | 2  | 3  | 4 | 5 |
|----|----|----|----|---|---|
| 15 | 18 | 20 | 45 | 42 |   |