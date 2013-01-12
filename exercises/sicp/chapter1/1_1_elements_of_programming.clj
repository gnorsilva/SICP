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
    (* 3 (- 6 2)(- 2 7))
    ) => (/ (- (/ 74 5)) 60))


(fact "1.3 define a procedure that takes three numbers and returns the sum of the squares of the two largest numbers"

  (defn square [n] (* n n))             *

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

  (eval-test 0 p) => 0

; For applicative-order evaluation the arguments are evaluated before
; being passed to a function. Since (p) returns itself it would never evaluate

; for normal-order evaluation p is never evaluated and so the program can
; evaluate the correct resut
)