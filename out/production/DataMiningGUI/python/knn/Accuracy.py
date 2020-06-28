import os
import numpy as np
import pandas as pd
import ast
from pandas.api.types import is_string_dtype
from pandas.api.types import is_numeric_dtype
from KNNAlgorithm import KNNAlgorithm
from Utils import Utils
class Accuracy:
    
    def calAccuracyKNN(self,trainPath, testPath, k):
        tempData=pd.DataFrame(columns=['train_result','test_result'])
        test=pd.read_csv(testPath)
        totalRows=len(test.index)
        print("Tổng số dòng trong tập test: ",totalRows)
        conditionColumns=test.iloc[:,:-1]
        resultColumn=test.iloc[:,-1]
        index=0
        numberOfTrue=0
        for index, row in conditionColumns.iterrows():
            temp="{"
            for columnName in conditionColumns:
                if is_numeric_dtype(test[columnName]):  
                    temp=temp+"'"+columnName+"':"+str(row[columnName])+","
                else:
                    temp=temp+"'"+columnName+"':'"+row[columnName]+"',"
            temp=temp+"}"
            temp=temp.replace(",}","}")
            knn= KNNAlgorithm()
            # print("Convert: ",Utils.Convert(temp))
            result=knn.runKNNAlgorithm(trainPath,Utils.Convert(temp),k)
            tempData.loc[index, 'train_result']=result
            tempData.loc[index, 'test_result']=resultColumn.iloc[index]
            index=index+1
        comparison_column = np.where(tempData["train_result"] == tempData["test_result"],1,0)
        numberOfTrue=np.count_nonzero(comparison_column==1)
        return numberOfTrue/totalRows