class Bag
{
    public:
        Bag(int MaxSize = DefaultSize); // constructor
        ~Bag(); // destructor

        void Add(int); // insert an integer into bag
        int *Delete(int&); // delete an integer from bag

        Boolean IsFull(); // return TRUE if the bag is full; FALSE otherwise
        Boolean IsEmpty(); // return TRUE if the bag is empty; FALSE otherwise
    private:
        void Full(); // action when bag is full
        void Empty(); // action when bag is empty

        int *array;
        int MaxSize; // size of array
        int top; // highest position in array that contains an element
}
