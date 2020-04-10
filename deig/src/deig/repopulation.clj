(ns deig.repopulation
  (:gen-class))

(def x [3])

(fitness 3)

(def x [{:fitness 2 :bar 11}
        {:bar 99 :fitness 1}
        {:bar 55 :fitness 34}
        {:foo 1 :fitness 77}])

(defn greyscale-pixel [] (rand-int 255))

(defn create-greyscale-pixels [dimension] (vec (repeatedly (* dimension dimension) greyscale-pixel)))

(def example-individual
  {:fitness 10 :genome (create-greyscale-pixels 24) :generation 1})

(defn select-parents [population]
  "Selects the two best individuals in any population based on fitness"
  (take-last 2 (sort-by :fitness population)))


;(defn cross-over [genome1 genome2]
;  "We want to cross over the RGB genomes randomly"
;  )


(defn new-individual [object-sizes capacity]
  "Returns a new, random individual in the context of capacity and object-sizes."
  (let [genome (vec (repeatedly (count object-sizes) #(rand-int 2)))]
    {:genome  genome
     :fitness (fitness genome capacity object-sizes)}))

(defn mutate-pixel [pixel mutation-chance]
  "Creates a mutated version of an individual's pixel"
  (if (< (rand) mutation-chance)
    (if (> (rand) 0.5)
      (mod (+ (rand-int 15) pixel) 256)
      (Math/abs (- pixel (rand-int 15))))
    pixel))


(defn mutate-pixels [genome mutation-chance]
  (vec (map #(mutate-pixel % mutation-chance) genome)))

(defn mutate-image [genome current-gen]
  "If generation is below RMG (currently set to 20), rapidly mutate, otherwise slow it down"
  (let [new-genome
        (if (< current-gen 20)
          (mutate-pixels genome 0.25)
          (mutate-pixels genome 0.1))]
    new-genome))

#_(mutate-image (:genome example-individual) 1)

(def test-color [0 0 0])