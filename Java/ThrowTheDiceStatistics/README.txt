-----------------------------------------------------------------------------------------------------------------------
Author: frederic.debrix.git@gmail.com
Date: 2024-10-14
Coverage : 100% (usage of TDD)
Status : DONE
-----------------------------------------------------------------------------------------------------------------------


The goal of the class ThrowTheDiceStatistics is to throw a dice multiple times and see the evolution of the standard deviation.
See the implementation in ThrowTheDiceStatistics # public double[][] throwTheDice(int nbOfThrowPerBatch, int nbOfBatch)
See an example of usage in ThrowTheDiceStatisticsTest # public void test_throwsTheDice_throws600000Times100Batches

Example ------------------------------------
Input:
- nbSidesOfTheDice:     6
- nbOfThrowPerBatch:    600000
- nbOfBatch:            6

Ouput:
     * ----------|--------------------|--------------|-------------------
     * Size of   | Standard deviation | Size of all  | Standard deviation
     * the batch | of the batch       | the batches  | of all the batches
     * ----------|--------------------|--------------|-------------------
     * 600000    | 270,886            | 600000       | 270,886
     * 600000    | 344,510            | 1200000      | 560,394
     * 600000    | 362,144            | 1800000      | 647,643
     * 600000    | 394,079            | 2400000      | 421,383
     * 600000    | 307,812            | 3000000      | 629,479
     * 600000    | 311,781            | 3600000      | 622,122


Possible improvements
- use Spring Boot
- The validation messages should be reviewed.
- The validation messages are hard coded. Internationalization to implement.
- In order to increase the limit in ThrowTheDiceStatistics#validateLimitOfAllTheThrows(int, int),
  need to replace the int[] by double [] in ThrowTheDiceStatistics#buildArrayForThrowResults().
