int String::FastFind(String pat)
{
    // Determine if pat is a substring of s
    int PosP = 0, PosS = 0;
    int LengthP = pat.Length(), LengthS = Length();
    while ((PosP < LengthP) && (PosS < LengthS))
        if (pat.str[PosP] == str[PosS]) { // character match
            PosP++; PosS++;
        }
        else
            if (PosP == 0) PosS++;
            else PosP = pat.f[PosP - 1] + 1;
    if (PosP < LengthP) return -1;
    else return PosS - LengthP;
} // end of FastFind
