;This code hasn't been tested or run yet because it has been finished. 

(def image-sizes (* 28 28)); For MINST

(defn new-individual
  []
  ;Uses random image generation function from either imagez or mikera
  ;For demo purposes, we will use a vector of size n*n (for MINST dataset it is 28*28)
  (let [genome (vec (repeatedly (count image-sizes) (rand-int 256)))]
    {:genome  genome
     :fitness (fitness genome capacity image-sizes)}))


(defn mutate [genome current-gen]
  "If generation is below RMG (currently set to 20), rapidly mutate, otherwise slow it down"
  (let [new-genome
        (if (< current-gen 20)
          (mutate-pixels genome 0.25)
          (mutate-pixels genome 0.1))]
    new-genome)); written by Sun; Mutates individual pixels

(defn mutate-pixel [pixel mutation-chance]
  "Creates a mutated version of an individual's pixel"
  (if (< (rand) mutation-chance)
    (if (> (rand) 0.5)
      (mod (+ (rand-int 15) pixel) 256)
      (Math/abs (- pixel (rand-int 15))))
    pixel))


(defn mutate-pixels [genome mutation-chance]
  (vec (map #(mutate-pixel % mutation-chance) genome)))

(defn prelimnary-mutation
  [genome generation]
  (for [gene genome]
    (map #((if (> rand (+ (/ generation generations-number) 0.05) (rand-int 256)))) gene))
    ); My mutation function

  (defn prelimnary-mutation2
  [genome generation partition-size]
  (let [new-genome (partition partition-size genome)]
    (for [gene new-genome]
    (if (> (rand) 0.2)
      (vec (repeatedly partition-size (rand-int 256)))
      gene
      ))); My second mutation function

(defn fitness
	[i]
	;Evaluate fitness using ML; Require using libpython-clj
	)

(defn best
	[i1 i2]
	(if(> (fitness i1) (fitness i2))
		i1
		i2); 
	)

(defn evolve
  [popsize generations-number]
  (loop [generation 0
         population (sort :fitness (repeatedly popsize new-individual))]; Can we use :fittnes as a function here?
    (let [fittest (first population)]
      (println "Generation:" generation ", Best fitness:" (:fitness fittest))
         (if (= generation generations-number))
         fittest
          (recur
            (inc generation)
            (sort fitness (conj (map mutate*
                             population) fittest) ))))); * any mutation function