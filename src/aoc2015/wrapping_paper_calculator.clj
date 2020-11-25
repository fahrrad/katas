(ns wrapping-paper-calculator
  (require [clojure.string :as s]))

(defn surface-box [l w h]
  (let [side-1 (* l w)
        side-2 (* w h)
        side-3 (* h l)]
    (+ (+ (* 2 side-1) (* 2 side-2) (* 2 side-3))
       (min side-1 side-2 side-3))))

(defn perimeter [[l w]]
  (+ l l w w))

(defn length-ribbon [l w h]
  (+ (* l w h)
     (apply min (map perimeter [[l w] [w h] [h l]]))))