package test;
import python.bayes.Bayes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Apriori {
    public static void main(String[] args) throws IOException {
        Bayes bayes=new Bayes();
        bayes.runAlgorithm(new ArrayList<>(Arrays.asList("Nắng ","Nóng ")));
    }
}
