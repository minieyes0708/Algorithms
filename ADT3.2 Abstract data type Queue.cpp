template<class KeyType>
class Queue
{
    // objecs: A finite ordered list with zero or more elements.
    public:
        Queue(int MaxQueueSize = DefaultSize);
        // Create an empty queue whose maximum size is MaxQueueSize
        Boolean IsFull();
        // if number of elements in the queue is equal to the maximum size of
        // the queue, return TRUE(1); otherwise, return FALSE(0)
        void Add(const KeyType &item);
        // if IsFull(), then QueueFull(); else insert item at rear of the queue.
        Boolean IsEmpty();
        // if number of elements in the queue is equal to 0, return TRUE(1)
        // else return FALSE(0).
        KeyType *Delete(KeyType&);
        // if IsEmpty(), then QUeueEmpty() and return 0;
        // else remove the item at the front of the queue and return a pointer to it.
}
