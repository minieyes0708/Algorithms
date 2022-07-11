int String::Find(String pat)
// i is set to -1 if pat does not occur in s (*this)
// otherwise i is set to point to the first position in *this, where pat begins.
{
    char *p = pat.str, *s = str;
    int i = 0; // i is starting point
    if (*p && *s)
        while (i <= Length() - pat.Length())
            if (*p++ == *s++) { // characters match, get next char in pat and s
                if (!*p) return i; // match found
            }
            else { // no match
                i++; s = str + i; p = pat.str;
            }
    return -1; // pat is empty or does not occur in s
} // end of Find
