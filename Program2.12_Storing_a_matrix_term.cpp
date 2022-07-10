int SparseMatrix::StoreSum(int sum, int &LastInResult, int r, int c)
// If sum != 0, then it along with its row and column position are stored as the
// (LastInResult + 1)'st entry in result. LastInResult is incremented. 1 is returned to
// signal to the calling function that there is no memory available for the new term,
// and 0 otherwise.
{
    if (sum != 0) {
        if (LastInResult < MaxTerms - 1) {
            LastInResult++;
            smArray[LastInResult].row = r;
            smArray[LastInResult].col = c;
            smArray[LastInResult].value = sum;
            return 0;
        }
        else {
            cerr << "Number of terms in product exceeds MaxTerms" << endl;
            return 1; // terminate proram
        }
    }
    else return 0;
} // end of StoreSum
