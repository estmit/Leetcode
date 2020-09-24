# Comparator vs Comparable

* Comparator is a `@FunctionalInterface` vs Comparable is a class interface
* A relation operator (`re`) totally / linearly orders a set S if all 4 of the following hold true:
    * Reflexivity -- a `re` a for all a in S
    * Antisymmetry -- a `re` b and b `re` a implies a = b
    * Transitivity -- a `re` b and b `re` c implies a `re` c
    * Comparability -- for any a, b in C, either a `re` b or b `re` a; aka every pair of elements in
     a set can be compared
* Natural ordering
    * "default" ordering
    * implementing the Comparable interface == defining the natural order
* Natural ordering is consistent with equals IFF `e1.compareTo(e2) == 0` has the same boolean value 
as `e1.equals(e2)` for all e1 and e2 (provided that e1 and e2 != null)
* Natural ordering consistent with equals == total ordering
* Java docs recommend that natural orderings should be consistent with equals:
    * S.add(e) adds e to the set S IFF there isn't an e2 in S where `e.equals(e2)`
    * a sorted set in contrast uses `compareTo` for element comparison; if `e1.compareTo(e2) == 0`, 
    it is assumed that `e1.equals(e2)`
    * if `!e1.equals(e2)` and `e1.compareTo(e2) == 0`, and e1 is added before e2, then e2 will not 
    get added to the set since they are considered"equal" according to `compareTo`
    * on the other hand, if `e1.equals(e2)` and `e1.comparesTo(e2) != 0`, and e1 is added before e2,
     then e2 will be added to the set since they are considered "equal" according to `compareTo`
    
* `compareTo` should return for all x and y:
    * -1 if x < y
    * 0 if x == y
    * 1 if x > y