# Double Hashing

Double Hashing, as any other type of hashing has a very special
way to deal with collisions. In double hashing, you would use
multiple functions that compose the hash value together in case
there are collisions.

Example:

`h(x) = (1x + 3) mod 11`

this would be your primary hash function, if there are no
collisions there's nothing else you need.

But to handle collisions double hashing uses a secondary
hash function like this one:

`h'(x) = 5 - (x mod 5)`

these functions are then combined using an extra function
that takes the value to be hashed and the number of collisions
that have already happened.

Example:

`h(x,i) = (h(x) + i * h'(x)) mod 11`

So say you want to add the value 5 to the table, the composite
function `h(5,0)` would produce 8.

Now let's say the table at position 8 is already occupied,
in that case we increment i and calculate the new hash value
of `h(5,1) = 2` and try to enter the value at the new table
position.

In case that position is filled as well, we simply increment
i again and start over.
