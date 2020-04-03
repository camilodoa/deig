from discriminator import Discriminator

class Fitness():
    def __init__(self, dataset='../datasets/mnist/'):
        self.d = Discriminator(dataset)
        self.d.train()

    def evaluate(self, img):
        predictions = self.d.predict()

        fitness = 0

        for prediction in predictions:
            # Each prediction is a value from 0 to 1
            # 1 means more confidence 
            fitness += (1 / prediction) * 10

        return fitness
