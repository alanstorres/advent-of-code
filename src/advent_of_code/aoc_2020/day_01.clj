(ns advent_of_code.aoc_2020.day-01
  (:require [clojure.math.combinatorics :as combo]
            [advent_of_code.aoc-2020.utils :as u]))

(defn multiply-if-sum-to [sum lst]
  (if (= (reduce + lst) sum) (reduce * lst)))

(defn numbers-that-sum-to [quantity sum lst]
  (let [pairs (combo/combinations lst quantity)
        multi (map (partial multiply-if-sum-to sum) pairs)]
    (first (filter some? multi))))

(def path "./resources/input-day01")

(def lst (map u/str->int (u/read-from-file path)))

(def result-pt-1 (numbers-that-sum-to 2 2020 lst))

(def result-pt-2 (numbers-that-sum-to 3 2020 lst))

(println "Part 1:" result-pt-1)

(println "Part 2:" result-pt-2)
