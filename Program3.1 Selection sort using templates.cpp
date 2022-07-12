template <class KeyType>
void sort(KeyType *a, int n)
// sort the n KeyTypes a[0] to a[n-1] into nondecreasing order
{
    for (int i = 0; i < n; i++)
    {
        int j = i;
        // find smallest KeyType in a[i] to a[n-1]
        for (int k = i + 1; k < n; k++)
            if (a[k] < a[j]) { j = k; }
        // interchange
        KeyType temp = a[i]; a[i] = a[j]; a[j] = temp;
    }
}
