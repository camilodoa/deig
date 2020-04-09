from discriminator import Discriminator

class Fitness():
    def __init__(self, dataset='../datasets/mnist/'):
        self.d = Discriminator(dataset)
        self.d.train()

    def evaluate(self, img):
        '''
        How the list of predictions gets transformed to a fitness number
        '''

        # Each prediction is a value from 0 to 1. 1 means that the image is
        # more accurate, so we're taking the inverse, normalizing so that
        # it starts at 0 (this is the -1 part) and multiplying by
        # the error coefficient

        predictions = self.d.predict()

        error_coefficient = 5

        fitness = ((1 / max(predictions)) - 1) * error_coefficient

        return fitness
