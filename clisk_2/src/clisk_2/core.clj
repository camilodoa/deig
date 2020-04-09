(ns clisk-2.core
  (:use [clisk live]))

;;https://cljdoc.org/d/net.mikera/clisk/0.11.0/api/clisk.effects#radial

(def checker-pattern (checker 0 1))    ;; 0 and 1 are shortcuts for black=[0 0 0] and white=[1 1 1]
(show checker-pattern)

(show (viewport [-1 -1] [1 1]
                (v*
                  (warp globe vplasma)      ;; colour vector plasma texture, samples on the surface of a globe
                  (light-value [-1 -1 1] (height-normal globe)))))  ;; diffuse lighting using globe as heightmap

;; warp checker pattern
(show  (scale 0.25 (offset vnoise checker-pattern) ))

;; make noise
(seed-perlin-noise!)

;; Add random turbulence to a pattern according to a perlin noise offset
(def altered (turbulate 300 checker-pattern))
(show checker-pattern)

(show
  (offset
    [0.2 0.2]
    (checker red pink)))

;; warp
(show
  (warp
    (scale 0.2 vnoise)
    (checker red blue)))


;; Creating new images not from checker pattern

;; black and white stripes
(show (scale 0.05 (vsin x)))

;; blue fuzzies
(show (scale 0.5 (rgb-from-hsl vnoise)))

;; combination of 2 above
(show (v* (scale 0.05 (vsin x)) (scale 0.5 (rgb-from-hsl vnoise))))

;; warped fuzzies
(def warped-fuzzies
  (warp
    ;; closer to 1 = less warped
    (scale 0.9 vnoise)

    ;; closer to 1 = less variation in colors
    (scale 0.1 (rgb-from-hsl vnoise))))

(show warped-fuzzies)

;; shatters the checker pattern
(show (shatter checker-pattern))

;; shatters the warped fuzzies
(show (shatter warped-fuzzies))

;; now use lightness-from-rgb
;; black and white fuzzies
(def bw-fuzzies (scale 0.5 (lightness-from-rgb vnoise)))
(show bw-fuzzies)

;; weird cow spots
(def hue (scale 0.5 (hue-from-rgb vnoise)))
(show hue)

(show clouds)
(show velvet)

;; like cells
(show flecks)

;; interesting radial pattern
(show wood)

;; like clouds but less varied
(show plasma)