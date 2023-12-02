(ns mrhota.day01-input 
  (:require [clojure.string :as string]))

(def input
  (->> (slurp "resources/day01.txt")
       (string/split-lines)))
