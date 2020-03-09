# Queues
A queue follows the FIFO-principle: First-in-first-out.
Similar to a waiting line, the first person in line is the first person to be serviced.
The person last in line has to wait until all people in front of him are finished/gone.

## Implementation
The implementation-details are similar to the implementation of dynamic lists.
However, only the first element can be popped and new elements can only be pushed to the end of the sequence.

### popFront()
| 0  | 1  | 2  | 3  | 4 | 5 |
|----|----|----|----|---|---|
| 15 | 18 | 20 | 45 |   |   |

| 0  | 1  | 2  | 3  | 4 | 5 |
|----|----|----|----|---|---|
|18 | 20 | 45 |   |   |   |

### pushBack(42)
| 0  | 1  | 2  | 3  | 4 | 5 |
|----|----|----|----|---|---|
| 15 | 18 | 20 | 45 |   |   |

| 0  | 1  | 2  | 3  | 4 | 5 |
|----|----|----|----|---|---|
| 15 | 18 | 20 | 45 | 42 |   |