class Point:
    # thuộc tính
    __dimention=2
    __list=[]
    def __init__(self, list=[0,0]):
        self.__dimention=len(list)
        self.__list=list
    # phương thức
    def setDimension(self, dimention):
        self.__dimention = dimention
    
    def getDimension(self):
        return self.__dimention
    
    def setList(self, list):
        self.__list = list
    
    def getList(self):
        return self.__list