(ns mrhota.day01
  (:require [clojure.string :as string]
            [mrhota.day01-input :refer [input] :rename {input day01-input}]))

(def digit-regexp #"[0-9]|one|two|three|four|five|six|seven|eight|nine")
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

(defn convert-chars-to-digits [input-string]
  (->> input-string
       ;; FIXME? instead of re-seq, I think I need to use
       (re-seq digit-regexp)
       (map digit-map)))

(defn convert-digits-to-ints [digits]
  (+ (* (first digits) 10) (last digits)))

(->> day01-input
     (map convert-chars-to-digits)
     (map convert-digits-to-ints)
     (reduce +))

(comment
  (re-seq digit-regexp (first day01-input))
  (convert-chars-to-digits (first day01-input))
  :rcf)