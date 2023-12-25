(ns mrhota.day02
  (:require [clojure.core.logic :as l]
            [clojure.core.logic.fd :as fd]
            [clojure.test :refer [is]]
            [mrhota.day02-input :as input]))

;; The Elf would first like to know which games would have been possible
;; if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?

;; Let's use core.logic.fd (for "finite domain") to describe the domains of
;; possible values for each color in a draw. Let's call a "possible game"
;; one in which the draws for each color are never greater than the values
;; mentioned above. For example, in a "possible game", on any draw, the
;; number of red cubes can be any number from 0 to 12, green cubes
;; can be any number from 0 to 13, and blue cubes can be any number
;; from 0 to 14.
(def red-domain (fd/interval 12))
(def green-domain (fd/interval 13))
(def blue-domain (fd/interval 14))

#_{:clj-kondo/ignore [:unresolved-symbol]}
(defn game-possible
  "given a vector of game-id and a sequence of draws, return the game-id
     if the game is possible, otherwise return nil."
  [[game-id draws]]
  ;; (run 1 ...) returns a list of 1 (or 0) result, but we don't want a list; so just take the first
  (first
   ;; we expect a maximum of 1 solution, so we use (run 1 ...) instead of (run* ...)
   (l/run 1 [q]
          ;; if a solution exists, q will unify (be the same as) game-id,
          ;; every draw of reds in the game is in the red-domain,
          ;; every draw of greens in the game is in the green-domain,
          ;; and every draw of blues in the game is in the blue-domain
          (l/== q game-id)
          (l/everyg #(fd/in % red-domain) (keep :red draws))
          (l/everyg #(fd/in % green-domain) (keep :green draws))
          (l/everyg #(fd/in % blue-domain) (keep :blue draws)))))

;; DAY 2, PART 1 SOLUTION
;; ----------------------
;; for each game in data, keep only the non-nil values
;; returned by game-possible, then apply + to the resulting sequence
(->> input/data
     (keep game-possible)
     (apply +))


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

  (game-possible [1 [{:red 4 :blue 3}
                     {:red 1 :green 2 :blue 6}
                     {:green 2}]])

  (game-possible [3 [{:green 8 :blue 6 :red 20}
                     {:blue 5 :red 4 :green 13}
                     {:green 5 :red 1}]])
  (keep :green [{:red 4 :blue 3}
                {:red 1 :green 2 :blue 6}
                {:green 2}])

  (is (= (keep game-possible sample-game-data)
         '(1 2 5)))
  (is (= (apply + (keep game-possible sample-game-data))
         8))

  (map game-possible sample-game-data)

  (defn game-determination
    [game-data]
    (map + (map game-possible game-data)))

  :rcf)
