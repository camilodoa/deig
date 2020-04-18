(ns deig.fitness
    (:require
      [libpython-clj.require :refer [require-python]]
      [libpython-clj.python :as py :refer [call-attr]]
      [quil.core :as q]))

(require-python
  '[numpy :as np]
  '[keras_preprocessing.image :as image-processing]
  '[tensorflow.keras.models :as models])

(defn fitness [individual]
      "Evaluate the image based on its best categorical score"
    (let [px (:genome individual)
          x-array (image-processing/img_to_array px)
          x (np/expand_dims x-array 0)
          model (models/load_model "model.h5")
          probabilities (first (call-attr model "predict_proba"  x))
          fitness (* (apply max probabilities) 100)]
        (print probabilities)
         fitness))

(defn grayscale-pixel
  []
  (let [number (rand-int 256)](vector number)))

(defn create-grayscale-pixels [dimension]
  (vec (repeatedly dimension
                   #(vec (repeatedly dimension grayscale-pixel)))))

(def example-individual
  "Creates an individual for testing. Sets the genome as a vector with 576 ints from 0-255."
  {:fitness 10 :genome (create-grayscale-pixels 28) :generation 1})

(defn trial
    "Run a practice fitness run"
    []
  (fitness example-individual))

; Should return 100.
#_ (trial)                                                  ;

