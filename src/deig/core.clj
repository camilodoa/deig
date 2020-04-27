(ns deig.core
  (:require [deig.fitness :refer [fitness save-image predict-class]]
            [deig.visualize :as vis]
            [quil.core :as q :include-macros true]          ;; add this
            [quil.middleware :as middleware]
            [clojure.pprint :as pp])
  (:gen-class))
;Set image dimensions here
(def dimension 28)

;Set pixel change maximum here
(def pixel-change-max 30)

;Set the generation when we stop rapidly mutating
(def rapid-mutation-gen 20)

;Set the color scheme we're operating on
(def rgb false)
;; Individual creation
(defn grayscale-genome [dimension]
  "Returns random grayscale genome of dimensions dimension*dimension"
  (vec (repeatedly dimension
                   #(vec (repeatedly dimension (fn [] (vector (rand-nth [0 0 0 0 0 255]))))))))

(defn rbg-genome [dimension]
  "Returns random grayscale genome of dimensions dimension*dimension"
  (vec (repeatedly dimension
                   #(vec (repeatedly dimension (fn [] (vec (repeatedly 3 (rand-int 256)))))))))

(defn new-individual [size & {:keys [grayscale] :or {grayscale false}}]
  "Creates a new individual with a genome of a random size*size image"
  (let [genome (if (true? grayscale) (grayscale-genome size) (rbg-genome size))]
    {:genome  genome
     :fitness (fitness genome)}))

;; Selection
(defn fittest [individuals]
  "Returns the fittest of the given individuals."
  (reduce (fn [i1 i2]
            (if (> (:fitness i1) (:fitness i2))
              i1
              i2))
          individuals))

(defn select [population]
  "Returns an individual selected from population using a tournament of 10."
  (fittest (repeatedly 10 #(rand-nth population))))

(defn abs [val]
  "Absolute value function."
  (if (< val 0)
    (* val -1)
    val))

(defn partitionv [n x]
  "Partitions into vecs within vecs"
  (mapv #(vec %) (partition n x)))

;; Mutation
(defn mutate-channel [pixel]
  "Creates a mutated version of an individual's pixel. Mutates each channel randomly."
  (let [mutation-rate 0.1]
    (if (< (rand) mutation-rate)
      ;; Case where the pixel mutates, you change each of its color channels
      (vec (map (fn [channel]
                  ;; Our changes to the color are bounded within a range of (-30, 30)
                  (let [change (+ (* (- 30 -30) (rand)) -30)]
                    (cond
                      ;; If the change is outside of our range, stick to our range
                      (> (+ channel change) 255) 255
                      (< (+ channel change) 0) 0
                      ;; Otherwise, just use the change
                      :else (+ channel change)))) pixel))
      ;; Case where it doesn't
      pixel)))

(defn mutate-uniform [genome]
  "Mutate each pixel in genome with the same mutation rate."
  (map (fn [row]
         (map
           (fn [column]
             (map (fn [channel] (mutate-channel channel))
                  column)))
         row)
       genome))


(defn mutate-chunk [genome]
  "Mutate a random 3x3 square in a random spot of the genome"
  (let [parent-pixel-location [(rand-int (count genome)) (rand-int (count (first genome)))]
        parent-pixel (nth (nth genome (first parent-pixel-location)) (second parent-pixel-location))]
    (map-indexed (fn [i col]
                   (map-indexed (fn [j pixel]
                                  (if (and
                                        (<= (abs (- i (first parent-pixel-location))) 1)
                                        (<= (abs (- j (second parent-pixel-location))) 1))
                                    parent-pixel
                                    pixel)) col)) genome)))


(defn mutate-line [genome]
  "Returns a genome with a centered vertical white line."
  (let [width (rand-nth [1 2 3])
        x-start (rand-int (count (first genome)))
        height (rand-int (count genome))
        y-start (rand-int (count genome))
        color (rand-nth [255 255 255 0])]
    (vec (map-indexed (fn [i col]
                        (map-indexed (fn [j pixel]
                                       (if (and (< j (+ x-start width)) (> j x-start) (> i y-start) (< i (+ y-start height)))
                                         [color]
                                         pixel)) col)) genome))))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;Set image dimensions here
(def dimension 28)

;Set pixel change maximum here
(def pixel-change-max 30)

;Set the generation when we stop rapidly mutating
(def rapid-mutation-gen 20)

;Set the color scheme we're operating on
(def rgb false)

;(defn grayscale-pixel
;  []
;  (let [number (rand-int 256)] (vector number)))
;
;(defn create-grayscale-pixels [dimension]
;  (vec (repeatedly dimension
;                   #(vec (repeatedly dimension grayscale-pixel)))))
;
;
;(def example-individual
;  {:fitness 10 :genome (create-grayscale-pixels dimension) :generation 1})
;
;;(defn absolute-val [val]
;;  (if (< val 0)
;;    (* val -1)
;;    val))
;
(defn partitionv [n x]
  "Partitions into vecs within vecs"
  (mapv #(vec %) (partition n x)))
;
;
;(defn mutate-pixel [pixel mutation-chance]
;  "Creates a mutated version of an individual's pixel"
;  (if (< (rand) mutation-chance)
;    (if (> (rand) 0.5)
;      (mapv #(mod (+ (rand-int pixel-change-max) %) 255) pixel)
;      (mapv #(absolute-val (- % (rand-int pixel-change-max))) pixel))
;    pixel))
;
;
;(defn mutate-pixels [genome mutation-chance]
;  "This let function converts a 28x28x1 vector into a 784x1x1 for easy mutation"
;  (let [stripped-genome (reduce into [] genome)]
;    (mapv #(mutate-pixel % mutation-chance) stripped-genome)))

;(defn mutate-image [genome current-gen]
;  "If generation is below RMG (currently set to 20), rapidly mutate, otherwise slow it down"
;  (let [new-genome
;        (if (< current-gen rapid-mutation-gen)
;          (mutate-pixels genome 0.25)
;          (mutate-pixels genome 0.1))]
;    (partitionv dimension new-genome)))



#_(mutate-image (:genome example-individual) (:generation example-individual))


(defn binary-convert [num]
  "Converts any nonzero values into 1"
  (if (= num 0)
    0
    (/ num num)))

(defn transform-vector [quilvec]
  "Transforms the quil vector into a vector of 1 and 0s"
  (mapv #(binary-convert %) quilvec))

(defn alter-pixel [rgb quilvecval genomeval]
  "Changes the pixel to the current color scheme if quilvec is currently 1
  otherwise, keep current genome value."
  (if (= quilvecval 1)
    rgb
    genomeval))

(defn mutate-quil-shape [genome]
  "Executes the start-visualization function to generate a new quilvec"
  (vis/start-visualization)
  "Strips down the genome, applies a random color to the shape created by the quilvec,
  and place it on the current genome."


  (let [quilvec (read-string (slurp "quilvec.txt"))
        stripped-genome (reduce into [] genome)
        rgb (if (true? rgb)
              (vec (take 3 (repeatedly #(rand-int 255))))
              [(if (> 0.5 rand) [0] [255])])
        transformed-quilvec (transform-vector quilvec)]

    (partitionv dimension (mapv #(alter-pixel rgb %1 %2) transformed-quilvec stripped-genome))))

#_(mutate-quil-shape (:genome example-individual))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn mutate [genome]
  "Randomly selects a mutation to mutate an image with"
  (let [type (rand)]
    (cond
      (< type 0.2) (try
                     (mutate-quil-shape genome)
                     (catch Exception e (mutate-chunk genome)))

      (and (> type 0.2) (< type 0.4)) (mutate-line genome)
      :else (mutate-uniform genome))))

(defn make-child [population]
  "Returns a new, evaluated child, produced by mutating the result
  of crossing over parents that are selected from the given population."
  (let [new-genome (vec (mutate (:genome (select population))))]
    {:genome  new-genome
     :fitness (fitness new-genome)}))

(defn populate [population-size]
  "Returns new population of random images"
  (repeatedly population-size
              #(new-individual 28 :grayscale true)))

(defn re-populate [population population-size]
  "Returns population of individuals based on fitness selection"
  (conj (repeatedly (dec population-size)
                    #(make-child population))
        (fittest population)))

;; Output
(defn report [generation population]
  "Prints a report on the status of the population at the given generation."
  (let [fittest (fittest population)
        predicted-class (predict-class (:genome fittest))]
    ;; Save image to file
    (save-image (:genome fittest) generation predicted-class)
    ;; Print stats
    (println {:generation      generation
              :best-fitness    (:fitness fittest)
              :predicted-class predicted-class})))

;; Main functions
(defn evolve [population-size generations]
  "Runs a genetic algorithm to generate an image according to the
  criteria of a deep classifier model."
  (loop [population (populate population-size)
         generation 0]
    (report generation population)
    (if (>= generation generations)
      (fittest population)
      (recur (re-populate population population-size)
             (inc generation)))))

(defn -main []
  "Run the evolutionary algorithm"
  (println "Starting evolution.")
  (evolve 300 50))

;: Evolve.
#_(-main)
