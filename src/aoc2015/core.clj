(ns aoc2015.core
  (:require [clojure.test :as t]
            [clojure.java.io :as io]))

(defn move [instructions]
  (- (count (filter #{ \( } instructions))
     (count (filter #{ \) } instructions))))

(defn
  first-subzero
  "returns the "
  ([is] (first-subzero 1 0 is))
  ([n f is]
   (let [[i & is] is
         move-fn (case i, \( inc, \) dec, identity)
         new-f (move-fn f)]
     (cond
       (neg? new-f) n
       (not (seq is)) 0
       :else (recur (inc n) new-f is)))))

(t/deftest first-subzero-test
(t/testing "returns first location when going subzero"
           (t/is (= 3 (first-subzero "())"))))
  (t/testing "0 if not going underground"
    (t/is (= 0 (first-subzero 0 0 "(((())))")))))

(t/deftest floors-test)

(t/deftest 
 parantheses-test
 (t/testing "()))"
            (t/is (= -2 (move "()))"))))
 (t/testing "(( => +2"
   (t/is (= 1 (move [\(]))))
 (t/testing "( => +1"
            (t/is (= 1 (move [\(]))))
 (t/testing ")) => -2"
   (t/is (= 1 (move [\(]))))
(t/testing ") => -1"
  (t/is (= 1 (move [\(]))))
 (t/testing "1 != 2"
            (t/is (not (= 1 2)))))

(comment 
  (with-open 
    [f (-> "parantheses.txt" io/resource io/reader)]
    (first-subzero (slurp f))
    #_(move (slurp f))))

(comment 
  (let [[f & r] "abc"]
    [f r]))

(t/run-tests)

