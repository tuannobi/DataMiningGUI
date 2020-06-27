import sys
import numpy as np
import pandas as pd
import ast
from cart_functions import calculate_accuracy

if __name__ == "__main__":
    tmp = "{'user_vote <= 141740.5': [{'duration <= 104.5': [{'user_vote <= 53473.0': [{'budget <= 12500000.0': [{'gross <= 13157432.0': ['(6, 8]', '(4, 6]']}, '(4, 6]']}, {'gross <= 17148459.5': [{'other_actors_fb <= 2438.0': ['(6, 8]', '(4, 6]']}, '(6, 8]']}]}, {'budget <= 13250000.0': [{'user_vote <= 3158.5': [{'content = PG-13': ['(4, 6]', '(6, 8]']}, '(6, 8]']}, {'duration <= 124.5': [{'movie_fb <= 2500.0': ['(4, 6]', '(6, 8]']}, '(6, 8]']}]}]}, {'user_vote <= 551933.0': [{'year <= 1987.5': [{'user_vote <= 172611.5': [{'other_actors_fb <= 329.5': ['(8, 10]', '(6, 8]']}, {'gross <= 169735135.5': ['(8, 10]', '(6, 8]']}]}, {'user_vote <= 355995.5': ['(6, 8]', {'gross <= 63585399.0': ['(8, 10]', '(6, 8]']}]}]}, {'gross <= 207717970.0': ['(8, 10]', {'year <= 2004.5': ['(8, 10]', {'content = R': ['(6, 8]', '(8, 10]']}]}]}]}]}"
    tree = ast.literal_eval(tmp)
    testing_df = pd.read_csv("D:/data/Data Mining/DataMiningAlgorithms/DataMiningAlgorithms/testing_set.csv")
    testing_df.columns = [*testing_df.columns[:-1], 'label']
    accuracy = calculate_accuracy(testing_df, tree)
    print(accuracy)