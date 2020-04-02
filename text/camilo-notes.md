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

## GANS

In classical GAN architectures, both networks train at the same time. This means
that the discriminator is being trained on images from the dataset and generated
images at the same time. For our purposes, this won't work. We need a
discriminator that is already trained by evolution time. Otherwise, our
evolutionary algorithm may terminate early because our fitness function /
discriminator isn't finely tuned yet. It is also difficult to train a discriminator on just a set of real images, because it might learn an incorrect behavior (e.g. all images are real!). This is why I'm thinking we should use a pre-trained discriminator.

Some research has been conducted on addressing a similar problem:
generating images from limited data. In the
[Transferring GANS](http://openaccess.thecvf.com/content_ECCV_2018/papers/yaxing_wang_Transferring_GANs_generating_ECCV_2018_paper.pdf)
paper, Wang et al. try to transfer GAN knowledge from pre-trained networks to
another one. Similarly, we might want to pre-train a Discriminator network
before the evolutionary process.
