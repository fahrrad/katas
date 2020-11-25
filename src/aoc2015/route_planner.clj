(ns aoc2015.route-planner)

(defn- new-coordinates [[x y] d]
  (case d
    \< [x (dec y)]
    \> [x (inc y)]
    \^ [(inc x) y]
    \v [(dec x) y]))

(defn directions-to-locations [dx]
  (reduce (fn [acc d] (conj acc (new-coordinates (last acc) d)))
          [[0 0]] dx))

(defn directions-to-visited 
  "Transforms a seq of directions (>,<,^ or v) to a set of coordinated visisted, starting from [0 0]"
  [dx]
  (into #{} (directions-to-locations dx)))

(defn santas-helper [dx]
  (let [santa-d (take-nth 2 dx)
        robo-santa-d (take-nth 2 (drop 1 dx))]
    (into
     (directions-to-visited santa-d)
     (directions-to-visited robo-santa-d))))