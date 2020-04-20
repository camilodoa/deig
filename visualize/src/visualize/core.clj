(ns visualize.core

      (:require [quil.core :as q :include-macros true]          ;; add this
                [quil.middleware :as middleware]))

  (def viz-width 28)                                          ;; width of the visualization window in pixels
  (def viz-height 28)                                         ;; height of the visualization window in pixels

  (def viz-width_div_2 14)                                          ;; width of the visualization window in pixels
  (def viz-height_div_2 14)

  (defn grayscale-pixel [] (rand-int 255))

  (defn create-grayscale-pixels [dimension]
        (vec (repeatedly dimension
                         #(vec (repeatedly dimension grayscale-pixel)))))

  (defn visualize_pixels []
    ;; set background color to white 255
        (let [n (create-grayscale-pixels viz-width)
              gr (q/background 255)
              im (q/create-image viz-width viz-height :rgb)]

          ;; randomly set this many pixels
          (doseq [i (range viz-width)
                  j (range viz-height)]

            (q/color-mode :hsb)

            (let [b (get (get n i) j)]
              (q/set-pixel im i j
                           (q/color 0 0 b))))
          (q/set-image 0 0 im))
        (q/no-loop))

  (defn start-visualization []
        "Create the Quil sketch."
        (q/sketch
          :size [viz-width_div_2 viz-height_div_2]
          :draw visualize_pixels))


  (start-visualization)
  ;;;

