# Utility
import numpy as np
import os
import random
import shutil
from pathlib import Path

# TF
import tensorflow as tf

from keras_preprocessing import image
from keras_preprocessing.image import ImageDataGenerator
from tensorflow.keras.initializers import glorot_uniform
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Activation, Flatten, Reshape
from tensorflow.keras.layers import Conv2D, Conv2DTranspose, UpSampling2D
from tensorflow.keras.layers import LeakyReLU, Dropout
from tensorflow.keras.layers import BatchNormalization
from tensorflow.keras.optimizers import Adam, RMSprop
from tensorflow.keras.models import load_model
from tensorflow.keras.utils import CustomObjectScope

# Display
import matplotlib.pyplot as plt


class Discriminator:
    def __init__(self, dataset=Path('../datasets/mnist/')):
        self.dataset = dataset
        self.model = None
        self.trained = False
        self.categories = 0

        # Count categories from dataset
        for _, dirnames, filenames in os.walk(self.dataset / 'train'):
            self.categories += len(dirnames)

    def train(self):
        # Dataset
        TRAINING_DIR = self.dataset + './train/'
        training_datagen = ImageDataGenerator(
        	rotation_range=40,
            width_shift_range=0.2,
            height_shift_range=0.2,
            shear_range=0.2,
            zoom_range=0.2,
            horizontal_flip=True,
            fill_mode='nearest')

        TESTING_DIR = self.dataset + './test/'
        testing_datagen = ImageDataGenerator(
            rotation_range=40,
            width_shift_range=0.2,
            height_shift_range=0.2,
            shear_range=0.2,
            zoom_range=0.2,
            horizontal_flip=True,
            fill_mode='nearest')

        training_generator = training_datagen.flow_from_directory(
        	TRAINING_DIR,
        	target_size=(256, 256),
        	class_mode='categorical'
        )

        testing_generator = testing_datagen.flow_from_directory(
        	TESTING_DIR,
        	target_size=(256, 256),
        	class_mode='categorical'
        )

        # Model
        depth = 64
        dropout = 0.4
        optimizer = RMSprop(lr=0.0002, decay=6e-8)

        self.model = Sequential([
            # Note the input shape is 256x256 with 3 color chanels
            # All images are automatically resized to this
            # This is the first convolution
            Conv2D(
                depth*1,
                5,
                strides=2,
                padding='same',
                input_shape=(256, 256, 3),
                activation='relu'
            ),

            LeakyReLU(alpha=0.2),
            Dropout(dropout),

            # The second convolution
            Conv2D(
                depth*2,
                5,
                strides=2,
                padding='same',
                activation='relu'
            ),
            LeakyReLU(alpha=0.2),
            Dropout(dropout),

            # The third convolution
            Conv2D(
                depth*4,
                5,
                strides=2,
                padding='same',
                activation='relu'
            ),
            LeakyReLU(alpha=0.2),
            Dropout(dropout),

            # The fourth convolution
            Conv2D(
                depth*8,
                5,
                strides=1,
                padding='same',
                activation='relu'
            ),
            LeakyReLU(alpha=0.2),
            Dropout(dropout),

            # Flatten the results to feed into a DNN
            Flatten(),
            Dropout(0.5),

            # 512 neuron hidden layer
            Dense(512, activation='relu'),

            # Output is same size as categories
            Dense(self.categories, activation='softmax')
        ])

        self.model.summary()

        self.model.compile(
            loss='categorical_crossentropy',
            optimizer=optimizer,
            metrics=['accuracy'])

        history = self.model.fit_generator(
            generator=training_generator,
            # steps_per_epoch=10000, # Not until we get a cloud service
            epochs=25,
            validation_data=testing_generator,
            verbose=1)

        self.model.save('./model.h5')

        self.trained = True

        acc = history.history['accuracy']
        val_acc = history.history['val_acc']
        loss = history.history['loss']
        val_loss = history.history['val_loss']

        epochs = range(len(acc))

        plt.plot(epochs, acc, 'r', label='Training accuracy')
        plt.plot(epochs, val_acc, 'b', label='Testing accuracy')
        plt.title('Training and testing accuracy')
        plt.legend(loc=0)
        plt.figure()
        plt.show()

if __name__ == '__main__':
    'Sample usage'
    d = Discriminator()
    d.train()
