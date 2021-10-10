# Financial transactions file comparator
A comparing application that compares two csv files with financial transaction data.

## Where to find it

[Link of the website](https://young-wildwood-77138.herokuapp.com/)

## User guide
### First page
On the first page you get the option to upload two csv files. These files will be validated whether they contain the
correct data. For example, the file should contain the following headers:

* ProfileName
* TransactionDate
* TransactionAmount
* TransactionNarrative
* TransactionDescription
* TransactionID
* TransactionType
* WalletReference

You can find two example csv files in the directory ./files

### Second page
On the second page you can find the summary of the comparison between de data of both files. You wil be provided with
following data:
* Total records
* Matching records
* Unmatched records
* Number of duplicates

Then you can go to the unmatched report, which contains all the transactions that did not match. This is a unique list
where the duplicates were removed. For this comparison all properties where used.

When you look for a specific match between a transaction, visible in the unmatched report, you can go to the 'Search'
link. This wil bring you to the third page.

### Third page
On this page you find a table with transactions that matches the given transaction you searched for. Here a broader search
algorithm is used so that transactions with slightly different data can be compared. The table provides a 'ratio' score,
which means that how closer the ratio gets to 100, the more accurately the transactions match.

For the broader search the [FuzzyWuzzy](https://github.com/xdrop/fuzzywuzzy) library is used. This is a fuzzy string
matching alghoritm for java based on the FuzzyWuzzy Python algorithm. The algorithm uses Levenshtein distance to calculate
similarity between strings.

Different searching strategies can be applied. You can search the results with the following strategies:
* Simple Ratio: the Simple Ratio function compares two strings by measuring the difference between two sequences. For
  this it uses the Levenshtein distance algorithm.
* Partial Ratio: the problem with the Simple Ratio function is that inconsistent substrings sometimes give an unjustified
  low score. To get around it, you can use the Partial Ratio or best partial algorithm, when two strings are of noticeably
  different lengths.
* Token Sort Ratio & Token Sort Partial Ratio: The problem with the Simple Ratio & Partial Ratio function is that not
  only inconsistent substrings give an unjustified low score, but we also have to deal with differences in string construction.
  To get around it, you can use the Token Sort Ratio algorithm. The token sort approach involves tokenizing the string in
  question, sorting the tokens alphabetically, and then joining them back into a string. Then it compares the transformed
  strings with a Simple Ratio or Partial Ratio.
* Token Set Ratio & Token Set Partial Ratio: The token set approach is similar, but a little bit more flexible. Here, it
  tokenizes both strings, but instead of immediately sorting and comparing, it splits the tokens into two groups:
  intersection and remainder. It uses those sets to build up a comparison string. Then it compares the transformed strings
  with a Simple Ratio or Partial Ratio.

You can find more information on this [video](https://www.youtube.com/watch?v=4L0Py4GkmPU) on Youtube.

You can also choose which ratio the matching result must meet in order to be displayed in the table.

The first time you enter the third page, by default you will be provided with transactions that where searched with
the Simple Ratio at a ratio of 80.

For all searching strategies, all properties are calculated equal. You can call this a more pessimistic approach, but
this way you do not miss any data that has different values.

## Developers guide

### Recourses
* You can find the Java Documentation in the directory ./javadoc. Open there the index.html file in you browser.
* You can find the csv files and some corrupted csv files (for demonstration purposes) in the directory ./files
* Diagrams can be found in the directory ./uml

### Running on local machine
1. If not already installed, download the java jdk on you machine. You can find the download link [here](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html).
2. Open the folder 'file-comparator' with a java compatible IDE. For example IntelliJ or Eclipse.
3. Right-click on the 'pom.xml' file and choose maven/reload project.
4. Open the java file src/main/java/be.jorisgulinck.filecomparator/FileComparatorApplication.
5. Right-click on 'FileComparatorApplication' and choose run.
6. Open your browser and go to 'http://localhost:8080/'.


