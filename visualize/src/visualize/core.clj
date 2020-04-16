(ns visualize.core
  (:require [quil.core :as q]))

(def viz-width 28) ;; width of the visualization window in pixels
(def viz-height 28) ;; height of the visualization window in pixels
(def num_pixels 200) ;; pixels from centers of edge points to window edge

(defn greyscale-pixel [] (rand-int 255))
(defn create-greyscale-pixels [dimension] ((repeatedly (* dimension dimension) greyscale-pixel)))


(defn rand_pixels []
  "Set a random selection of pixels"

  (let [gr (q/background 255)
        im (q/create-image (* viz-width 2) (* viz-height 2) :rgb)]

    ;; randomly set this many pixels
    (doseq [i (range num_pixels)
            j (range num_pixels)]

      (q/color-mode :hsb)
      ;; set pixel at random x,y with random color

      (def rand_pixel = (rand-int 255))
      (q/set-pixel im i j
                   (q/color 0 0 rand_pixel)))
    (q/set-image 0 0 im))
  (q/no-loop))




(defn start-visualization []
  "Create the Quil sketch."

  (q/sketch
    :size [viz-width viz-height]
    :draw rand_pixels))

;; Start visualization when this file is loaded.
(start-visualization)
