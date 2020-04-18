(ns deig.core
  (:require [deig.fitness :refer [fitness]]))

;; Individual creation
(defn grayscale-pixel []
  (let [number (rand-int 256)] (vector number)))

(defn grayscale-genome [dimension]
  "Returns random grayscale genome of dimensions dimension*dimension"
  (vec (repeatedly dimension
                   #(vec (repeatedly dimension (fn [] (vector (rand-int 256))))))))

(defn new-individual [size & {:or {grayscale false}}]
  "Creates a new individual with a genome of a random size*size image"
  (let [genome (if grayscale-pixel (grayscale-genome size) [])]
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
  "Returns an individual selected from population using a tournament."
  (fittest (repeatedly 2 #(rand-nth population))))

;; Mutation
(defn mutate-channel [pixel]
  "Creates a mutated version of an individual's pixel"
  ;; Whether a mutation should occur
  (if (< (rand) 0.2)
    ;; If a mutation occurs, make it binary
    (vec (map (fn [channel] (if (< (rand) 0.5) 255 0) pixel)))
    pixel))

(defn mutate [genome]
  "Randomly mutates an image by changing each pixel channel by a random amount"
  (map (fn [column]
         (map
           (fn [row]
             (map (fn [channel] (mutate-channel channel))
                  row)))
         column)
       genome))

(defn crossover
  [g1 g2]
  (map (fn [column1 column2]
         (map
           (fn [row1 row2]
             (map (fn [channel1 channel2] (if (rand-nth [true false]) channel1 channel2))
                  row1
                  row2)))
         column1
         column2)
       g1
       g2))

(defn make-child [population]
  "Returns a new, evaluated child, produced by mutating the result
  of crossing over parents that are selected from the given population."
  (let [new-genome (vec (mutate (crossover (:genome (select population))
                                           (:genome (select population)))))]
    {:genome  new-genome
     :fitness (fitness new-genome)}))

;; Output
(defn report [generation population]
  "Prints a report on the status of the population at the given generation."
  (println {:generation generation :best (:fitness (fittest population))}))

;; Main functions
(defn evolve [population-size generations]
  "Runs a genetic algorithm to generate an image according to the
  criteria of a deep classifier model."
  (loop [population (repeatedly population-size
                                #(new-individual 28 :grayscale true))
         generation 0]
    (report generation population)
    (if (>= generation generations)
      (fittest population)
      (recur (conj (repeatedly (dec population-size)
                               #(make-child population))
                   (fittest population))
             (inc generation)))))

(defn -main
  "Run the evolutionary algorithm"
  []
  (println "Starting evolution.")
  (evolve 1000 30))

;: Evolve.
#_(-main)
