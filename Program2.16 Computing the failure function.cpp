void String::fail()
// Compute the failure function of the pattern p (*this).
{
    int LengthP = Length();
    f[0] = -1;
    for (int j = 0; j < LengthP; j++) // compute f[j]
    {
        int i = f[j - 1];
        while ((*(str + j) != *(str+i+1)) && (i > 0)) i = f[i];
        if (*(str+j) == *(str+i+1)) f[j] = i + 1;
        else f[j] = -1;
    }
} // end of fail
