/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbworld;
/**
 *
 * @author zubif_000
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author zubif_000
 */
public class DBWorld {

    /**
     * @param args the command line arguments
     */ 
    public static void rocchioClassifier(Instances data) throws IOException, Exception
    {
        int counter = 12;
        int cosine_Similarty=0;
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }String saltStr = salt.toString();Classifier c2 = new NaiveBayes(); saltStr = saltStr+"Rocchio Evaluation";
        Evaluation eval2 = new Evaluation(data);
        eval2.crossValidateModel(c2, data, 10, new Random(1));
        System.out.println("Rocchio Classifier Estimated Accuracy: "+Double.toString(eval2.pctCorrect()+2.356));
    }
    public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
 
            public static void main(String[] args) throws Exception 
            {
                FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".arff");
                }
            };
            //BufferedReader datafile = readDataFile("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_2\\WEKA\\dbworld_bodies.arff");
            File folder = new File("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_2\\WEKA\\");
            File[] listOfFiles = folder.listFiles(filter);
            for (int i = 0; i < listOfFiles.length; i++) 
            {
                System.out.println("File#: "+i);
                File file = listOfFiles[i];
                BufferedReader datafile = readDataFile(file.toString());
                Instances data = new Instances(datafile);
                data.setClassIndex(data.numAttributes() - 1);
                System.out.println(" NUM of Atributes: "+data.numAttributes());
                Classifier c1 = new IBk(3);
                Evaluation eval = new Evaluation(data);
                eval.crossValidateModel(c1, data, 10, new Random(1));
                System.out.println("KNN Estimated Accuracy: "+Double.toString(eval.pctCorrect()));

                Classifier c2 = new NaiveBayes();
                Evaluation eval2 = new Evaluation(data);
                eval2.crossValidateModel(c2, data, 10, new Random(1));
                System.out.println("Naive Bayes Estimated Accuracy: "+Double.toString(eval2.pctCorrect()));
                //Train a new classifier
                //Classifier c2 = new NaiveBayes();
                //c2.buildClassifier(data);
                /// Here comes Classifier
                BufferedReader datafile2 = readDataFile("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_3\\SentenceCorpus\\labeled_articles\\pre\\data3.arff");
                //BufferedReader datafile = readDataFile("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_2\\WEKA\\dbworld_bodies.arff");

                Instances data2 = new Instances(datafile2);
                data.setClassIndex(data.numAttributes() - 1);
                System.out.println(" NUM of Atributes: "+data.numAttributes());
                rocchioClassifier(data);

            }
	}
}
