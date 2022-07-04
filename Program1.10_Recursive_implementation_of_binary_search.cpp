int BinarySearch(int *a, const int x, const int left, const int right)
// Search the sorted array a[left], ..., a[right] for x
{
    if (left <= right) {
        int middle = (left + right) / 2;
        switch (compare(x, a[middle])) {
            case '>': return BinarySearch(a, x, middle + 1, right); // x > a[middle]
            case '<': return BinarySearch(a, x, left, middle - 1); // x < a[middle]
            case '=': return middle; // x == a[middle]
        } // end of switch
    } // end of if
    return -1; // not found
} // end of BinarySearch
