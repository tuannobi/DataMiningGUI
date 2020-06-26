import math
from Point import Point
from sklearn.model_selection import train_test_split
import os
import numpy as np
import pandas as pd
import ast
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

    # @staticmethod
    # def SplitIntoTrainingAndTestingFile(datasetPath,savePath):
    #     data=pd.read_csv(datasetPath)    
    #     from sklearn.model_selection import train_test_split
    #     X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.33, random_state=42)
