(ns advent-of-code.aoc-2020.day-03
  (:require [advent_of_code.aoc-2020.utils :as u]))

(defn tree? [s]
  (= s "#"))

(def path "./resources/input-day03")

(def terrain-map (u/read-from-file path))

(defn get-terrain-info [m]
  {:length (count m)
   :width (count (first m))})

(defn encountered-elements [terrain down right]
  (let [info (get-terrain-info terrain)]
    (loop [e terrain
           r right
           result []]
      (let [v (subvec e down)
            line (nth v 0)
            index (mod r (:width info))
            c (u/get-char-at-index line index)
            res (conj result c)]
        (if (> (count v) down)
          (recur v (+ r right) res)
          res)))))

(defn count-trees [terrain slopes]
  (let [right (:right slopes)
        down (:down slopes)]
    (u/sum-booleans (map tree? (encountered-elements terrain down right)))))

(def slopes-pt1 {:right 3 :down 1})

(def result-pt-1 (count-trees terrain-map slopes-pt1))

(def slopes-pt2 [{:right 1 :down 1}
                 {:right 3 :down 1}
                 {:right 5 :down 1}
                 {:right 7 :down 1}
                 {:right 1 :down 2}])

(def result-pt-2 (reduce * (map (partial count-trees terrain-map) slopes-pt2)))

(println "Part 1:" result-pt-1)

(println "Part 2:" result-pt-2)