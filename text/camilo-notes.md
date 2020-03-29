# Notes

## Image creation

From my research it seems that [imagez](https://github.com/mikera/imagez) is a
good option if we want to evolutionarily create new images. Here is one of their
examples:

```clojure
(use 'mikera.image.core)
(use 'mikera.image.colours)

;; create a new image
(def bi (new-image 32 32))

;; gets the pixels of the image, as an int array
(def pixels (get-pixels bi))

;; fill some random pixels with colours
(dotimes [i 1024]
  (aset pixels i (rand-colour)))

;; update the image with the newly changed pixel values
(set-pixels bi pixels)
```

If we can control this randomness, we should be golden.
