(ns deig.fitness
    (:require [libpython-clj.python :as py]))

(require-python
  '[numpy :as np]
  '[keras_preprocessing :as image-processing]
  '[tensorflow.keras.models :as models])



(defn fitness [img]
    (let [x (image-processing.image/img_to_array img)
          x (np/expand_dims x 0)
          model (models/load_model "model.h5" false)
          probabilities (first (model.predict_proba x))
          fitness (* (apply max probabilities) 100)]
         fitness))






