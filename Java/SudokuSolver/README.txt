-----------------------------------------------------------------------------------------------------------------------
Author: frederic.debrix.git@gmail.com
Coverage : Ongoing (usage of TDD)
Status : Resolve easy, medium, expert, master and extreme sudokus. See the class SudokuSolverTest.


         BLOCKED ON one impossible sudoku. I need to figure out the algorithm before being able to implement it!

-----------------------------------------------------------------------------------------------------------------------

Next Steps
- Javadoc
- Update current file
- consistency : need to review all the unit tests function name in order to be consistent



Vocabulary - https://en.wikipedia.org/wiki/Sudoku
Let assume we have a sudoku with a 4 x 4 grid
- The sudoku has 16 Squares.
- Each square has one of the 4 possible values : 1 - 4.
- A region is a subgrid which contains all the digits from 1 to 4. For example, a row or a column is a region.

Example with
[AA] [AB] [AC] [AD]
[BA] [BB] [BC] [BD]
[CA] [CB] [CC] [CD]
[DA] [DB] [DC] [DD]

We have 16 squares: named [AA] to [DD]
We have 12 regions, each of them have 4 squares:
- 4 horizontals regions [AA]-[AD], [BA]-[BD], [CA]-[CD] and [DA]-[DD],
- 4 verticals regions [AA]-[DA], [AB]-[DB], [AC]-[DC] and [AD]-[DD],
- 4 "sub-grid" regions [AA]-[BB], [AC]-[BD], [CA]-[DB] and [CC]-[DD].

Each square is in 3 regions : one horizontal region, one vertical region and one sub-region.

----------------------------------------------------------------------------------------------------
Classes
----------------------------------------------------------------------------------------------------
- SudokuSquare is a square of the sudoku.
- SudokuRegion is a region of the sudoku.
- SudokuRegions is a list of SudokuRegion.
- SudokuBuilder converts the input int[][] into SudokuSquare[][], creates the SudokuRegion, and link the SudokuSquare to SudokuRegion.


----------------------------------------------------------------------------------------------------
Logic / Algorithms
----------------------------------------------------------------------------------------------------


----------------------------------------------------------------------------------------------------
1) When a square finds its winning value, this value is not possible for the other squares of the 3 regions

We have a 4 x 4 sudoku without winning values.
[AA] [AB] [AC] [AD]
[BA] [BB] [BC] [BD]
[CA] [CB] [CC] [CD]
[DA] [DB] [DC] [DD]

Each square has 4 possible values: 1, 2, 3, 4.
[1-4] [1-4] [1-4] [1-4]
[1-4] [1-4] [1-4] [1-4]
[1-4] [1-4] [1-4] [1-4]
[1-4] [1-4] [1-4] [1-4]

Let say that in [AA], the winning value is 1, then the value 1 is not available in the other squares of
- the horizontal region [AA]-[AD]
- the vertical region [AA]-[BA]
- the sub-grid region [AA]-[BB]

[1]   [2-4] [2-4] [2-4]
[2-4] [2-4] [1-4] [1-4]
[2-4] [1-4] [1-4] [1-4]
[2-4] [1-4] [1-4] [1-4]

This logic is done thanks to broadcast to the regions of the square that the square found its winning value.


----------------------------------------------------------------------------------------------------
2) OneValueInOneSquareOfTheRegion: When a possible value is only possible in one square of the region

[AA] [AB] | [AC] [AD]
[BA] [BB] | [BC] [BD]
----------|----------
[CA] [CB] | [CC] [CD]
[DA] [DB] | [DC] [DD]

[AA] is 1, [DB] is 4 and [CC] is 4. The sudoku is in the state below.

[1]   [2-3] [2-3] [2-4]
[2-4] [2-3] [1-3] [1-4]
[2-3] [1-3] [4]   [1-3]
[2-3] [4]   [1-3] [1-3]

On the first horizontal region [AA]-[AD], composed of 4 squares, the value 4 is only possible in the square [AD]. Then [AD] is 4.
On the left vertical region [AA]-[DA], composed of 4 squares, the value is only possible in the square [BA]. Then [BA] is 4.
[1]   [2-3] [2-3] [4]
[2-4] [2-3] [1-3] [1-4]
[2-3] [1-3] [4]   [1-3]
[2-3] [4]   [1-3] [1-3]

Then, we can clean up [BD], using the algorithm 1) above.
[1]   [2-3] [2-3] [4]
[4]   [2-3] [1-3] [1-3]
[2-3] [1-3] [4]   [1-3]
[2-3] [4]   [1-3] [1-3]


----------------------------------------------------------------------------------------------------
3) ValueInHorizontalOrVerticalOfASubGrid: In a row or a column, if a value is only possible
   in the same subgrid, this value cannot be possible for the other squares of the subgrid.

[AA] [AB] | [AC] [AD]
[BA] [BB] | [BC] [BD]
----------|----------
[CA] [CB] | [CC] [CD]
[DA] [DB] | [DC] [DD]

Below 2 columns of a sudoku 4 X 4
[1,4]   [2,3,4] |
[3,4]   [2,3,4] |
----------------|
[1,2,3] [1,2,3] |
[1,2,3] [1,3,4] |

In the left column, which is a region, the value 4 is only in the top subgrid, [AA] and [BA].
That means the others squares of the region cannot have the value 4 : [AB] and [BB].

[1,4]   [2,3]   |
[3,4]   [2,3]   |
----------------|
[1,2,3] [1,2,3] |
[1,2,3] [1,3,4] |

