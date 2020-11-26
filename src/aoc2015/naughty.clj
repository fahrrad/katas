(ns naughty
  (:require [clojure.string :as s]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest testing is run-tests]]))

(defn contains-3-vowels? [s]
  (>= 
   (count (filter #{\a \e \i \o \u} (s/lower-case s)))
   3))

(defn contains-same-letter-twice-in-a-row? [s]
  (map (juxt count identity) (partition-by identity s)))

(defn does-contains-special-str? [s]
  (or
   (.contains s "ab")
   (.contains s "cd")
   (.contains s "pq")
   (.contains s "xy")))

(defn double-pairs [s]
  (letfn [(double-pair?
            [s]
            (seq (filter (partial <= 2) (vals (frequencies (partition 2 2 s))))))]
    (or (double-pair? s)
        (double-pair? (drop 1 s)))))

(defn double-pairs? [s]
  (let [partitioned (partition-by identity s)
        partioned-counted (map count partitioned)]
    (prn "double pair? ")
    (or (<= 2 (count (filter (partial <= 2)  partioned-counted )))
        (seq (filter (partial <= 4)  partioned-counted)))))

(def good?
  (every-pred
   contains-3-vowels?
   contains-same-letter-twice-in-a-row?
   (complement does-contains-special-str?)))

(def good-2?
  (every-pred
   double-pairs?
   (partial re-find #"(\w)\w\1")))

(defn debug-good-2 [s]
  (prn "double pair? " (double-pairs? s))
  (prn "found xyx? "  (re-find #"(\w)\w\1" s)))

(comment 
  (double-pairs? "abaaaa")
  (good-2? "xxyxx")
  ((partial re-find #"(\w)\w\1") "qjhvhtzxzqqjkmpb")
  (double-pairs "xxyxx")
  (good-2? "qjhvhtzxzqqjkmpb")
  (with-open [r (-> "naughty.txt" io/resource io/reader)]
    (count (filter good-2? (line-seq r))))
  (contains-same-letter-twice-in-a-row? "abcc")
  (does-contains-special-str? "ugknbfddgicrmopn"))

(deftest good-2?-test
  (testing (is (good-2? "qjhvhtzxzqqjkmpb")) (debug-good-2 "qjhvhtzxzqqjkmpb")))

(run-tests)