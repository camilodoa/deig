(ns deig.repopulation
  (:require [deig.visualize :as vis])
  (:gen-class))

;Set image dimensions here
(def dimension 28)

;Set pixel change maximum here
(def pixel-change-max 30)

;Set the generation when we stop rapidly mutating
(def rapid-mutation-gen 20)

;Set the color scheme we're operating on
(def rgb false)

(defn grayscale-pixel
  []
  (let [number (rand-int 256)](vector number)))

(defn create-grayscale-pixels [dimension]
  (vec (repeatedly dimension
                   #(vec (repeatedly dimension grayscale-pixel)))))


(def example-individual
  {:fitness 10 :genome (create-grayscale-pixels dimension) :generation 1})


;(defn select-parents [population]
;  "Selects the two best individuals in any population based on fitness"
;  (take-last 2 (sort-by :fitness population)))

;(defn select-best [population]
;  "Selects the best performing individual for elite, asexual reproduction"
;  (first (sort-by :fitness population)))


;HSV: Hue, Saturation, and Value (or Brightness)


;(defn cross-over [genome1 genome2]
;  "We want to cross over the RGB genomes randomly"
;  )


;(defn new-individual [object-sizes capacity]
;  "Returns a new, random individual in the context of capacity and object-sizes."
;  (let [genome (vec (repeatedly (count object-sizes) #(rand-int 2)))]
;    {:genome  genome
;     :fitness (fitness genome capacity object-sizes)}))

(defn absolute-val [val]
  (if (< val 0)
    (* val -1)
    val))

(defn partitionv [n x]
  "Partitions into vecs within vecs"
  (mapv #(vec %) (partition n x)))

;(defn mutate-flattened-pixel [pixel mutation-chance]
;    "Creates a mutated version of an individual's pixel -- only when completely flattened"
;    (if (< (rand) mutation-chance)
;      (if (> (rand) 0.5)
;        (mod (+ (rand-int 15) pixel) 255)
;        (absolute-val (- pixel (rand-int 15))))
;      pixel))

(defn mutate-pixel [pixel mutation-chance]
  "Creates a mutated version of an individual's pixel"
  (if (< (rand) mutation-chance)
    (if (> (rand) 0.5)
      (mapv #(mod (+ (rand-int pixel-change-max) %) 255) pixel)
      (mapv #(absolute-val (- % (rand-int pixel-change-max))) pixel))
    pixel))


(defn mutate-pixels [genome mutation-chance]
  "This let function converts a 28x28x1 vector into a 784x1x1 for easy mutation"
  (let [stripped-genome (reduce into [] genome)]
    (mapv #(mutate-pixel % mutation-chance) stripped-genome)))

(defn mutate-image [genome current-gen]
  "If generation is below RMG (currently set to 20), rapidly mutate, otherwise slow it down"
  (let [new-genome
        (if (< current-gen rapid-mutation-gen)
          (mutate-pixels genome 0.25)
          (mutate-pixels genome 0.1))]
    (partitionv dimension new-genome)))

(defn mutate-horizontal-line [genome]
  "Mutates a (uniformly colored) horizontal line into the genome. Needs fixing"
  (let [stripped-genome (reduce into[] genome)
        line-start (rand-int 1)
        pixel-values (rand-int 255)]
    (partitionv 28 (assoc stripped-genome line-start pixel-values))))


#_(mutate-image (:genome example-individual) (:generation example-individual))



(defn store-image [image]
  "Store the following image into a repository. This image will have the generation and vector genome stored."
  )

(def example-quilvec [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, -841558314, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -2697514, -53029162, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 1809241814, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -2697514, -120138026, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 299029202, -153692458, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -2697514, -204024106, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, -1512712747, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -287910186, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 1121310165, -2697514, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -405350698, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      -841558314, -2697514, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -53029162, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 1809241814, -2697514, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -120138026, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 299029202, -153692458, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -204024106, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, -1512712747, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -287910186, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 1121310165, -2697514, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -2697514, -2697514, -2697514, -2697514, -2697514,
                      -2697514, -2697514, -405350698, 0, 0, 0, 0] )

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
  (let [quilvec
        (read-string (slurp "quilvec.txt"))
        stripped-genome (reduce into[] genome)
        rgb (if (true? rgb)
              (vec (take 3 (repeatedly #(rand-int 255))))
              [(rand-int 255)])
        transformed-quilvec (transform-vector quilvec)]
    (partitionv dimension (mapv #(alter-pixel rgb %1 %2) transformed-quilvec stripped-genome))))

#_(mutate-quil-shape (:genome example-individual))
