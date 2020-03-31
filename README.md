# Networks

## About

We're creating a more efficient GAN-alternative technique that leverages evolution as an adversarial process. We are evolving a population of images by using a discriminator output as their "fitness" and updating them with random mutation/recombination at each generational step.

## Future goals

Use an SVM discriminator and compare the output with a neural net discriminator.

## Teams

We need to divide people up to take care of the following items:

1. Random image creation with Clojure (Caroline)

2. Editing images with Clojure (Ben)

3. Researching what best criteria of repopulation we want to use (and writing it!) (i.e. elitism, recombination, mutation, etc) (Sunghoon)

4. Writing the discriminator (Camilo)

  - And testing the discriminator with MNIST Dataset

5. Using Python functions in a Clojure script (Sergei)

6. Writing/sketching out the skeleton of our evolutionary algorithm with pretend APIs (Mahran)

## Getting started with git

First, install [git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) so you can access it in your shell (alternatively you can use a git-helper application like [GitHub Desktop](https://desktop.github.com/)). If you have a Mac, I would recommend installing [Homebrew](https://brew.sh/), and then running

```bash
brew install git
```

Homebrew takes care of your paths and is overall super convenient.

Then, navigate to the directory where you would like to place this project folder. Once you've done this, run

```bash
git clone https://github.com/camilodoa/EC-Networks.git
```

to copy the repo locally.

If you want to add something to git, you do:

```bash
git add .
git commit -m "your message"
git push
```

For simple merging you want to do:
```bash
# In the merge message do:
# ESC
# :wq
git push
```

## To Do

- Pick a name for our project (GEN? DEIG?)

- Read up on Neural Networks

- Find a core/cloud computing service (Ask Prof. Spector)

- Pick a dataset (We're thinking of starting with MNIST)

- Look into frameworks for ML in Clojure / Porting ML into Clojure

- Research classical [GANS](https://github.com/NVlabs/stylegan)

- Look into using different discriminators

  - Results can inform what the classifiers are doing

- ~Set up GitHub accounts~

- ~Join [Slack channel](https://join.slack.com/t/ec-networks/shared_invite/zt-d2zlhyvq-0nHuia~~UffdUTl8EGBUGg)~

## Notes

Notes people take on research/frameworks should be put in the `/text` directory.

## Datasets

If you find cool image datasets, please feel free to put them in the `/datasets` directory!
