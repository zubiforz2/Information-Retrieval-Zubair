# Information-Retrieval- Term Project
These are Netbeans projects using JAVA

Submitted by

Zubair Iqbal [203708]

Hassan Raza Niazi [204622]

Kashif Hafeez Anjum [117700]


## Getting Started

Please follow these steps to run the code.

1) Download Data in a folder "Datasets"

2) Change Complete Paths to data in all programs 

3) Adding jars in given sequences.

Note: We are assuming that you have installed JDK and Netbeans . Thanks 

### Prerequisites DATA

What things you need to install the software and how to install them
Data Set Required:

 Health Tweets: https://archive.ics.uci.edu/ml/datasets/Health+News+in+Twitter
 
 Sentence Corpus: https://archive.ics.uci.edu/ml/datasets/Sentence+Classification
 
 DBworld: https://archive.ics.uci.edu/ml/datasets/DBWorld+e-mails
 
 And then extract data

### Path Edit 

1)I have used comple paths to my data sets.. Please replace it to yours.

2)Also create a directory in your Health Tweets data with name "pre1" (used for storing arff files and preprocessed data)

3)Create a directory in your Sentence Corpus data with name "pre" (used for storing arff files )

4)Replace all the complete paths given in all of the three projects with your's 

NOTE: Please must create afore mentioned directories in your data folders

### Adding Jar Files

 There are 2 weka jar files and you need to add both jar files as they contains some different functions used in Java Programs
 please add Jar files in given sequence otherwise you might get errors
 
 1) Add Weka.jar first
 
 2) Add weka-stable-3.8.1.jae second
 
 ### Stopwords File
 
 I have also added a text file containing stopwords which would be used in Heath Tweets data for removal of stopwords while preprocessing the data.
 
 ### Run
 
 Please note that I have used 10% data of tweetes which took 2 hourse to train and then classify the data. You can change the percentage of the data to be used in "Health Tweets" as it would take time. I have put a comment in the code where you can change that percentage. pleae use 2% data to get results in few seconds or minutes...  Time would increase as you increase the percentage of data to used in training ...  (2% data means 2% data from each document )
 
Also Increase Heap Size in Netbeans for given projects, especially for Health Tweets as it would required a lot of Memory 
 
 ### Explaination of work
 
 We used three data sets out of which only one was preprocessed, We preprocessed remaining two data sets by doing follwing setps 
 
 1) Extract sentences / tweets as one document
 
 2) Remove junk from it
 
 3) Tokenize it
 
 4) Remove Stop words 
 
 5) Counting Frequences ( In Sentence Corpus, we don't consider frequences as it downgrade the accuracy but we have wrote code for that and then later commented, you can uncomment the code and check it)
 
 6) Adding Labels 
 
 7) Creating ARFF files used in weka with the help of preprocessed data
 
 8) Apply all three Classifieres one by one and prrint respectivr accuracies 
