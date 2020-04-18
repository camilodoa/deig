# Fitness

The fitness function is an API for the customizable deep neural network discriminator class in `discriminator.py`. It is trained on parameterized datasets and is used to determine the confidence that a given image belongs to the dataset it was trained on.

**This discriminator is currently only written to work on categorical datasets.**

## Getting started

To download dependencies, navigate to this directory with your shell and run

```bash
pip install -r requirements.txt
```

If you don't have `pip`, you can try to [install it manually](https://pip.pypa.io/en/stable/installing/). If you have a Mac and you have [Homebrew](https://brew.sh/) installed, you can simply run:

```bash
brew install python3
```

which will install the python3 interpreter and the pip3 package manager.

## Project structure

A Discriminator class is written in `discriminator.py`. This discriminator is used for training the underlying mechanisms of our fitness function. The structure of our fitness function is defined in `fitness.py`. This is the function that will be translated to Clojure for use in our evolutionary algorithm.

I also included a quick script for training a classifier on the MNIST dataset. This is found in `mnist.py`

## Usage

To run a sample discriminator prediction, run:

```bash
python3 fitness.py
```

To train our customizable model on the MNIST dataset, run:

```bash
python3 discriminator.py
```

Your model will be saved to `model.h5` Please note that the customizable model takes a long time to train.

If you want to quickly train a MNIST classifier model, run:

```bash
python3 mnist.py
```

Your model will be saved to `mnist.h5`.

## Datasets

All datasets should have the same format:

```
├── data
│   ├── test
│   └── train
```
