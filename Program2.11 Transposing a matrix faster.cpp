SparseMatrix SparseMatrix::FastTranspose()
// The transpose of a (*this) is placed in b and is found in O(terms + columns) time.
{
    int *RowSize = new int[Cols];
    int *RowStart = new int[Cols];
    SparseMatrix b;
    b.Rows = Cols; b.Cols = Rows; b.Terms = Terms;
    if (Terms > 0) // nonzero matrix
    {
        // compute RowSize[i] = number of terms in row i of b
        for (int i = 0; i < Cols; i++) RowSize[i] = 0; // initialize
        for (i = 0; i < Terms; i++) RowSize[smArray[i].col]++;

        // RowStart[i] = starting position of row i in b
        RowStart[0] = 0;
        for (i = 1; i < Cols; i++) RowStart[i] = RowStart[i - 1] + RowSize[i - 1];

        for (i = 0; i < Terms; i++) // move from a to b
        {
            int j = RowStart[smArray[i].col];
            b.smArray[j].row = smArray[i].col;
            b.smArray[j].col = smArray[i].row;
            b.smArray[j].value = smArray[i].value;
            RowStart[smArray[i].col]++;
        } // end of for
    } // end of if

    delete[] RowSize;
    delete[] RowStart;
    return b;
} // end of FastTranspose
