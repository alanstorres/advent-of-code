(ns advent-of-code.aoc-2020.day-02
  (:require [clojure.string :as str]
            [advent_of_code.aoc-2020.utils :as u]))

(defn rule-params [rule]
  (let [[interval letter] (str/split rule #"\s")
        [f s] (str/split interval #"-")]
    {:first (u/str->int f)
     :second (u/str->int s)
     :letter letter}))

(defn get-frequencies [s]
  (frequencies (str/lower-case s)))

(defn valid-password-1? [line]
  (let [[rule password] (str/split line #":\s")
        params (rule-params rule)
        freq (get-frequencies password)]
    (<= (get params :first)
        (get freq (u/str->char (get params :letter)) 0)
        (get params :second))))

(defn char-at-index-match [s char index]
  (= (u/get-char-at-index s index) char))

(defn valid-password-2? [line]
  (let [[rule password] (str/split line #":\s")
        params (rule-params rule)
        indexes (map dec [(get params :first) (get params :second)])
        appears? (map (partial char-at-index-match
                               password
                               (get params :letter)) indexes)
        appearances (u/sum-booleans appears?)]
    (= appearances 1)))

(def path "./resources/input-day02")

(def lst (u/read-from-file path))

(def result-pt-1 (u/sum-booleans (map valid-password-1? lst)))

(def result-pt-2 (u/sum-booleans (map valid-password-2? lst)))

(println "Part 1:" result-pt-1)

(println "Part 1:" result-pt-2)
