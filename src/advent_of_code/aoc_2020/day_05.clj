(ns advent-of-code.aoc-2020.day-05
  (:require [advent_of_code.aoc-2020.utils :as u]))

(def path "./resources/aoc_2020/input-day05")

(def codes (u/read-from-file path))

(defn seat-id [row column]
  (+ column (* row 8)))

(defn get-half [code r]
  (let[n (/ (count r) 2)
       [lower upper] (partition n r)]
    (cond
      (u/in? ["F", "L"] code) lower
      (u/in? ["B", "R"] code) upper
      :else [])))

(defn get-position [places spec]
  (loop [s spec
         p (range places)]
      (let [code (str (first s))
            seats (get-half code p)]
        (if (> (count seats) 1)
          (recur (rest s) seats)
          (first seats)))))

(defn log2 [n]
  (/ (Math/log n) (Math/log 2)))

(defn get-seat-info [row-number col-number code]
  (let [row-codes (subs code 0 (log2 row-number))
        col-codes (subs code (log2 row-number))
        row (get-position row-number row-codes)
        col (get-position col-number col-codes)
        id (seat-id row col)]
    {:row row
     :column col
     :id id}))

(def seats-info (map (partial get-seat-info 128 8) codes))

(def ids (map :id seats-info))

;Part 01
(def result-pt-1 (apply max ids))

(println "Part 1:" result-pt-1)

;Part 02
(defn return-if-not-contained [arr element]
  (if (not (u/in? arr element))
    element))

;Most likely really inefficient
(defn ugly-search [arr]
  (let [start (apply min arr)
        end (apply max arr)
        r (range start (+ 1 end))]
    (first (remove nil? (map (partial return-if-not-contained arr) r)))))

(def result-pt-2 (ugly-search ids))

(println "Part 2:" result-pt-2)
