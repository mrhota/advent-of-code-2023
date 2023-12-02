(ns mrhota.day01
  (:require [mrhota.day01-input :refer [data]]))

(def digit-regexp-begin #"^.*?([0-9]|one|two|three|four|five|six|seven|eight|nine)")
(def digit-regexp-end #".*([0-9]|one|two|three|four|five|six|seven|eight|nine).*?$")
(def digit-map {"one" 1
                "two" 2
                "three" 3
                "four" 4
                "five" 5
                "six" 6
                "seven" 7
                "eight" 8
                "nine" 9
                "0" 0
                "1" 1
                "2" 2
                "3" 3
                "4" 4
                "5" 5
                "6" 6
                "7" 7
                "8" 8
                "9" 9})

(defn convert-str-to-digits
  "Given an input-string like 'twoone1', or 'oneight6six',
   return the corresponding pair of first and last integer values, like
   [2 1] or [1 6] for the above example input-strings.
   
   strings like 'twone' or 'oneight' correspond to [2 1] and [1 8] respectively."
  [input-string]
  (let [first-as-str (second (re-find digit-regexp-begin input-string))
        last-as-str (second (re-find digit-regexp-end input-string))
        first-digit (digit-map first-as-str)
        last-digit (digit-map last-as-str)]
    [first-digit last-digit]))

(defn convert-digits-to-ints [digits]
  (+ (* (first digits) 10) (last digits)))

(->> data
     (map convert-str-to-digits)
     (map convert-digits-to-ints)
     (reduce +))

(comment
  ;;(re-seq digit-regexp-begin (first data))
  (convert-str-to-digits (first data))

  (convert-str-to-digits "oneight6six")
  (convert-str-to-digits "twoonetwone")

  (convert-str-to-digits "oneight")

  (convert-digits-to-ints [2 1 2])

  (->> data
       (map convert-str-to-digits)
       (map convert-digits-to-ints))

  :rcf)
