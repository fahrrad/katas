(ns katas.reflow
  (:require [clojure.test :as t])
  (:require [clojure.string :as s])
  (:gen-class))

(def lines ["Lorem ipsum dolor sit amet, consectetur"
             "adipiscing elit. Donec massa purus pharetra" 
            "malesuada velit at, vehicula euismod velit. Sed" 
            "id lorem vitae est accumsan gravida." 
            "Vivamus quis quam vel erat gravida sagittis. In" ])



(defn stream-words [lines]
  (-> lines
      (as-> a (map #(str % " ") a))
      (as-> a (apply str a))
      (s/split #" ")))

(defn create-line 
  ([words width] (create-line "" words width))
  ([acc words width]
   (cond
     (or (not (seq? words)) 
         (> (+ 1 (count (first words)) (count acc)) width)) [acc words]
     (= 0 (count acc)) (create-line (first words) (rest words) width)
     :else (create-line (str acc " " (first words)) (rest words) width))))

(defn reflow' 
  ([words width]
   (reflow' [] words width))
  ([acc words width]
   (println acc)
   (if (seq words)
     (let [[line rest-words] (create-line words width)] 
       (recur (conj acc [line]) rest-words width))
     acc)))

(defn pad [line width]
 (let [missing-width (- width (count line))]
   (s/replace 
    (first (drop missing-width (iterate #(s/replace-first % " " "--") line)))
    " "
    "-")))

(defn reflow [lines width]
  (-> lines
      stream-words
      (reflow' width)
      (as-> a (map pad a))))

(defn pick-random-word []
  (rand-nth ["id" "lorem" "vitae" "est" "accumsan" "gravida."]))

(comment (pick-random-word))

(def test-words (repeatedly pick-random-word))

(t/deftest
  reflow-test
  (t/testing "123 != 321"
    (t/is (not (= 123 321))))
 (t/testing
  "create-line generates a single string of less then 24 chars"
  (t/is (= "Lorem ipsum dolor sit" 
           (first (create-line (stream-words lines) 24)))))
 (t/testing
  "generate word-stream"
  (let [words-stream (stream-words lines)]
    (t/is (= "Lorem" (first words-stream)))
    (t/is (= "ipsum" (second words-stream))))))

(t/run-tests)