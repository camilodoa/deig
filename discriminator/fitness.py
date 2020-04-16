# Utility imports for the example
from discriminator import Discriminator
import os
import random

# Imports our Clojure code will have to use
import numpy as np

from keras_preprocessing import image
from tensorflow.keras.models import load_model


def fitness(img):
    '''
    Our fitness function to be copied to Clojure
    '''
    x = image.img_to_array(img)
    x = np.expand_dims(x, axis=0)

    model = load_model('model.h5', compile=False)

    # This prediction is not used in fitness calculation
    prediction = model.predict_classes(x)
    print('Prediction is ', prediction)

    probabilities = model.predict_proba(x)[0]
    print('Probabilities are', probabilities)

    error = ((1.0 / max(probabilities)) - 1) * 100
    print('Error is', error)
    return error


if __name__ == '__main__':
    'Sample usage'
    # Find a random image from dataset
    d = Discriminator()
    for _, dirnames, filenames in os.walk(d.dataset / 'test'):
        example = random.choice(filenames)

        # Load file from HD
        img = image.load_img(d.dataset / 'test' / example,
                            target_size=(28, 28), color_mode="grayscale")

        # Pass image to our fitness function
        fitness(img)
