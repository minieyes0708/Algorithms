// int BinarySearch(int *a, const int x, const int n)
// // Search the sorted array a[0], ..., a[n-1] for x
// {
//   for (initialize left and right; while there are more elements;)
//   {
//     let middle be the middle element;
//     switch (compare(x, a[middle])) {
//       case '>': set left to middle + 1; break;
//       case '<': set right = middle - 1; break;
//       case '=': found x;
//     } // end of switch
//   } // end of for
//   not found;
// } // end of BinarySearch
int BinarySearch(int *a, const int x, const int n)
// Search the sorted array a[0], ..., a[n-1] for x
{
    for (int left = 0, right = n - 1; left <= right;) { // while more elements
        int middle = (left + right) / 2;
        switch (compare(x, a[middle])) {
            case '>': left = middle + 1; break; // x > a[middle]
            case '<': right = middle - 1; break; // x < a[middle]
            case '=': return middle; // x == a[middle]
        } // end of switch
    } // end of for
    return -1; // not found
} // end of BinarySearch
