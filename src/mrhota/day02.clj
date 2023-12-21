(ns mrhota.day02
  (:require [clojure.core.logic :as l]
            [clojure.core.logic.arithmetic :as la]
            [clojure.core.logic.pldb :as pldb]))

;; can we use core.logic here?
;; the puzzle involves constraints, and determining which of a set of items
;; satisfy those constraints.

;; The Elf would first like to know which games would have been possible
;; if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?

;; "There exists a game consisting of `draws` draws."
(pldb/db-rel game draws)
;; the relation, draw, describes a game and counts of red, green, and blue pips
;; you might say the predicate for this relation is:
;; "There exists a draw for a game, `gm`, with counts `r`, `g`, and `b`."
(pldb/db-rel draw gm r g b)

(def draws
  (pldb/db
   [1 4 0 3]
   [1 1 2 6]))

#_{:clj-kondo/ignore [:unresolved-symbol]}
;; find all games
(pldb/with-dbs [draws]
  (l/run* [gm]
          (l/fresh [red green blue]
                   (la/<= red 12)
                   (la/<= green 13)
                   (la/<= blue 14)
                   (draw gm red green blue))))

(comment
  ;; Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
  ;; Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
  ;; Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
  ;; Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
  ;; Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
  ;; convert into a sequence of games. each game is a sequence of draws. each draw is a map
  ;; of colors to counts


  (def sample-game-data
    "a sequence of games. each game is a sequence of draws. each draw is a map
     of color keywords to counts."
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
         {:blue 2 :red 1 :green 2}]]])
  
  (pldb/with-dbs [draws]
    (l/run* [gm]
            (l/fresh [game red green blue]
                     (draw game red green blue)
                     (la/<= red 12)
                     (la/<= green 13)
                     (la/<= blue 14))))
  )
