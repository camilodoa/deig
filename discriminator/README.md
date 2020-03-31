# GEN

Generative Evolutionary Network.

## About

Deep Evolutionary Image Generation (DEIG) applies an evolutionary approach to the question of Computer-Generated Images. Over time, a population of images are evolved using a pre-trained discriminator network as the population's fitness function.

## Fitness

The fitness function designed in `discriminator.py` is a customizable deep neural network discriminator. It is trained on parameterized datasets and is used to determine the confidence that a given image belongs to the dataset it was trained on.

## Getting started

To download dependencies, navigate to this directory with your shell and run

```bash
pip install -r requirements.txt
```

If you don't have `pip`, you can try to [install it manually](https://pip.pypa.io/en/stable/installing/). If you have a Mac and you have [Homebrew](https://brew.sh/) installed, you can simply run:

```bash
brew install python3
```

which will install the python3 interpreter and the pip3 package manager. If you want the pip interpreter and the python2 interpreter, just replace `python3` with `python` and re-run the command.

If you don't have the Clojure interpreter installed and you have a Mac, you can run:

```bash
brew install clojure
```

This will allow you to access the Clojure interpreter from the command line.

## Project structure

A Discriminator class (to be ported over to Clojure with some sort of library) is written in `discriminator.py`. This discriminator will act as our fitness function.

The evolutionary algorithm itself will be written in `main.clj`.
