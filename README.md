# Deep Evolutionary Image Generation

## About

We're creating an efficient GAN-alternative technique that leverages evolution for image generation. We are evolving a population of images by using a discriminator output as their "fitness" and updating them with random mutation/recombination at each generational step.

## Teams

1. Random image creation with Clojure (Caroline)

2. Editing images with Clojure (Ben) (maybe the [imagez](https://github.com/mikera/imagez) library can help!)

3. Researching what best criteria of repopulation we want to use (and writing it!) (i.e. elitism, recombination, mutation, etc) (Sunghoon)

4. Writing the discriminator and the fitness API (Camilo)

5. Use of Python functions in a Clojure script (Sergei)

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

If you want to push your changes to GitHub, you do:

```bash
git add .
git commit -m "your message"
git push
```

For simple merging you want to do:
```bash
# In the merge message do:
# ESC
# :wq ENTER
git push
```

## Installation

You'll need Git Large File Storage. [Install](https://git-lfs.github.com/) it.

Then, run

```bash
git lfs install
```

## Notes

Notes people take on their progress should be put in the `/text` directory.

## Datasets

If you find cool image datasets, please feel free to put them in the `/datasets` directory!

## Communication

We have a [Slack channel](https://join.slack.com/t/ec-networks/shared_invite/zt-d2zlhyvq-0nHuia~~UffdUTl8EGBUGg).

## Future goals

Use an SVM discriminator and compare the output with a neural net discriminator.
