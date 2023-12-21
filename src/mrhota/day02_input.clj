(ns mrhota.day02-input
  (:require [clojure.string :as string]))

(defn colors-to-map
  "Convert a string of a drawn color, like \"3 blue\" or \"4 red\",
   into a map like {:blue 3} or {:red 4}, respectively."
  [colors]
  (let [[count color] (string/split colors #" ")]
    {(keyword color) (Integer/parseInt count)}))

(defn draw-to-colors
  "for a given draw of colors and counts, convert into a map
   from color to count.

   e.g.:
   \"3 blue, 1 green, 2 red\"
   ->
   {:blue 3 :green 1 :red 2}"
  [draw]
  (let [individual-colors-from-draw (string/split draw #", ")
        maps-of-color-counts (map colors-to-map individual-colors-from-draw)]
    (into {} maps-of-color-counts)))

(defn extract-game-info [line]
  (let [[game-prefix all-draws-str] (string/split line #": ")
        game-id (parse-long (string/replace game-prefix #"Game " ""))
        draws (string/split all-draws-str #"; ")
        draws-data [game-id (mapv draw-to-colors draws)]]
    draws-data))

(def data
  "A sequence of games. Each game is a pair of game id and a sequence of draws. Each draw is a map
   of colors to counts.

   Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
   Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
   Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
   Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
   Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
   
   becomes:
   
   [[1 [{:red 4 :blue 3}
     {:red 1 :green 2 :blue 6}
     {:green 2}]]
    [2 [{:blue 1 :green 2}
     {:green 3 :blue 4 :red 1}
     {:green 1 :blue 1}]]
    [3 [{:green 8 :blue 6 :red 20}
     {:blue 5 :red 4 :green 13}
     {:green 5 :red 1}]]
    [4 [{:green 1 :red 3 :blue 6}
     {:green 3 :red 6}
     {:green 3 :blue 15 :red 14}]]
    [5 [{:red 6 :blue 1 :green 3}
     {:blue 2 :red 1 :green 2}]]]"
  (->> (slurp "resources/day02.txt")
       (string/split-lines)
       (mapv extract-game-info)))

(comment
  (extract-game-info "Game 3: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green")
  (mapv draw-to-colors ["3 blue, 4 red" "1 red, 2 green, 6 blue" "2 green"])
  data)
  