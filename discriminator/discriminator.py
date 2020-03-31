import tensorflow as tf
from tensorflow import keras
import numpy as np
import os
import random
import shutil
from keras.models import Sequential
from keras.layers import Dense, Activation, Flatten, Reshape
from keras.layers import Conv2D, Conv2DTranspose, UpSampling2D
from keras.layers import LeakyReLU, Dropout
from keras.layers import BatchNormalization
from keras.optimizers import Adam, RMSprop

class Discriminator:
    def __init__(self, dataset='../datasets/africa_fabric'):
        self.network = None
        self.model = None
        self.trained = None
        self.cleaned = False
        self.clean(dataset)

        '''
        Dataset should be -> REAL_IMG : 0 && FAKE_IMG : 1
        '''

    def clean(self, dataset):
        if self.cleaned: return True

        paths = {
        'train' : './data/train',
        'test' : './data/test'
        }

        for path in paths.values():
            shutil.rmtree(path)
            os.makedirs(path)

        delegations = ['train'] * 9 + ['test'] * 1

        for file in os.listdir(dataset):
            selected = random.choice(delegations)

            destination = shutil.copyfile(dataset + '/' + file, paths[selected] + '/' + file)

        print("Seperated dataset into test and train.")
        self.cleaned = True
        return True

    def network(self, img_rows=64, img_cols=64, channel=3):
        if self.network: return self.network
        self.network = Sequential()
        depth = 64
        dropout = 0.4

        input_shape = (img_rows, img_cols, channel)
        self.network.add(Conv2D(depth*1, 5, strides=2, input_shape=input_shape, padding='same'))
        self.network.add(LeakyReLU(alpha=0.2))
        self.network.add(Dropout(dropout))

        self.network.add(Conv2D(depth*2, 5, strides=2, padding='same'))
        self.network.add(LeakyReLU(alpha=0.2))
        self.network.add(Dropout(dropout))

        self.network.add(Conv2D(depth*4, 5, strides=2, padding='same'))
        self.network.add(LeakyReLU(alpha=0.2))
        self.network.add(Dropout(dropout))

        self.network.add(Conv2D(depth*8, 5, strides=1, padding='same'))
        self.network.add(LeakyReLU(alpha=0.2))
        self.network.add(Dropout(dropout))

        # Out: 1-dim probability
        self.network.add(Flatten())
        self.network.add(Dense(1))
        self.network.add(Activation('sigmoid'))
        self.network.summary()
        return self.network

    def model(self):
        if self.model: return self.model
        optimizer = RMSprop(lr=0.0002, decay=6e-8)
        self.model = Sequential()
        self.model.add(self.network())
        self.model.compile(loss='binary_crossentropy', optimizer=optimizer, metrics=['accuracy'])
        return self.model

    def train(self, train_steps=2000, batch_size=256, save_interval=0):
        history = self.model().fit()

if __name__ == '__main__':
    d = Discriminator()
