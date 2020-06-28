from KNNAlgorithm import KNNAlgorithm
from Utils import Utils
from Point import Point
import numpy as np
import pandas as pd
from pandas.api.types import is_string_dtype
from pandas.api.types import is_numeric_dtype
from Accuracy import Accuracy
import sys

# userRow=sys.argv[1]
# k=int(sys.argv[2])
k=47
acc=Accuracy()
print("tham so k nhan duoc: ",k)
trainPath="D:\\Intellij_Projects\\DataMiningGUI\\src\\python\\data.csv"
testPath="D:\\Intellij_Projects\\DataMiningGUI\\src\\python\\test.csv"
# result=acc.calAccuracyKNN("D:\\Intellij_Projects\\DataMiningGUI\\src\\python\\data.csv","D:\\Intellij_Projects\\DataMiningGUI\\src\\python\\test.csv",k)
tempData=pd.DataFrame(columns=['train_result','test_result'])
test=pd.read_csv(testPath)
totalRows=len(test.index)
# print("Tổng số dòng trong tập test: ",totalRows)
conditionColumns=test.iloc[:,:-1]
resultColumn=test.iloc[:,-1]
index=0
numberOfTrue=0
# print("With k= ",k)  
for index, row in conditionColumns.iterrows():
    temp="{"
    for columnName in conditionColumns:
        if is_numeric_dtype(test[columnName]):  
            temp=temp+"'"+columnName+"':"+str(row[columnName])+","
        else:
            temp=temp+"'"+columnName+"':'"+row[columnName]+"',"
    temp=temp+"}"
    temp=temp.replace(",}","}")
    # print("Dòng hiện tại trong tập test: ",temp)
    knn= KNNAlgorithm()
    # print("Convert: ",Utils.Convert(temp))
    result=knn.runKNNAlgorithm(trainPath,Utils.Convert(temp),k)
    # print("Kết quả khi áp vào tập train: ",result)
    # print("Kết quả của tập test: ",resultColumn.iloc[index])
    tempData.loc[index, 'train_result']=result
    tempData.loc[index, 'test_result']=resultColumn.iloc[index]
    # print(tempData)
    # if (result==resultColumn.iloc[index]):
    #     # print("Dòng này đúng")
    #     numberOfTrue=numberOfTrue+1
        # print("Sai")
    index=index+1
comparison_column = np.where(tempData["train_result"] == tempData["test_result"],1,0)
# print(comparison_column)
numberOfTrue=np.count_nonzero(comparison_column==1)
result= numberOfTrue/totalRows
print("Accuracy is ",result)
