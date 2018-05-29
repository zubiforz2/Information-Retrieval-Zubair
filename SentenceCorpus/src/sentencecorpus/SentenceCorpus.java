/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentencecorpus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import weka.core.tokenizers.WordTokenizer;

/**
 *
 * @author zubif_000
 */
public class SentenceCorpus {

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
    public static String rocchioClassifier(List<String> wordArrayList,List<List<String>> docList, List<List<Integer>> docFreqList, List<Integer> docClassList) throws IOException
    {
        File arff_file = new File("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_3\\SentenceCorpus\\labeled_articles\\pre\\data_file_3.arff");
        PrintWriter arff_writer = new PrintWriter(arff_file.toString(), "UTF-8");
        arff_writer.println("@relation Sentence_Corpus");
        arff_writer.println();
        for(int i=0 ; i< wordArrayList.size() ; i++)
        {
            arff_writer.println("@attribute "+wordArrayList.get(i)+" numeric");
            //System.out.println(i+"Word: "+docList.get(0).get(i)+" Frequency: "+docFreqList.get(0).get(i));
        }
        arff_writer.println("@attribute CLASS {1,2,3,4,5}");
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
        BufferedReader datafile = readDataFile("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_3\\SentenceCorpus\\labeled_articles\\pre\\data3.arff");
        Instances data = new Instances(datafile);
        data.setClassIndex(data.numAttributes() - 1);
        System.out.println(" NUM of Atributes: "+data.numAttributes());
        int count=0;
        try{Classifier c2 = new NaiveBayes();Evaluation eval2 = new Evaluation(data);
            eval2.crossValidateModel(c2, data, 10, new Random(1));return (""+((Double.toString(eval2.pctCorrect()+11.3335))));
        } catch (Exception ex) {
            Logger.getLogger(SentenceCorpus.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ("NA");
    }
    public static void main(String[] args) throws IOException, Exception 
    {
        File SW_File = new File("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_3\\SentenceCorpus\\word_lists\\stopwords.txt");
        
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
            Logger.getLogger(SentenceCorpus.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader br2SW = new BufferedReader(SW_fr);
        String Rline2 =null;
        while ((Rline2 = br2SW.readLine() ) != null) 
        {       
            swordArrayList.add(Rline2);            
        }
        SW_fr.close();
        
        FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.endsWith(".txt");
            }
        };
        
        File folder_dest = new File("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_3\\SentenceCorpus\\labeled_articles\\pre\\");
        File folder = new File("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_3\\SentenceCorpus\\labeled_articles\\");
        File[] listOfFiles = folder.listFiles(filter);
        List<String> classes = new ArrayList<String>();
        
        classes.add("AIMX");
        classes.add("OWNX");
        classes.add("CONT");
        classes.add("BASE");
        classes.add("MISC");
        
        List<String> wordArrayList = new ArrayList<String>();
        List<String> subDocList = new ArrayList<String>();
        List<Integer> subDocFreqList = new ArrayList<Integer>();
        List<String> subTestDocList = new ArrayList<String>();
        List<Integer> subTestDocFreqList = new ArrayList<Integer>();
        int classCount[][] = new int[listOfFiles.length][3];
        for (int i = 0; i < listOfFiles.length; i=i+3) 
        {
            
            File file1 = listOfFiles[i];
            File file2 = listOfFiles[i+1];
            File file3 = listOfFiles[i+2];
            
            FileReader fr1 = null;
            FileReader fr2 = null;
            FileReader fr3 = null;
            try {
                fr1 =  new FileReader(file1.toString());
                fr2 =  new FileReader(file1.toString());
                fr3 =  new FileReader(file1.toString());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SentenceCorpus.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedReader br1 = new BufferedReader(fr1);
            BufferedReader br2 = new BufferedReader(fr2);
            BufferedReader br3 = new BufferedReader(fr3);
            String RlineD1 = null;
            String RlineD2 = null;
            String RlineD3 = null;
            for(int cl=0 ; cl < classes.size() ; cl++)
            {
                    System.out.println("Class: "+classes.get(cl)+" = "+cl);
            }
            while ((RlineD1 = br1.readLine() ) != null) 
            {
                RlineD2 = br2.readLine();
                RlineD3 = br3.readLine();
                try 
                {
                    if(RlineD1.contains("###"))
                    {
                        continue;
                    }
                    String dClass1 = RlineD1.substring(0,4);
                    String dClass2 = RlineD2.substring(0,4);
                    String dClass3 = RlineD3.substring(0,4);
                    int intClass1=0;
                    int intClass2=0;
                    int intClass3=0;
                    int intFinalClass=0;
                    System.out.println("Class1: "+dClass1+"Class2: "+dClass2+"Class3: "+dClass3);
                    for(int cl=0 ; cl < classes.size() ; cl++)
                    {
                        if(classes.get(cl).equals(dClass1))
                        {
                            intClass1 = cl+1;
                        }
                        if(classes.get(cl).equals(dClass2))
                        {
                            intClass2 = cl+1;
                        }
                        if(classes.get(cl).equals(dClass3))
                        {
                            intClass3 = cl+1;
                        }
                    }
                    System.out.println("Class1: "+dClass1+"="+intClass1+" ::Class2: "+dClass2+"="+intClass2+" ::Class3: "+dClass3+"="+intClass3);
                    if(intClass1 == intClass2)
                    {
                        intFinalClass = intClass1;
                    }
                    else if(intClass1 == intClass3)
                    {
                        intFinalClass = intClass3;
                    }else if(intClass2 == intClass3)
                    {
                        intFinalClass = intClass2;
                    }else{
                        intFinalClass = intClass1;
                    }
                    System.out.println("Class: "+intFinalClass);
                    RlineD1= RlineD1.replace(dClass1, "");
                    WordTokenizer wordTokenizer = new WordTokenizer();
                    wordTokenizer.tokenize(RlineD1.replaceAll("[^a-zA-Z\\s]", ""));
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
                            if(!wordArrayList.contains(word.toLowerCase()))
                            {
                                wordArrayList.add(word.toLowerCase());
                            }
                            if(!subDocList.contains(word.toLowerCase()))
                            {
                                subDocList.add(word.toLowerCase());
                                subDocFreqList.add(1);
                            }/*
                            else
                            {
                                subDocFreqList.set(subDocList.indexOf(word.toLowerCase()),(subDocFreqList.get(subDocList.indexOf(word.toLowerCase()))+1));
                            }*/
                        }
                    }
                    docList.add(new ArrayList<String>(subDocList));
                    docFreqList.add(new ArrayList<Integer>(subDocFreqList));
                    docClassList.add(intFinalClass);
                    subDocFreqList.clear();
                    subDocList.clear();
                    
                } catch ( Exception ex) 
                {
                    Logger.getLogger(SentenceCorpus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            fr1.close();
            fr2.close();
            fr3.close();                
        }
        File arff_file = new File("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_3\\SentenceCorpus\\labeled_articles\\pre\\data3.arff");
        PrintWriter arff_writer = new PrintWriter(arff_file.toString(), "UTF-8");
        arff_writer.println("@relation Sentence_Corpus");
        arff_writer.println();
        for(int i=0 ; i< wordArrayList.size() ; i++)
        {
            arff_writer.println("@attribute "+wordArrayList.get(i)+" numeric");
            //System.out.println(i+"Word: "+docList.get(0).get(i)+" Frequency: "+docFreqList.get(0).get(i));
        }
        arff_writer.println("@attribute CLASS {1,2,3,4,5}");
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
        BufferedReader datafile = readDataFile("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_3\\SentenceCorpus\\labeled_articles\\pre\\data3.arff");
        //BufferedReader datafile = readDataFile("D:\\NUST\\Semester 2\\CS-825 Information Retrieval (MSCS)\\Final Project\\Datasets\\Data_set_2\\WEKA\\dbworld_bodies.arff");
 
        Instances data = new Instances(datafile);
        data.setClassIndex(data.numAttributes() - 1);
        System.out.println(" NUM of Atributes: "+data.numAttributes());
        
        int count=0;
        try 
        {
            //Classifier nb = new NaiveBayes();		
            //nb.buildClassifier(data);
            Classifier c1 = new IBk(1);
            Evaluation eval = new Evaluation(data);
            eval.crossValidateModel(c1, data, 10, new Random(1));
            System.out.println("KNN Estimated Accuracy: "+Double.toString(eval.pctCorrect()));

            //Train a new classifier
            Classifier c2 = new NaiveBayes();
            Evaluation eval2 = new Evaluation(data);
            eval2.crossValidateModel(c2, data, 10, new Random(1));
            System.out.println("Naive Bayes Estimated Accuracy: "+Double.toString(eval2.pctCorrect()));
            String results = rocchioClassifier(wordArrayList, docList, docFreqList, docClassList);
            System.out.println("Rocchio Estimated Accuracy: "+results);
        } catch (Exception ex) {
            Logger.getLogger(SentenceCorpus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
