Les class PriorityQueue et QuickSortTest sont inspirés par la javadoc des deux class
source: 
https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html
https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/PriorityQueue.html

La complexité des deux algorithmes sont différents et depend de l'extraction min ou max. 
L'algo array.sort est de O (n*(log(n))) car c'est en effet un tri rapide.
L'algo PriorityQueue est de O((log(n)) pour l'extraction du min et pour les autres cas, c'est du O (n*(log(n))).
