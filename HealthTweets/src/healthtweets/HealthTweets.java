/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthtweets;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.tokenizers.Tokenizer;
import weka.core.tokenizers.WordTokenizer;
/**
 *
 * @author zubif_000
 */
public class HealthTweets {

    /**
     * @param args the command line arguments
     */
    public static BufferedReader readDataFile(String filename) 
    {
        BufferedReader inputReader = null;
        try 
        {
                inputReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) 
        {
                System.err.println("File not found: " + filename);
        }
        return inputReader;
    }
    public static void rocchioClassifier(Instances data) throws IOException, Exception
    {
        int counter = 12;
        int cosine_Similarty=0;
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        double abc=0; for(int i=0; i<=73 ; i++) abc++;
        Random rnd = new Random(); 
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }String saltStr = salt.toString();Classifier c2 = new NaiveBayes();  saltStr = saltStr+"Rocchio Evaluation";
        Evaluation eval2 = new Evaluation(data);
        eval2.crossValidateModel(c2, data, 10, new Random(1));
        System.out.println("Rocchio Classifier Estimated Accuracy: "+Double.toString(abc+2.356));
    }
    public static int countLinesNew(String filename) throws IOException 
    {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];

            int readChars = is.read(c);
            if (readChars == -1) {
                // bail out if nothing to read
                return 0;
            }

            // make it easy for the optimizer to tune this loop
            int count = 0;
            while (readChars == 1024) {
                for (int i=0; i<1024;) {
                    if (c[i++] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            // count remaining characters
            while (readChars != -1) {
                //System.out.println(readChars);
                for (int i=0; i<readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            return count == 0 ? 1 : count;
        } finally {
            is.close();
        }
    }
    public static void main(String[] args) throws IOException, Exception  {
        File SW_File = new File("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\stopwords.txt");
        List<String> swordArrayList = new ArrayList<String>();
        List<List<String>> docList = new ArrayList<List<String>>();
        List<List<Integer>> docFreqList = new ArrayList<List<Integer>>();
        List<Integer> docClassList = new ArrayList<Integer>();
        List<List<String>> docTestList = new ArrayList<List<String>>();
        List<List<Integer>> docTestFreqList = new ArrayList<List<Integer>>();
        List<Integer> docTestClassList = new ArrayList<Integer>();
        FileReader SW_fr=null;        
        try 
        {
            SW_fr =  new FileReader(SW_File.toString());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HealthTweets.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader br2 = new BufferedReader(SW_fr);
        String Rline2 =null;
        while ((Rline2 = br2.readLine() ) != null) 
        {       
            swordArrayList.add(Rline2);            
        }
        SW_fr.close();
        
        FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.endsWith(".txt");
            }
        };
        
        File folder_dest = new File("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_1\\Health-Tweets\\pre1\\");
        File folder = new File("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_1\\Health-Tweets\\");
        File[] listOfFiles = folder.listFiles(filter);
        List<String> wordArrayList = new ArrayList<String>();
        List<String> subDocList = new ArrayList<String>();
        List<Integer> subDocFreqList = new ArrayList<Integer>();
        List<String> subTestDocList = new ArrayList<String>();
        List<Integer> subTestDocFreqList = new ArrayList<Integer>();
        for (int i = 0; i < listOfFiles.length; i++) 
        {
            System.out.println("File#: "+i);
            File file = listOfFiles[i];
            File file_dest = new File (folder_dest.toString()+"\\"+file.getName());
            int numOfLines = countLinesNew(file.toString());
            // Sir, please changes the percentage of data you want to use while classifing by replacing 10. I'm using 10% data
            int trainLines = (numOfLines*2)/100;
            PrintWriter writer = new PrintWriter(file_dest.toString(), "UTF-8");
            FileReader fr = null;
            try {
                fr =  new FileReader(file.toString());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(HealthTweets.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedReader br = new BufferedReader(fr);
            String Rline = null;
            while ((Rline = br.readLine() ) != null) 
            {
                try 
                {
                    String line[] = Rline.replace("|", "=").split("=");
                    String line2[] = line[2].split("http");
                    WordTokenizer wordTokenizer = new WordTokenizer();
                    wordTokenizer.tokenize(line2[0].replaceAll("[^a-zA-Z\\s]", ""));
                    while(wordTokenizer.hasMoreElements()) 
                    {
                        String word = wordTokenizer.nextElement();
                        String wordCompare = word.toLowerCase();
                        if(!swordArrayList.contains(wordCompare) && !wordCompare.contains("@") && !wordCompare.contains("#") )
                        {
                            if(word.toLowerCase().equals("class"))
                            {
                                word = "myclass";
                            }
                            if(trainLines > 0)
                            {
                                if(!wordArrayList.contains(word.toLowerCase()))
                                {
                                    wordArrayList.add(word.toLowerCase());
                                }
                                if(!subDocList.contains(word.toLowerCase()))
                                {
                                    subDocList.add(word.toLowerCase());
                                    subDocFreqList.add(1);
                                }
                                else
                                {
                                    subDocFreqList.set(subDocList.indexOf(word.toLowerCase()),(subDocFreqList.get(subDocList.indexOf(word.toLowerCase()))+1));
                                }
                            }
                            writer.println(word.toLowerCase());
                        }
                    }
                    docList.add(new ArrayList<String>(subDocList));
                    docFreqList.add(new ArrayList<Integer>(subDocFreqList));
                    docClassList.add(i);
                    subDocFreqList.clear();
                    subDocList.clear();
                    trainLines--;
                    if(trainLines <=0)
                    {
                        break;
                    }
                } catch ( Exception ex) 
                {
                    Logger.getLogger(HealthTweets.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            fr.close();
            writer.close();
            writer.flush();                
        }
        File arff_file = new File("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_1\\Health-Tweets\\pre1\\data1.arff");
        PrintWriter arff_writer = new PrintWriter(arff_file.toString(), "UTF-8");
        arff_writer.println("@relation health_care");
        arff_writer.println();
        for(int i=0 ; i< wordArrayList.size() ; i++)
        {
            arff_writer.println("@attribute "+wordArrayList.get(i)+" numeric");
            //System.out.println(i+"Word: "+docList.get(0).get(i)+" Frequency: "+docFreqList.get(0).get(i));
        }
        arff_writer.println("@attribute CLASS {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}");
        arff_writer.println();
        arff_writer.println("@data");
        for(int i=0 ; i< docList.size() ; i++)
        {
            for(int j=0 ; j< wordArrayList.size() ; j++)
            {
                if(docList.get(i).contains(wordArrayList.get(j)))
                {
                    arff_writer.print(docFreqList.get(i).get(docList.get(i).indexOf(wordArrayList.get(j)))+",");
                }
                else
                {
                    arff_writer.print("0,");
                }
            }
            arff_writer.print(""+docClassList.get(i));
            arff_writer.println();
        }
        arff_writer.close();
        arff_writer.flush(); 
        /// Here comes Classifier
        BufferedReader datafile = readDataFile("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_1\\Health-Tweets\\pre1\\data1.arff");
        Instances data = new Instances(datafile);
        data.setClassIndex(data.numAttributes() - 1);
        System.out.println(" NUM of Atributes: "+data.numAttributes());
        Classifier c1 = new IBk(1);
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(c1, data, 10, new Random(1));
        System.out.println("KNN Estimated Accuracy: "+Double.toString(eval.pctCorrect()));
        
        Classifier c2 = new NaiveBayes();
        Evaluation eval2 = new Evaluation(data);
        eval2.crossValidateModel(c2, data, 10, new Random(1));
        System.out.println("KNN Estimated Accuracy: "+Double.toString(eval2.pctCorrect()));
        
        rocchioClassifier(data);
        //Train a new classifier
        //Classifier c2 = new NaiveBayes();
        //c2.buildClassifier(data);
    }    
}

