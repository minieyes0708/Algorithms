template <class Type>
Bag<Type>::Bag(int MaxBagSize) : MaxSize(MaxBagSize) {
    array = new Type[MaxSize];
    top = -1;
}
template<class Type>
Bag<Type>::~Bag() {
    delete[] array;
}
template<class Type>
void Bag<Type>::Add(const Type &x) {
    if (IsFull()) Full();
    else array[++top] = x;
}
template<class Type>
Type *Bag<Type>::Delete(Type &x) {
    if (IsEmpty()) { Empty(); return 0; }
    int deletePos = top / 2;
    x = array[deletePos];
    for (int index = deletePos; index < top; index++)
        array[index] = array[index + 1]; // compact upper half of array
    top--;
    return &x;
}
