(* Kaitlin Gu
kg1292@nyu.edu
Programming Languages - HW 3, Cory Plock *)

(* 1. Implement a function listsum : int list -> int -> bool: given an input integer list and an
integer n, compute the sum of the numbers in the input list and evaluate to true if the sum is n,
or false otherwise. *)
fun listsum [] n = (n = 0)
  | listsum (x::xs) n = listsum xs (n-x);

(* 2. Implement a function isten : int list -> bool which, given a list, determines if the sum equals
10. Write the function in terms of the function listsum above using partial application. See the
lecture slides for ideas. *)
fun isten xs = listsum xs 10;

(* 3. Implement length : ’a list -> int using the Standard Basis Library function foldl. Do not
use recursion. *)
fun length xs =
  foldl (fn (x,acc) => 1 + acc) 0 xs;

(* 4. Implement reverse : ’a list -> ’a list using the Standard Basis Library functions foldl or
foldr. Do not use recursion. *)
fun reverse xs =
  foldl (fn(x,acc) => x::acc) [] xs;

(* 5. Write a function zip: ’a list * b list -> (’a * ’b) list that takes a pair of lists (of equal
length) and returns the equivalent list of pairs. Raise the exception Mismatch if the lengths don’t
match. *)
exception Mismatch;
fun zip ([],[]) = []
  | zip (x::xs, y::ys) = (x, y)::zip (xs, ys)
  | zip ([], ys) = raise Mismatch
  | zip (xs, []) = raise Mismatch;

(* 6. Write a function unzip : (’a * ’b) list -> ’a list * ’b list that turns a list of pairs (such
as generated with zip above) into a pair of lists. *)
fun unzip xs =
  foldr (fn ((a,b), (alist, blist)) => (a::alist, b::blist)) ([],[]) xs;

(* A list of tests that I used for the assignment:
listsum [1,2,3] 6;
listsum [1,2,3] 5;
listsum [] 0;
isten ([5,5]);
isten ([5,4]);
isten ([]);
length ([1,2,3]);
length ([1,2,4,5]);
reverse([1,2,3]);
reverse([3,6,4]);
zip([1,2,3], [4,5,6]);
unzip([(1,4), (2,5), (3,6)]);
zip([2,4,5], [1,2]);
zip ([1,2], [3,4,5]); *)
