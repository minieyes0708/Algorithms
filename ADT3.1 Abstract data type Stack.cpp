template<class KeyType>
class Stack
{
    // objects: A finite ordered list with zero or more elements.
    public:
        Stack(int MaxStackSize = DefaultSize);
        // Create an empty stack whose maximum size is MaxStackSize
        Boolean IsFull();
        // if number of elements in the stack is equal to the maximum size of
        // the stack, return TRUE(1); otherwise, return FALSE(0)
        void Add(const KeyType &item);
        // if IsFull(), then StackFull(); else insert item into the top of the stack.
        Boolean IsEmpty();
        // if number of elements in the stack is 0, return TRUE(1) else return FALSE(0).
        KeyType *Delete(KeyType&);
        // if IsEmpty(), then StackEmpty() and return 0;
        // else remove and return a pointer to the top element of the stack.
};
