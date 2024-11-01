-----------------------------------------------------------------------------------------------------------------------
Author: frederic.debrix.git@gmail.com
Date: 2024-10-30
Coverage : On-going (usage of TDD)
Status : On-going
-----------------------------------------------------------------------------------------------------------------------

Next Steps
- in SudokuSolver, extract the logic of the function handleTwoValuesOnlyAvailableInTwoSquares() into a new class
- consistency : need to review all the unit tests function name in order to be consistent


Vocabulary - https://en.wikipedia.org/wiki/Sudoku
Let assume we have a sudoku with a 9 x 9 grid
- The sudoku has 81 Squares.
- Each square has one of the 9 possible values : 1 - 9.
- A region is a subgrid which contains all the digits from 1 to 9. For example, a row or a column is a region.


Classes
- SudokuSquare is a square of the sudoku.
- SudokuRegion is a region of the sudoku.
- SudokuRegions is a list of SudokuRegion.
- SudokuBuilder converts the input int[][] into SudokuSquare[][] and creates the SudokuRegion.