Bag::Bag(int MaxBagSize) : MaxSize(MaxBagSize) {
    array = new int[MaxSize]; top = -1;
}
Bag::~Bag() { delete[] array; }
inline Boolean Bag::IsFull() {
    if (op == MaxSize - 1) return TRUE; else return FALSE;
}
inline Boolean Bag::IsEmpty() {
    if (top == -1) return TRUE; else return FALSE;
}
inline void Bag::Full() {
    cerr << "Data structure is full" << endl;
}
inline void Bag::Empty() {
    cerr << "Data structure is empty" << endl;
}
void Bag::Add(int x) {
    if (IsFull()) Full(); else array[++top] = x;
}
int *Bag::Delete(int &x)
{
    if (IsEmpty()) { Empty(); return 0; }
    int deletePos = top / 2; x = array[deletePos];
    for (int index = deletePos; index < top; index++)
        array[index] = array[index + 1]; // compact upper half of array
    top--; return &x;
}
