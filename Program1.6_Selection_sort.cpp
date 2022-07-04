// for (int i = 0; i < n; i++) {
//   examine a[i] to a[n-1] and suppose the smallest integer is at a[j];
//   interchange a[i] and a[j];
// }
void sort (int *a, const int n)
// sort the n integers a[0] to a[n-1] to nondescreasing order
{
    for (int i = 0; i < n; i++) {
        int j = i;
        // find smallest integer in a[i] to a[n-1]
        for (int k = i + 1; k < n; k++)
            if (a[k] < a[j]) j = k;
        // interchange
        int temp = a[i]; a[i] = a[j]; a[j] = temp;
    }
}
