(ns advent-of-code.aoc-2020.day-06
  (:require [advent_of_code.aoc-2020.utils :as u]
            [clojure.string :as str]))

(def path "./resources/aoc_2020/input-day06")

(defn parse-answers [path]
  (-> path
      (slurp)
      (str/split #"\n\n")))

(def raw-answers (parse-answers path))

(defn linefy [s]
  (str/replace s #"\n" ""))

(defn get-frequencies [s]
  (frequencies (str/lower-case s)))

;Part 01
(defn get-sum-1 [answers]
  (->> answers
       (map linefy)
       (map distinct)
       (map count)
       (reduce +)))

(def result-pt-1 (get-sum-1 raw-answers))

(println "Part 1:" result-pt-1)


;Part 02
(defn get-keys-by-value [m v]
  (filter (comp #{v} m) (keys m)))

(defn remove-whitespace [s]
  (str/replace s #"\s" ""))

(defn group-info [answers]
  (let [size (count answers)
        freq (get-frequencies (apply str answers))]
    {:size size
     :frequencies freq}))

(defn answered-by-all [info]
  (let [size (:size info)
        freq (:frequencies info)]
    (get-keys-by-value freq size)))

(defn get-sum-2 [answers]
  (->> answers
       (map str/split-lines)
       (map group-info)
       (map answered-by-all)
       (map count)
       (reduce +)))

(def result-pt-2 (get-sum-2 raw-answers))

(println "Part 2:" result-pt-2)