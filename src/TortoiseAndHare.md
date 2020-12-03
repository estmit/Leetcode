# Floyd's Algorithm (aka Tortoise and Hare)

Applications: detecting cycles in a linked list
linked list is not limited to just node-based structures, e.g.
integer array where elements are of range [1, n] and size of array 
is n + 1 (see https://leetcode.com/problems/find-the-duplicate-number/)

## Gist
* There are two pointers, `slow` (tortoise) and `fast` (hare)
* `fast` moves at 2x speed of `slow`
* If there's a cycle, `slow` will eventually meet `fast` at some point
 within the cycle since they will both we trapped in the cycle

```
                a
                 \ _____slow, fast
                 //     \\
            F   //       \\
   start --------         ))
                \\       //  
                 \\_____//
  
```
* let `F` = length from start of linked list to beginning of cycle
* let length of cycle = `C` and distance from start of cycle to `fast` = `a`
* note that the distance from start of cycle to `slow` is also `a`
* since `fast` moved at 2x speed of `slow`:
```
distance_traveled_by_fast = 2 * distance_traveled_by_slow
               F + nC + a = 2 * (F + a) // fast looped around C n times before
                                        // being caught up by slow
               F + nC + a = 2F + 2a
                       nC = F + a
```
* `slow` is reset to the start of the linked list and will now travel at the same speed 
(both 1 node at a time) as `fast` (recall `fast` is at `F + a`)
* we want `slow` to only travel F steps to determine where the cycle begins
* since `fast` and `slow` are moving at the same speed, `fast` will also travel only F steps
starting from `F + a`:
```
F + a + F = (F + a) + F
          = nC + F // substitution using result from above code block
```
* if we want `slow` to meet `fast` at precisely the start of the cycle, meaning `F = nC + F`, 
then `nC` must be equal to 0
* this means `fast` will travel exactly `F` steps to "complete" the cycle, and `slow` will meet 
`fast` at precisely `F` steps from the start of the linked list which is also the start of the cycle

```java
class ListUtils {
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = slow.next.next;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
```