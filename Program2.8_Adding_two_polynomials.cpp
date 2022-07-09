Polynomial Polynomial::Add(Polynomial B)
// return the sum of A(x)(int *this) and B(x)
{
    Polynomial C; int a = Start; int B.Start; C.Start = free; float c;
    while ((a <= Finish) && (b <= B.Finish))
        switch (compare(termArray[a].exp, termArray[b].exp))
        {
            case '=':
                c = termArray[a].coef + termArray[b].coef;
                if (c) NewTerm(c, termArray[a].exp);
                a++; b++;
                break;
            case '<':
                NewTerm(termArray[b].coef, termArray[b].exp);
                b++;
                break;
            case '>':
                NewTerm(termArray[a].coef, termArray[a].exp);
                a++;
        } // end of switch and while
    // add in remaining terms of A(x)
    for (; a <= Finish; a++)
        NewTerm(termArray[a].coef, termArray[a].exp);
    // add in remaining terms of B(x)
    for (; b <= B.Finish; b++)
        NewTerm(termArray[b].coef, termArray[b].exp);
    C.Finish = free - 1;
    return C;
} // end of Add
