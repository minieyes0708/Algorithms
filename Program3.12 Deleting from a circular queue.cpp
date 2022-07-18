template<class KeyType>
KeyType *Queue<KeyType>::Delete(KeyType &x)
// remove front element from queue
{
    if (front == rear) { QueueEmpty(); return 0; }
    front = (front + 1) % MaxSize;
    x = queue[front]; return &x;
}
