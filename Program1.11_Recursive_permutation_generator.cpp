void perm(char *a, const int k, const int n) // n is the size of a
// Generate all the permutations of a[k], ..., a[n-1].
{
    if (k == n - 1) { // output permutation
        for (int i = 0; i < n; i++) cout << a[i] << " ";
        cout << endl;
    }
    else { // a[k], ..., a[n-1] has more than one permutations. Generate these recursively.
        for (int i = k; i < n; i++)  {
            // interchange a[k] and a[i]
            char temp = a[k]; a[k] = a[i]; a[i] = temp;
            perm(a, k+1, n); // all permutations of a[k+1], ..., a[n-1]
            // interchange a[k] and a[i] again, to return to the original configuration
            temp = a[k]; a[k] = a[i]; a[i] = temp;
        }
    } // end of else
} // end of perm