; Kaitlin Gu
; kg1292@nyu.edu
; Programming Languages - HW 2 Q5, Cory Plock


; 5a.) foldr: foldr (“fold right”) accepts the following parameters: a binary function f, a seed value s and a list L = l1, . . . , ln
(define (foldr f s L)
  (if (null? L) s ;if the list is empty foldr evaluates to s
   (f (car L) (foldr f s (cdr L))))) ; evaluates f(ln,s), f(ln-1,s) ... f(0,s)


; 5b.) paramreverse: takes a function F and a list of arguments AL and calls function F, passing the arguments to AL in reverse order.
(define (reverselist L) ; helper function to reverse  list
  (if (null? L) '() 
  (append (reverselist(cdr L)) (list(car L)))))

(define (paramreverse F AL)
  (if (null? AL) '() 
  (apply f (reverselist AL)))) ; apply function on reverse list


; 5c.) highest: takes a list of integers and an integer k > 0 as arguments and returns a new list containing the k highest numbers in the original list.
(define (remove ele L)
  (cond
    ((null? L) '())
    ((= ele (car L)) (cdr L)) ; found a match!
    (else (cons (car L) (remove ele (cdr L))))))


(define (max_list l) ; Getting the max in a list: taken from recitation
  (let ((single? (lambda (x) (null? (cdr x)))))
    (if (single? l) (car l) (max (car l) (max_list (cdr l))))))

(define (highest L k)
  (if (<= k 0) '() ; stopping condition
  (cons (max_list L) (highest (remove (max_list L) L) (- k 1))))) ; get the maximum in a list and then remove it 

; 5d.) mapfun: takes a list of functions FL and a list L and applies each element of FL to the corresponding element of L. For instance,
(define (mapfun FL L)
  (cond
    ((null? L) '()) ; list L is null, return empty list, handling different sized lists
    ((null? FL) '()) ; list FL is null, return empty list
    (else (cons ((car FL)(car L)) ; applies each element of FL to corresponding element of L
          (mapfun (cdr FL)(cdr L)))))) ; rest of lists



; 5e.) filter: outputs a list such that an item i in L will appear in the output list if pred(i) is true.
(define (filter pred L)
  (cond
    ((null? L) '())
    ((pred (car L)) (cons (car L) (filter pred (cdr L)))) ; construct a list where all the elements pass predicate
    (else (filter pred (cdr L)))))


; Test cases from hw:
(foldr + 0 '(1 2 3)) ; should return 6
(paramreverse - '(2 1)) ; should return -1
(highest '(1 7 4 5 3) 2) ; should return a combination of (5 7)
(mapfun (list cadr car cdr) '((A B) (C D) (E F))) ; should return (b c (f))
(mapfun (list cadr car) '((A B) (C D) (E F))) ; should return (b c)
(mapfun (list cadr car cdr) '((A B) (C D))) ; should return (b c)
(filter even? '(1 2 3 4))  ;should return (2 4)
