SparseMatrix SparseMatrix::Multiply(SparseMatrix b)
// Multiply two sparse matrices A (*this) and B producing Result.
{
    if (Cols != b.Rows) {
        cout << "Incompatible matrices" << endl;
        return EmptyMatrix();
    }
    if ((Terms == MaxTerms) || (b.Terms == MaxTerms)) {
        cout << "One additional space in a or b needed" << endl;
        return EmptyMatrix();
    }
    SparseMatrix bXpose = b.FastTranspose();
    SparseMatrix result;
    int currRowIndex = 0, LastInResult = -1,
        currRowBegin = 0, currRowA = smArray[0].row;
    // set boundary conditions
    smArray[Terms].row = Rows; bXpose.smArray[b.Terms].row = b.Cols;
    bXpose.smArray[b.Terms].col = -1; int sum = 0;
    while (currRowIndex < Terms) // generate row currRowA of result
    {
        int currColB = bXpose.smArray[0].row; int currColIndex = 0;
        while (currColIndex <= b.Terms)
        // multiply row currRowA of a by column currColB of b
        {
            if (smArray[currRowIndex].row != currRowA) // end of row currRowA
            {
                if (result.StoreSum(sum, LastInResult, currRowA, currColB))
                    return EmptyMatrix(); // error
                else sum = 0; // reset sum
                currRowIndex = currRowBegin;
                // go to next column
                while (bXpose.smArray[currColIndex].row == currColB) currColIndex++;
                currColB = bXpose.smArray[currColIndex].row;
            }
            else if (bXpose.smArray[currColIndex].row != currColB)
            // end of column currColB of b
            {
                if (result.StoreSum(sum, LastInResult, currRowA, currColB))
                    return EmptyMatrix(); // error
                else sum = 0; // reset sum
                // set to multiply row currRowA with next column
                currRowIndex = currRowBegin;
                currColB = bXpose.smArray[currColIndex].row;
            }
            else
                switch (compare(smArray[currRowIndex].col,
                            bXpose.smArray[currColIndex].col)) {
                    case '<': // advance to next term in row.
                        currRowIndex++; break;
                    case '=': // add to sum
                        sum += smArray[currRowIndex].value *
                            bXpose.smArray[currColIndex].value;
                        currRowIndex++; currColIndex++; break;
                    case '>': // advance to next term in column currColB
                        currColIndex++;
                } // end of switch
        } // of while (currColIndex <= b.Terms)
        while (smArray[currRowIndex].row == currRowA) // advance to next row
            currRowIndex++;
        currRowBegin = currRowIndex; currRowA = smArray[currRowIndex].row;
    } // end of while (currRowIndex < Terms)
    result.Rows = Rows; result.Cols = b.Cols; result.Terms = LastInResult + 1;
    return result;
} // end of Multiply
