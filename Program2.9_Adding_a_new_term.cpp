void Polynomial::NewTerm(float, c, int e)
// Add a new term to C(x).
{
    if (free >= MaxTerms) {
        cerr << "Too many terms in polynomials" << endl;
        exit(1);
    }
    termArray[free].coef = c;
    termArray[free].exp = e;
    free++;
} // end of NewTerm
