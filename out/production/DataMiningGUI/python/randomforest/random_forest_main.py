import sys
import numpy as np
import pandas as pd
import json
from random_forest_functions import random_forest_algorithm, random_forest_predictions
from helper_functions import calculate_accuracy
from decision_tree_functions import predict_example

if __name__ == "__main__":
    _type = int(sys.argv[1])
    df = pd.read_csv("D:\\Intellij_Projects\\DataMiningGUI\\src\\python\\data.csv")
    test = pd.read_csv("D:\\Intellij_Projects\\DataMiningGUI\\src\\python\\test.csv")
    df.columns = [*df.columns[:-1], 'label']
    test.columns = [*test.columns[:-1], 'label']
    if _type == 1:
        tmp = sys.argv[3]
        arr = tmp.split("/")
        forest = random_forest_algorithm(df, n_trees=int(arr[0]), n_bootstrap=int(arr[1]), n_features=int(arr[2]), dt_max_depth=int(arr[3]))
        print(forest)
    elif _type == 2:
        forest_tmp = (sys.argv[3]).replace("##"," ")
        temp = forest_tmp.replace("'", "\"")
        forest = json.loads(temp)
        row_index=int(sys.argv[2])
        predict_row = df.iloc[row_index]
        arr = []
        for i in range(len(forest)):
            prediction = predict_example(predict_row, tree=forest[i])
            arr.append(prediction)
        print(max(set(arr), key = arr.count))
    else:
        forest_tmp = (sys.argv[3]).replace("##"," ")
        temp = forest_tmp.replace("'", "\"")
        forest = json.loads(temp)
        predictions = random_forest_predictions(test, forest)
        accuracy = calculate_accuracy(predictions, test.label)
        print("Accuracy = {}".format(accuracy))