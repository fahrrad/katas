(ns wrapping-paper-calculator-test
  (:require 
   [wrapping-paper-calculator :refer [surface-box length-ribbon]]
   [clojure.test :refer [deftest testing is]]
   [clojure.java.io :as io]
   [clojure.string :as s]
   [clojure.edn :as edn]))

(def xf-parse-file 
  (comp 
   (map #(s/split % #"x"))
   (map (partial map edn/read-string))))

(def xf-surface
  (comp 
   xf-parse-file
   (map (partial apply surface-box))))

(def xf-length 
  (comp
   xf-parse-file
   (map (partial apply length-ribbon))))

(deftest length-ribbon-test
  (testing "2x3x4 => 10"
    (is (= 34 (length-ribbon 2 3 4))))
  (testing "1x1x0 => 4"
    (is (= 14 (length-ribbon 1 1 10)))))

(deftest surface-box-test
  (testing "2x3x4 => 58 (52+slack)"
    (is (= 58 (surface-box 2 3 4))))
  (testing "processes line"
    (is (= 116 (transduce xf-surface + ["2x3x4" "2x3x4"])))))

(comment
  ;; Length
  (with-open [f (-> "dimensions.txt" io/resource io/reader)]
    (transduce xf-length + (line-seq f)))
  ;; Surface
  (with-open [f (-> "dimensions.txt" io/resource io/reader)]
    (transduce xf-surface + (line-seq f))))