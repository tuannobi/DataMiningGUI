from KNNAlgorithm import KNNAlgorithm
from Utils import Utils
from Point import Point
import pandas as pd
from pandas.api.types import is_string_dtype
from pandas.api.types import is_numeric_dtype
from Accuracy import Accuracy
import sys

knn= KNNAlgorithm()
userRow=Utils.Convert(sys.argv[1])
k=int(sys.argv[2])
# k=49
# userRow={'budget':8000000.0,'gross':5.9889948E7,'user_vote':355810.0,'critic_review_ratio':0.303712036,'movie_fb':15000.0,'director_fb':36.0,'actor1_fb':7000.0,'other_actors_fb':177.0,'duration':101.0,'face_number':1.0,'year':2006.0,'country':'USA','content':'R'}
result=knn.runKNNAlgorithm("D:\\Intellij_Projects\\DataMiningGUI\\src\\python\\data.csv",userRow,k)
print("Prediction is ",result)