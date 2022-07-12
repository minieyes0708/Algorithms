template<class Type>
class Bag
{
    public:
        Bag(int MaxSize = DefaultSize); // constructor
        ~Bag(); // destructor

        void Add(const Type&); // insert element into bag
        Type *Delete(Type&); // delete element from bag

        Boolean IsFull(); // return TRUE if the bag is full; FALSE otherwise
        Boolean IsEmpty(); // return TRUE if the bag is empty; FALSE otherwise

    private:
        void Full(); // action when bag is full
        void Empty(); // action when bag is empty

        Type *array;
        int MaxSize; // size of array
        int top; // highest position in array that contains an element
};
