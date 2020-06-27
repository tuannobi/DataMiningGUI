import sys
import numpy as np
import pandas as pd
import ast
from cart_functions import decision_tree_algorithm, classify_example, calculate_accuracy

if __name__ == "__main__":
    _type = int(sys.argv[1])
    df = pd.read_csv("D:\\Intellij_Projects\\DataMiningGUI\\src\\python\\data.csv")
    test = pd.read_csv("D:\\Intellij_Projects\\DataMiningGUI\\src\\python\\test.csv")
    test.columns = [*test.columns[:-1], 'label']
    df.columns = [*df.columns[:-1], 'label']
    if _type == 1:
        depth = int(sys.argv[2])
        tree = decision_tree_algorithm(df, max_depth=depth)
        print(tree)
    elif _type == 2:
        #tmp = "{'user_vote <= 141740.5': [{'duration <= 104.5': [{'user_vote <= 53473.0': [{'budget <= 12500000.0': [{'gross <= 13157432.0': ['(6, 8]', '(4, 6]']}, '(4, 6]']}, {'gross <= 17148459.5': [{'other_actors_fb <= 2438.0': ['(6, 8]', '(4, 6]']}, '(6, 8]']}]}, {'budget <= 13250000.0': [{'user_vote <= 3158.5': [{'content = PG-13': ['(4, 6]', '(6, 8]']}, '(6, 8]']}, {'duration <= 124.5': [{'movie_fb <= 2500.0': ['(4, 6]', '(6, 8]']}, '(6, 8]']}]}]}, {'user_vote <= 551933.0': [{'year <= 1987.5': [{'user_vote <= 172611.5': [{'other_actors_fb <= 329.5': ['(8, 10]', '(6, 8]']}, {'gross <= 169735135.5': ['(8, 10]', '(6, 8]']}]}, {'user_vote <= 355995.5': ['(6, 8]', {'gross <= 63585399.0': ['(8, 10]', '(6, 8]']}]}]}, {'gross <= 207717970.0': ['(8, 10]', {'year <= 2004.5': ['(8, 10]', {'content = R': ['(6, 8]', '(8, 10]']}]}]}]}]}"
        #a = tmp.replace(" ", "##")
        #b = a.replace("##", " ")
        #tree = ast.literal_eval(b)
        tmp = (sys.argv[3]).replace("##", " ")
        row_index = int(sys.argv[2])
        tree = ast.literal_eval(tmp)
        predict_row = test.iloc[row_index]
        result = classify_example(predict_row, tree)
        print(result)
    else:
        #tmp = "{'user_vote <= 141740.5': [{'duration <= 104.5': [{'user_vote <= 53473.0': [{'budget <= 12500000.0': [{'gross <= 13157432.0': ['(6, 8]', '(4, 6]']}, '(4, 6]']}, {'gross <= 17148459.5': [{'other_actors_fb <= 2438.0': ['(6, 8]', '(4, 6]']}, '(6, 8]']}]}, {'budget <= 13250000.0': [{'user_vote <= 3158.5': [{'content = PG-13': ['(4, 6]', '(6, 8]']}, '(6, 8]']}, {'duration <= 124.5': [{'movie_fb <= 2500.0': ['(4, 6]', '(6, 8]']}, '(6, 8]']}]}]}, {'user_vote <= 551933.0': [{'year <= 1987.5': [{'user_vote <= 172611.5': [{'other_actors_fb <= 329.5': ['(8, 10]', '(6, 8]']}, {'gross <= 169735135.5': ['(8, 10]', '(6, 8]']}]}, {'user_vote <= 355995.5': ['(6, 8]', {'gross <= 63585399.0': ['(8, 10]', '(6, 8]']}]}]}, {'gross <= 207717970.0': ['(8, 10]', {'year <= 2004.5': ['(8, 10]', {'content = R': ['(6, 8]', '(8, 10]']}]}]}]}]}"
        tmp = (sys.argv[3]).replace("##", " ")
        tree = ast.literal_eval(tmp)
        accuracy = calculate_accuracy(test, tree)
        print(accuracy)
    