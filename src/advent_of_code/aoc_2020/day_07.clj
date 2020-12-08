(ns advent-of-code.aoc-2020.day-07
  (:require [clojure.string :as str]
            [advent_of_code.aoc-2020.utils :as u]
            [loom.graph :as l.g]
            [loom.alg :as l.alg]))

(def path "./resources/aoc_2020/input-day07")

(def my-bag :shiny-gold)

(def inputs (u/read-from-file path))

(defn format-content [content]
  (let [[_ & colors] (str/split content #"\s")]
    (str/join "-" colors)))

(defn parse-content [content]
  (if (= content "no other bags.")
    []
    (let [bags-info (-> content
                        (str/replace #" bags?\.?" "")
                        (str/split #", "))]
      (map keyword (map format-content bags-info)))))

(defn parse-rule [rule]
  (let [[color content] (str/split rule #" bags contain ")]
    {(keyword (str/replace color " " "-"))
     (vec (parse-content content))}))

(def rules-parsed (apply merge (map parse-rule inputs)))

(def rules-graph (l.g/digraph rules-parsed))

(defn has-path? [g end start]
  (some? (l.alg/shortest-path g start end)))

(def result-pt-1 (->> (keys rules-parsed)
                      (map (partial has-path? rules-graph my-bag))
                      (u/sum-booleans)))

(println "Part 1:" result-pt-1)
