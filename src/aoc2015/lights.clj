(ns aoc2019.lights
  (:require [clojure.string :as s]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [taoensso.tufte :as tufte :refer (defnp p profiled profile)]))

(def lights-array (make-array Long/TYPE 1000 1000))

(tufte/add-basic-println-handler! {})

(def add-two (partial + 2))
(def dec-not-neg #(let[n (dec %)]
                   (if (neg? n)
                     0
                     n)))

(defn parse-instruction [s]
  (let [[_ operation from through] (re-find #"^([\w ]+) (\d+,\d+) through (\d+,\d+)$" s)
        _ (prn "operation: " operation)
        operation (case operation "toggle" add-two, "turn on" inc "turn off" dec-not-neg)
        from (map edn/read-string (s/split from #","))
        through (map edn/read-string (s/split through #","))]
    [from through operation]))

(defn apply-instruction [s]
  (prn s)
  (let [[[from-x from-y] [to-x to-y] op] (parse-instruction s)]
    (doseq [x (range (min from-x to-x) (inc (max from-x to-x)))
            y (range (min from-y to-y) (inc (max from-y to-y)))]
      (aset lights-array  x  y  
            (op (aget lights-array  x  y))))))

(comment
  (reduce + 
   (for [x (range 1000) 
         y (range 1000)]
     (aget lights-array ^int x ^int y)))
  
  (with-open [r (-> "light_instructions.txt" io/resource io/reader)]
    (dorun (map apply-instruction (line-seq r))))

  (doseq [x (range 4 6)
          y (range 1 3)]
    (prn (aget lights-array x y)))

  (let [[_ _ op] (parse-instruction "toggle 0,0 through 999,999")]
    (op 1))
  (aset lights-array 1 1 true)
  (aget lights-array 959,629)
  (get-in lights-array [1 1]))