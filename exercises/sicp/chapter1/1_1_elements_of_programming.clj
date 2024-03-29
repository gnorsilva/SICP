(ns sicp.chapter1.1-1-elements-of-programming
  (:use midje.sweet))

(fact "1.1 - evaluate the following expressions"
  (+ 5 3 4) => 12
  (- 9 1) => 8
  (/ 6 2) => 3
  (+ (* 2 4) (- 4 6)) => 6

  (def a 3)
  (def b (+ a 1))

  (+ a b (* a b)) => 19
  (= a b) => false

  (if (and (> b a) (< b (* a b)))
    b
    a) => b

  (cond
    (= a 4) 6
    (= b 4) (+ 6 7 a)
    :else 25) => 16

  (+ 2 (if (> b a) b a)) => 6

  (* (cond
       (> a b) a
       (< a b) b
       :else -1)
    (+ a 1)) => 16)


(fact "1.2 - Translate the following expression into prefix form
        5 + 4 + (2 - (3 - (6 + 4/5))) / 3(6 - 2)(2 - 7)"
  (/
    (+ 5 4 (- 2 (- 3 (+ 6 (/ 4 5)))))
    (* 3 (- 6 2) (- 2 7))
    ) => (/ (- (/ 74 5)) 60))


(fact "1.3 - define a procedure that takes three numbers and returns the sum of the squares of the two largest numbers"

  (defn square [n] (* n n))

  (defn sum-of-squares [x y] (+ (square x) (square y)))

  (defn squares-sum-of-largest-numbers [a b c]
    (cond
      (and (< a b) (< a c)) (sum-of-squares b c)
      (and (< c a) (< c b)) (sum-of-squares a b)
      :else (sum-of-squares a c)))

  (squares-sum-of-largest-numbers 1 2 3) => 13
  (squares-sum-of-largest-numbers 5 7 4) => 74
  (squares-sum-of-largest-numbers 4 2 3) => 25)


(fact "1.4 - describe the behaviour of the following procedure"
  (defn a-plus-abs-b [a b]
    ((if (> b 0) + -) a b))
  ; the primitive procedure (- or +) to use is determined at runtime
  ; based on whether b is > 0 or not

  (a-plus-abs-b 3 -2) => 5)


(fact "1.5 - determining whether the interpreter is using applicate or normal order evaluation"
  (defn p [] p)
  (defn eval-test [x y]
    (if (= x 0) 0 y))

  ; For applicative-order evaluation the arguments are evaluated before
  ; being passed to a function. Since (p) returns itself it would never evaluate

  ; for normal-order evaluation p is never evaluated and so the program can
  ; evaluate the correct result

  (eval-test 0 p) => 0)



; *** Difference between function (math) and procedure (CS) is describing properties of things
; and describing how to do things, i.e. declarative vs imperative knowledge ***



(fact "1.6 - What happens when Allysa P. Hacker tries to use a new-if to compute square roots?"

  (defn average [x y]
    (/ (+ x y) 2))

  (defn improve [guess x]
    (double (average guess (/ x guess))))
  ; casting these to double to loose clojure's inbuilt rational number fractions

  (defn abs [x]
    (if (< x 0) (- x) x))

  (defn good-enough? [guess x]
    (< (abs (- (square guess) x)) 0.0001))

  (defn sqrt-iter [guess x]
    (if (good-enough? guess x)
      guess
      (sqrt-iter (improve guess x) x)))

  (defn sqrt [x]
    (sqrt-iter 1 x))

  (sqrt 2) => 1.4142156862745097
  (sqrt 4) => 2.0000000929222947


  (defn new-if [predicate then-clause else-clause]
    (cond predicate then-clause
      :else else-clause))

  ; The new-if is of applicative-order evaluation and so the procedure never terminates
  ; as both clauses will be evaluated before the 'predicate' test!
  )

(fact "1.7"
  (fact "explain why the above 'good-enough?' function is not effective for small and large numbers"

    (sqrt 0.000000001) => 0.007812542664015912) ; WRONG! should have been 3.162278058889937E-5
    ; (sqrt 10000000000000000000000000000000) => STACK OVERFLOW!

    ; For small numbers 0.0001 is not an accurate threshold
    ; For large numbers small precision is lost, so 0.0001 is not usable


  (fact "implement an alternative 'good-enough?' which uses fractions to decide when to stop"

    (defn good-enough? [guess x]
      (< (abs (- 1 (/ (square guess) x))) 0.0001))

    (sqrt 2) => 1.4142156862745097
    (sqrt 4) => 2.0000000929222947
    (sqrt 0.000000001) => 3.162278058889937E-5
    (sqrt 10000000000000000000000000000000) => 3.162348943003884E15))

(fact "1.8 - Implement Newton's method for cube roots analogously to the square root procedure"
  ; approximation: (x / y² + 2y) / 3

  (defn cube [x] (* x x x));

  (defn good-enough? [guess x]
    (< (abs (- (cube guess) x)) 0.0001))

  (defn improve [guess x]
    (double (/ (+ (/ x (square guess)) (* 2 guess)) 3)))
  ; casting to double to loose clojure's inbuilt rational number fractions

  (defn cube-iter [guess x]
    (if (good-enough? guess x)
      guess
      (cube-iter (improve guess x) x)))

  (defn cube-root [x]
    (sqrt-iter 1 x))

  (defn _4-decimal-places [x]
    (Double/valueOf (format "%.4f" x)))

  (_4-decimal-places (cube-root 27)) => 3.0
  (_4-decimal-places (cube-root 1000)) => 10.0)
