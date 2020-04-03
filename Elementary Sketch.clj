(defn new-individual
  []
  ;Uses random image generation function from either imagez or mikera
  )
(defn cross-over
	[g1 g2]
	;Designed by Sun
	)
(defn mutate 
	[genome]
	;dependens on what Ben, Caroline and Sun do.
	)
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