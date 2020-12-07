(ns advent_of_code.aoc-2020.utils
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn read-from-file [path]
  (->> path
       (io/resource)
       (slurp)
       (str/split-lines)))

(defn boolean->int [bool]
  (if bool 1 0))

(defn str->int [s]
  (Integer/parseInt s))

(defn str->char [s]
  (.charAt s 0))

(defn sum-booleans [bools]
  (reduce + (map boolean->int bools)))

(defn get-char-at-index [s index]
  (subs s index (+ index 1)))

(defn in? [lst element]
  (.contains lst element))

(defn between? [n lower upper]
  (and (>= n lower) (<= n upper)))

