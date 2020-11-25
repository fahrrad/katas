(ns mining
  (:import [java.security MessageDigest]))

(defn md5 [^String s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        raw (.digest algorithm (.getBytes s))]
    (format "%032x" (BigInteger. 1 raw))))

(defn md5-wrapper [s n]
  [(md5 (str s n)) n])

(comment
  (first 
   (filter #(.startsWith (first %) "000000")
           (pmap (partial md5-wrapper "yzbqklnj") (range)))) 
  (md5 "abc123")
  (md5-wrapper "abc" 123))