import numpy as np
import pandas as pd

import matplotlib.pyplot as plt
import seaborn as sns

import random
from pprint import pprint
import sys

def calculate_entropy(data):
    
    label_column = data[:, -1]
    _, counts = np.unique(label_column, return_counts=True)

    probabilities = counts / counts.sum()
    entropy = sum(probabilities * -np.log2(probabilities))
     
    return entropy

def calculate_entropy_attribute(df, attribute):
    variables = df[attribute].unique()
    entropy_attribute = 0
    for variable in variables:
        new_df = df[df[attribute] == variable]
        entropy = calculate_entropy(new_df.values)
        entropy_attribute += (len(new_df)/len(df))*entropy
    return entropy_attribute

def find_winner(df):
    IG = []
    for key in df.keys()[:-1]:
        IG.append(calculate_entropy(df.values) - calculate_entropy_attribute(df, key))
    return df.keys()[:-1][np.argmax(IG)]

def generate_subtable(df, node, value):
    return df[df[node] == value].reset_index(drop = True)

def id3_algorithm(df, tree = None):
    node = find_winner(df)
    attributeValues = np.unique(df[node])
    
    if tree is None:
        tree = {}
        tree[node] = {}

    for value in attributeValues:
        subtable = generate_subtable(df, node, value)
        classifying_values, counts = np.unique(subtable.values[:, -1], return_counts=True)

        if len(counts) == 1:
            tree[node][value] = classifying_values[0]
        else:
            tree[node][value] = id3_algorithm(subtable)

    return tree

if __name__ == "__main__":
    df = pd.read_csv("D:\\Intellij_Projects\\DataMiningGUI\\src\\python\\data.csv")
    tree = id3_algorithm(df)
    print(tree)