Java-app-command-line-design-patterns
=====================================

A small java app using design pattern. Technologies: Java

###Manage with changes in the structure.
 - The program must be executed from the command line, with following format: 
path1_nameOfProgram path2_nameFileOfdata intervalSeconds controlInterval.
(example: C:\UzDiz\kgrcevic_zadaca_2 klubovi.txt 30 5 3).
 -  After loading the file data is determined by the initial state of each club, and makes 
the championship table. At the beginning all the clubs labeled as competitors. Then 
the thread that runs repeats his performance in the exact interval, regardless of the 
duration of the internal processing. Thread in each interval generates match between 
the clubs. First generate two clubs that will be in the meeting, and then score (0 -
draw, 1 - Winner 1st Club, 2 - winner of the second club). The following is an update 
of the table based on the results of the existing round. If there is a change in the 
order of teams in the table, it is necessary to archive (in working memory) the 
existing order of clubs. The following is a check for changes in the efficiency of the 
clubs. Efficiency is calculated as the ratio of the number of points and the number of 
rounds in which the club participated. For each altered efficiency it is necessary to 
inform the club about changing. After each control interval (determined by the 
number worked in rounds) checks the condition of the clubs. Club that the table has 
a worse place than in the previous inspection, referred to as a weak competitor.
Weak competitor who does not become a normal competitor in the number of 
checks specified controlInterval, ceases to be a competitor and does not appear in 
continue of competition. The following is a print of his data on the screen. The user 
can request a printout the total number of archived Championship table, print the 
results of a particular round, print all the historical results of certain club of your 
choice, etc.

![Alt text](https://github.com/krunogr/Java-app-command-line-design-patterns/blob/master/Capture.JPG "App")
