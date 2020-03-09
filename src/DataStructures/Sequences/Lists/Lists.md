# Dynamic Lists
Dynamic lists are a sequence of single list elements that are linked together.
The list can be doubly linked or singly linked.

Elements in a doubly linked list have pointers to their predecessor and successor.
In a singly linked list only the successor is saved.

## Implementation
The list class contains an empty element called the header which assists in navigating the list.
Elements are classes containing: a single value to store and pointer(s) to their successor (and predecessor).

### Adding an element to the list
When appending the element e, the current last element f of the list has its next-pointer set to e.
As a result, e is the last element of the list sequence and its next-pointer points to the header (creating a cycle).
e then has its prev-pointer set to f.

When prepending the element e, the current first element f (which is the successor of the header) has its prev-pointer set to e.
The header has its next-pointer set to e.\
`e.next = f`\
`e.prev = h`

### Removing an element in the list
When removing the element e, the pointers are simply removed. 
If e.next = f and e.prev = d, then the following steps occur:\
`f.next = d`\
`d.prev = e`\
As a result, e cannot be accessed since no pointers pointing to e exist.
e has therefore been successfully removed.

