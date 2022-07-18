template<class KeyType>
void Queue<KeyType>::Add(const KeyType &x)
// add x to the circular queue
{
    int newrear = (rear + 1) % MaxSize;
    if (front == newrear) QueueFull();
    else queue[rear = newrear] = x;
}
