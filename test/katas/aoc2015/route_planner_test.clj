(ns aoc2015.route-planner-test
  (:require [clojure.test :refer [testing deftest is]]
            [aoc2015.route-planner :refer [directions-to-visited santas-helper]]
            [clojure.java.io :as io]))

(deftest directions-to-visisted-test
  (testing ">^v< moves as expected"
    (is (= #{'(0 0) '(0 1)} (directions-to-visited ">")))
    (is (= #{'(0 0) '(-1 0)} (directions-to-visited "v")))
    (is (= #{'(0 0) '(1 0)} (directions-to-visited "^")))
    (is (= #{'(0 0) '(0 -1)} (directions-to-visited "<"))))
  (testing "no directions returns starting coordinates"
    (is (= #{'(0 0)} (directions-to-visited "")))))

(comment
  (with-open [r (-> "directions.txt" io/resource io/reader)]
    (count (santas-helper (slurp r))))
  (with-open [r (-> "directions.txt" io/resource io/reader)]
    (count (directions-to-visited (slurp r)))))