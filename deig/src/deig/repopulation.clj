(ns deig.repopulation
  (:gen-class))

;Set image dimensions here
(def dimension 28)

;Set pixel change maximum here
(def pixel-change-max 30)

;Set the generation when we stop rapidly mutating
(def rapid-mutation-gen 20)

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
  "This let function converts a 28x28x1 vector into a 576x1x1 for easy mutation"
  (let [stripped-genome (reduce into [] genome)]
    (mapv #(mutate-pixel % mutation-chance) stripped-genome)))

(defn mutate-image [genome current-gen]
  "If generation is below RMG (currently set to 20), rapidly mutate, otherwise slow it down"
  (let [new-genome
        (if (< current-gen rapid-mutation-gen)
          (mutate-pixels genome 0.25)
          (mutate-pixels genome 0.1))]
    (partitionv dimension new-genome)))


#_(mutate-image (:genome example-individual) (:generation example-individual))

(defn store-image [image]
  "Store the following image into a repository. This image will have the generation and vector genome stored."
  )
