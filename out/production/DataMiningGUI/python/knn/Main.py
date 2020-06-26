from KNNAlgorithm import KNNAlgorithm
from Utils import Utils
from Point import Point
import pandas as pd
from pandas.api.types import is_string_dtype
from pandas.api.types import is_numeric_dtype
import sys
# knn= KNNAlgorithm()
# userRow=({'budget':17000000,'gross':2315683,'user_vote':4081,'critic_review_ratio':0.75,'movie_fb':154.00,'director_fb':3,'actor1_fb':400,'other_actors_fb':354,'duration':97,'face_number':2.388288,'year':1986,'country':'USA','content':'PG-13','imdb_score':3})
# knn.runKNNAlgorithm("D:\\Python\\DataMiningAlgorithms\\DataMiningAlgorithms\\data.csv",userRow)
# knn.testAccuracy(2,"D:\\Python\\DataMiningAlgorithms\\DataMiningAlgorithms\\test.csv")

# test=pd.read_csv("D:\\Python\\DataMiningAlgorithms\\DataMiningAlgorithms\\test.csv")
# conditionColumns=test.iloc[:,:-1]
# resultColumn=test.iloc[:,-1]
# for k in range(1,20):
#     index=0;
#     numberOfTrue=0;
#     print("With k= ",k)  
#     for index, row in conditionColumns.iterrows():
#         temp="{"
#         for columnName in conditionColumns:
#             if is_numeric_dtype(test[columnName]):  
#                 temp=temp+"'"+columnName+"':"+str(row[columnName])+","
#             else:
#                 temp=temp+"'"+columnName+"':'"+row[columnName]+"',"
#         temp=temp+"}"
#         temp=temp.replace(",}","}")
#         # print(temp)
#         knn= KNNAlgorithm()
#         # print("Ban đầu: ",temp)
#         # print("Convert: ",Utils.Convert(temp))
#         # userRow=({'budget':17000000,'gross':2315683,'user_vote':4081,'critic_review_ratio':0.75,'movie_fb':154.00,'director_fb':3,'actor1_fb':400,'other_actors_fb':354,'duration':97,'face_number':2.388288,'year':1986,'country':'USA','content':'PG-13','imdb_score':3})
#         result=knn.runKNNAlgorithm("D:\\Python\\DataMiningAlgorithms\\DataMiningAlgorithms\\train.csv",Utils.Convert(temp),k)
#         # print("Result is ",result)
#         # print("Test result is ",resultColumn.iloc[index])
#         if (result==resultColumn.iloc[index]):
#             # print("Đúng")
#             numberOfTrue=numberOfTrue+1
#             # print("Sai")
#         index=index+1
#     # print("Tổng số dòng đúng: ",numberOfTrue)
#     print("Tỉ lệ: ",numberOfTrue/12)

# for i in range(10):
#     knn= KNNAlgorithm()
#     userRow=({'budget':17000000,'gross':2315683,'user_vote':4081,'critic_review_ratio':0.75,'movie_fb':154.00,'director_fb':3,'actor1_fb':400,'other_actors_fb':354,'duration':97,'face_number':2.388288,'year':1986,'country':'USA','content':'PG-13','imdb_score':3})
#     knn.runKNNAlgorithm("D:\\Python\\DataMiningAlgorithms\\DataMiningAlgorithms\\data.csv",userRow,5)
print ("This is the name of the script: ", sys.argv[0])
print ("Number of arguments: ", len(sys.argv))
print ("The arguments are: " , str(sys.argv))
knn= KNNAlgorithm()
# userRow={'budget':17000000,'gross':2315683,'user_vote':4081,'critic_review_ratio':0.75,'movie_fb':154.00,'director_fb':3,'actor1_fb':400,'other_actors_fb':354,'duration':97,'face_number':2.388288,'year':1986,'country':'UK','content':'PG-13','imdb_score':3}
# print(type(userRow))
# # print("Convert: ",Utils.Convert(userRow))
# print(type(Utils.Convert(userRow)))
# print(Utils.Convert(userRow))
# temp={'duration <= 104.5': [{'user_vote <= 53473.0': [{'budget <= 12500000.0': [{'gross <= 13157432.0': ['(6, 8]','(4, 6]']},'(4, 6]']},{'gross <= 17148459.5': [{'other_actors_fb <= 2438.0': ['(6, 8]','(4, 6]']},'(6, 8]']}]},{'budget <= 13250000.0': [{'user_vote <= 3158.5': [{'content = PG-13': ['(4, 6]','(6, 8]']},'(6, 8]']},{'duration <= 124.5': [{'movie_fb <= 2500.0': ['(4, 6]', '(6, 8]']},'(6, 8]']}]}]}
# temp=str({"petal_width <= 0.8": ["Iris-setosa", {"petal_width <= 1.65": [{"petal_length <= 4.9": ["Iris-versicolor", "Iris-virginica"]}, "Iris-virginica"]}]})
# print(type(temp))
# print(Utils.Convert(temp))
# print(type(Utils.Convert(temp)))
# print("hello world")
userRow=Utils.Convert(sys.argv[1])
isIndex=int(sys.argv[2])
result = knn.runKNNAlgorithm("D:\\Intellij_Projects\\DataMiningGUI\\src\\python\\data.csv",userRow,isIndex)
print("Prediction is ",result)
# knnn= KNNAlgorithm()
# userRow1=({'budget':17000000,'gross':2315683,'user_vote':4081,'critic_review_ratio':0.75,'movie_fb':154.00,'director_fb':3,'actor1_fb':400,'other_actors_fb':354,'duration':97,'face_number':2.388288,'year':1986,'country':'UK','content':'PG-13','imdb_score':3})
# knnn.runKNNAlgorithm("D:\\Intellij_Projects\\DataMiningGUI\\src\\python\\knn\\data.csv",userRow1,5)