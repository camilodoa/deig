(ns visualize.core
  (:require [quil.core :as q :include-macros true]          ;; add this
            [quil.middleware :as middleware]))

(def viz-width 28)                                          ;; width of the visualization window in pixels
(def viz-height 28)                                         ;; height of the visualization window in pixels
(def num_pixels 200)
(def viz-width_div_2 14)                                    ;; width of the visualization window in pixels
(def viz-height_div_2 14)

(defn grayscale-pixel [] (rand-int 255))

(defn create-grayscale-pixels [dimension]
  (vec (repeatedly dimension
                   #(vec (repeatedly dimension grayscale-pixel)))))

;(def n (create-grayscale-pixels viz-width))
;(print n)
;(print (get (get n 1) 1))

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



(defn draw []
  (q/background 255)
  (let [size 28
        gr (q/create-graphics 28 28)
        im (q/create-image viz-width viz-height :rgb)]
    (q/with-graphics gr
                     ;; make it hsb
                     (q/color-mode :hsb)
                     (q/fill 0 0 (rand-int 255))

                     ;; no outside line
                     (q/stroke nil)

                     ; draw arc
                     ;; x, y, width, height, start, stop, mode (open, chord, pie)
                     ;(q/arc 28 28 20 17 0 q/QUARTER-PI)
                     ; draw different arc modes

                     (doseq [[ind mode] [[0 :open] [1 :chord] [2 :pie]]]
                       (q/arc (rand-int 28) (rand-int 28) (rand-int 50) (rand-int 200) 0 q/QUARTER-PI mode)))

    (q/image gr 0 0)
    (let [px (q/pixels gr)
          half (/ (* size size) 2)]
      (dotimes [i half] (aset-int px (+ i half) (aget px i))))
    (q/update-pixels gr)
    ;(pprint (q/pixels gr))
    ;(println (q/get-pixel 24 20))
    (q/no-loop)))


(defn start-visualization []
  "Create the Quil sketch."
  (q/sketch
    :size [28 28]
    :draw draw))



(start-visualization)




