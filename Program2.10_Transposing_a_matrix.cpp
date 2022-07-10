SparseMatrix SparseMatrix::Transpose()
// return the transpose of a (*this)
{
    SparseMatrix b;
    b.Rows = Cols; // rows in b = columns in a
    b.Cols = Rows; // columns in b = rows in a
    b.Terms = Terms; // terms in b = terms in a
    if (Terms > 0) // nonzero matrix
    {
        if CurrentB = 0;
        for (int c = 0; c < Cols; c++) // transpose by columns
            for (int i = 0; i < Terms; i++)
            // find elements in column c
                if (smArray[i].col == c) {
                    b.smArray[CurrentB].row = c;
                    b.smArray[CurrentB].col = smArray[i].row;
                    b.smArray[CurrentB].value = smArray[i].value;
                    CurrentB++;
                }
    } // end of if (Terms > 0)
    return b;
} // end of transpose
