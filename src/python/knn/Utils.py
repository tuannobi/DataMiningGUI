import math
from Point import Point
from sklearn.model_selection import train_test_split
import os
import numpy as np
import pandas as pd
import ast
from pandas.api.types import is_string_dtype
from pandas.api.types import is_numeric_dtype
class Utils:

    @staticmethod
    def Convert(lst): 
        return ast.literal_eval(lst) 

    @staticmethod
    def getFileNameFromPath(path):
        return os.path.basename(path)
    
    @staticmethod
    def calEuclideanDistance(A=Point(),B=Point()):
        sum=0
        for i in range(A.getDimension()):
            sum=sum+math.pow(B.getList()[i]- A.getList()[i],2)
        result=math.sqrt(sum)
        return result  

    @staticmethod
    def train_test_split(dataPath):
        df = pd.read_csv(dataPath)
        df['split'] = np.random.randn(df.shape[0], 1)
        msk = np.random.rand(len(df)) <= 0.7
        train = df[msk]
        test = df[~msk]
        print(train)
        print(test)
        train.to_csv("C:\\Users\\Tuan\\Desktop\\TEst\\train.csv",index="False")
        test.to_csv("C:\\Users\\Tuan\\Desktop\\TEst\\test.csv",index="False")