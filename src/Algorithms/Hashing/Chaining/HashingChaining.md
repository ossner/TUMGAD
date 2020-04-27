# Hashing with Chaining

Chaining is a hashing technique that deals with collisions
very intuitively. The hashtable in this case is an array of
Lists and when inserting a value in the table it is
simply added to the list at the calculated index.

Example: `h(x) = (3x + 9) mod 7`

So say you want to insert the value 5 into an empty hashtable
after insertion the table looks like this (`h(5) = 3`):

| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 
|----|----|----|----|----|----|----|
| | | | 5 | | | |

All lists except for the one at index 3 are empty.

After this, you want to insert 12 into the same table.
Sine `h(12) = 3` the key maps to the same value as 5.
Since our hashtable consists of lists and we simply append
the new key, after the insertion the table looks like this:


| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 
|----|----|----|----|----|----|----|
| | | | 5 | | | |
| | | | 12 | | | |

So as a general rule: If there are collisions, simply append
the new value after the old one.
