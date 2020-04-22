(ns deig.core
  (:gen-class))

(defn grayscale-pixel [] [(rand-int 255)])

(defn create-grayscale-pixels [dimension] (vec (repeatedly (* dimension dimension) grayscale-pixel)))

(def example-individual
  "Creates an individual for testing. Sets the genome as a vector with 576 ints from 0-255."
  {:fitness 10 :genome (create-grayscale-pixels 24) :generation 1})

(defn new-individual
  []
  ;Uses random image generation function from either imagez or mikera
  )
(defn cross-over
	[g1 g2]
	;Designed by Sun
	)

(defn absolute-val [val]
  (if (< val 0)
    (* val -1)
    val))

(defn partitionv [n x]
  "Partitions into vecs within vecs"
  (mapv #(vec %) (partition n x)))

;Set image dimensions here
(def dimension 28)

;Set pixel change maximum in each possible mutation here
(def pixel-change-max 30)

;Set the generation where we stop rapidly mutating
(def rapid-mutation-gen 20)

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

(defn mutate [genome current-gen]
  "If generation is below RMG (currently set to 20), rapidly mutate, otherwise slow it down"
  (let [new-genome
        (if (< current-gen rapid-mutation-gen)
          (mutate-pixels genome 0.25)
          (mutate-pixels genome 0.1))]
    (partitionv dimension new-genome)))

#_(mutate-image (:genome example-individual) (:generation example-individual))


(defn fitness
	[i]
	;Evaluate fitness using ML; Require using libpython-clj
	)
(defn best
	[i1 i2]
	(if(> (fitness i1) (fitness i2))
		i1
		i2); Should be used in mutation
	)
(defn evolve
  [popsize generations-number]
  (loop [generation 0
         population (sort fitness (repeatedly popsize new-individual))]
    (let [fittest (first population)]
      (println "Generation:" generation ", Best fitness:" (fitness fittest))
         (if (= generation generations-number))
         fittest
          (recur
            (inc generation)
            (sort fitness (conj (map mutate
                             population) fittest) )))))

(defn -main
  "Run the evolutionary algorithm"
  [& args]
  (evolve "path-to-data"))
