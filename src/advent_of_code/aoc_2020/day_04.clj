(ns advent-of-code.aoc-2020.day-04
  (:require [clojure.string :as str]
            [advent_of_code.aoc-2020.utils :as u]))

(def path "./resources/input-day04")

(defn parse-passport [path]
  (-> path
      (slurp)
      (str/split #"\n\n")))

(defn linefy [s]
  (str/replace s #"\n" " "))

(defn key-values [s]
  (let [[k v] (str/split s #":")]
    {(keyword k) v}))

(defn mappify [s]
  (let [pairs (str/split s #"\s")
        infos (into {} (map key-values pairs))]
    infos))

(def required-fields [:byr :iyr :eyr :hgt :hcl :ecl :pid])

(def passports (map mappify (map linefy (parse-passport path))))

;Part 01
(defn valid-passport-1? [req-fields passport]
  (let [fields (keys passport)]
    (every? true? (map (partial u/in? fields) req-fields))))

(def validity-status-1 (map (partial valid-passport-1? required-fields) passports))

(def result-pt-1 (u/sum-booleans validity-status-1))

(println "Part 1:" result-pt-1)

;Part 02
(defn valid-byr? [s]
  (let [n (u/str->int s)]
    (and (= (count s) 4) (u/between? n 1920 2002))))

(defn valid-iyr? [s]
  (let [n (u/str->int s)]
    (and (= (count s) 4) (u/between? n 2010 2020))))

(defn valid-eyr? [s]
  (let [n (u/str->int s)]
    (and (= (count s) 4) (u/between? n 2020 2030))))

(defn valid-hgt? [s]
  (let [number (-> s
                   (str/replace #"in" "") (str/replace #"cm" ""))
        unit? (or (str/includes? s "in") (str/includes? s "cm"))
        number? (= number (re-matches #"[0-9]+" number))]
    (if (or (not number?) (not unit?))
      false
      (if (str/includes? s "in")
        (u/between? (u/str->int number) 59 76)
        (u/between? (u/str->int number) 150 193)))))

(defn valid-hcl? [s]
  (= s (re-matches #"#[0-9a-f]{6}" s)))

(defn valid-ecl? [s]
  (u/in? ["amb" "blu" "brn" "gry" "grn" "hzl" "oth"] s))

(defn valid-pid? [s]
  (= s (re-matches #"[0-9]{9}" s)))

(defn valid-cid? [s]
  true)

(defn check-passport-info [passport field]
  (let [checks {:byr valid-byr?
                :iyr valid-iyr?
                :eyr valid-eyr?
                :hgt valid-hgt?
                :hcl valid-hcl?
                :ecl valid-ecl?
                :pid valid-pid?
                :cid valid-cid?}]
    ((field checks) (get passport field ""))))

(defn valid-passport-2? [req-fields passport]
  (let [fields (keys passport)
        req-present? (every? true? (map (partial u/in? fields) req-fields))]
    (if req-present?
      (every? true? (map (partial check-passport-info passport) fields))
      false)))

(def validity-status-2 (map (partial valid-passport-2? required-fields) passports))

(def result-pt-2 (u/sum-booleans validity-status-2))

(println "Part 2:" result-pt-2)







