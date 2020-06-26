from Point import Point
import pandas as pd
from pandas.api.types import is_string_dtype
from pandas.api.types import is_numeric_dtype
import numpy as np
import math
from Utils import Utils
class KNNAlgorithm:
    
    def __init__(self):
        self.__train=pd.DataFrame()
        self.__test=pd.DataFrame()
        self.__conditionColumns=[]
        self.__quantitativeColumns=[] #Cột định lượng
        self.__quanlitativeColumns=[] #Cột định tính
        self.__resultColumn=""
        self.__normalizedConditionColumns=[]#Tên những cột điều kiện sau khi đã được normalize
        self.__userRow=[]
        self.__k=0

    def setK(self,k):
        self.__k = k
    
    def getK(self):
        return self.__k             

    def __getColumnsList(self):
        return self.__train.columns.tolist()

    def __getTopSmallestValue(self,columnName,topNumber):
        return self.__train.nsmallest(topNumber,[columnName])

    def __groupAndGetPrediction(self,dataFrameByK):
        result=dataFrameByK.groupby([self.__resultColumn]).size().reset_index(name='counts')
        result=result.sort_values(by=['counts'],ascending=False)
        # result.to_csv('D:\\Python\\DataMiningAlgorithms\\DataMiningAlgorithms\\temp.csv',index=False)
        return result.iloc[0][self.__resultColumn] #Lấy vị trí dòng đầu tiên ở ô imdb_binned

    def __classifyColumn(self):
        self.__conditionColumns=self.__train.columns.tolist()
        self.__resultColumn=self.__conditionColumns.pop()
        for (columnName, columnData) in self.__train[self.__conditionColumns].iteritems():
            if is_numeric_dtype(self.__train[columnName]):
                self.__quantitativeColumns.append(columnName)
            else:
                self.__quanlitativeColumns.append(columnName)

    def __minMaxNormalization(self):
        for (columnName, columnData) in self.__train[self.__quantitativeColumns].iteritems():
            max=self.__train[columnName].max()
            min=self.__train[columnName].min()
            self.__train[columnName] = self.__train[columnName].apply(lambda x : (x-min)/(max-min))

    def __numpy_where(self,columnName, value):
        return self.__train.assign(new_column=np.where(self.__train[columnName]==value, 1, 0))
    
    def __quanlitativeColumnsNormalization(self):
        for (columnName, columnData) in self.__train[self.__quanlitativeColumns].iteritems(): #Lặp từng column
            columnNameList=self.__train[columnName].unique()
            for item in columnNameList: #Lặp từng phần tử trong list các giá trị unique của cột
                self.__train=self.__numpy_where(columnName,item)
                self.__train.rename(columns={'new_column':columnName+"_"+item},inplace=True)
            del self.__train[columnName]
        # print(self.numpy_where('country','UK'))
        # self.__train.to_csv('D:\\Python\\DataMiningAlgorithms\\DataMiningAlgorithms\\01Normalization.csv',index=False)
    # def __qualitativeNormalization(self)

    def __normalization(self,userRow):
        self.__train=self.__train.append(userRow, ignore_index=True)
        self.__minMaxNormalization()
        self.__quanlitativeColumnsNormalization()
        self.__userRow=pd.concat([self.__train.tail(1)]).values.flatten().tolist()
        self.__userRow = [item for item in self.__userRow if str(item) != 'nan'] #Xóa nan trong chuỗi chưa hiểu dòng này
        # print('Dòng cuối cùng sau khi chuẩn hóa: ', self.__userRow)
        #drop the last row
        self.__train.drop(self.__train.tail(1).index,inplace=True)
        self.__normalizedConditionColumns=self.__train.columns.tolist()
        self.__normalizedConditionColumns.remove(self.__resultColumn)

    # def testAccuracy(self,k,testfilePath):
    #     self.__test=pd.read_csv(testfilePath)
    #     conditionColumns=self.__test.iloc[:,:-1]
    #     resultColumn=self.__test.iloc[:,-1]         
    #     for index, row in conditionColumns.iterrows():
    #         temp="({"
    #         for columnName in conditionColumns:
    #             if is_numeric_dtype(self.__test[columnName]):  
    #                 temp=temp+"'"+columnName+"':"+str(row[columnName])+","
    #             else:
    #                 temp=temp+"'"+columnName+"':'"+row[columnName]+"',"
    #         temp=temp+"})"
    #         temp=temp.replace(",})","})")
    #         abc_series = pd.Series(temp)
    #         print(abc_series)
            # knn= KNNAlgorithm()
            # userRow=({'budget':17000000,'gross':2315683,'user_vote':4081,'critic_review_ratio':0.75,'movie_fb':154.00,'director_fb':3,'actor1_fb':400,'other_actors_fb':354,'duration':97,'face_number':2.388288,'year':1986,'country':'USA','content':'PG-13','imdb_score':3})
            # knn.runKNNAlgorithm("D:\\Python\\DataMiningAlgorithms\\DataMiningAlgorithms\\data.csv",userRow)

    #Người dùng có thể nhập vào đường dẫn hoặc DataFrame    
    def runKNNAlgorithm(self,filePath,userRow,isIndex):
        self.__train=pd.read_csv(filePath)
        if(isIndex==1):
            self.__train.drop(self.__train.columns[0],axis=1,inplace=True)
        self.__k=len(self.__train.index)
        print(int(math.sqrt(self.__k)))
        # self.__test=pd.read_csv(testfilePath)
        self.__classifyColumn()
        # print('Cột điều kiện: ',self.__conditionColumns)
        # print('Cột kết quả: ',self.__resultColumn)
        # print('Cột định lượng: ',self.__quantitativeColumns)
        # print('Cột định tính: ',self.__quanlitativeColumns)
        self.__normalization(userRow)
        # print("Các cột điều kiện còn lại sau khi chuẩn hóa: ",self.__normalizedConditionColumns)


        # tempInput=int(input("Input k= "))
        # if tempInput<=0:
        #     self.__k=int(math.sqrt(self.__train.shape[0])) #Lấy mặc định chỉ số k
        # else:
        #     self.__k=tempInput

        # fileName=Utils.getFileNameFromPath(filePath)
        # print(fileName)
    
        userPoint=Point(self.__userRow) #Cố định
        # print("Điểm cố định",userPoint.getList())
        euclideanDistanceColumn=[] #List chứa các giá trị khoảng cách
        # print("Danh sách cột: ",list(self.__train))
        for index,row in self.__train[self.__normalizedConditionColumns].iterrows(): #Duyệt từng dòng trong dataset
            tempRow=[]
            for column in self.__normalizedConditionColumns: #Duyệt từng ô trong dòng
                tempRow.append(row[column])
            tempPoint=Point(tempRow)
            euclideanDistanceColumn.append(Utils.calEuclideanDistance(tempPoint,userPoint))
        self.__train['euclidean_distance']=euclideanDistanceColumn #Đưa list dữ liệu vào __train
        # self.__train.to_csv('D:\\Python\\DataMiningAlgorithms\\DataMiningAlgorithms\\result.csv',index=False)
        ###################TEST TỪNG TRƯỜNG HỢP VỚI 1 TO MAX_K
        # accuracyDataFrame=pd.DataFrame(columns=['k','accuracy'])
        tempDataFrame=self.__getTopSmallestValue('euclidean_distance',self.__k)
        return self.__groupAndGetPrediction(tempDataFrame)
