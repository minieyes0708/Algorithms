initialize stack to the maze entrance coordinates and direction east;
while (stack is not empty)
{
    (i, j, dir) = coordinates and direction deleted from top of stack;
    while (there are more moves)
    {
        (g, h) = coordinates of next move;
        if ((g == m) && (h == p)) success;
        if ((!maze[g][h]) // legal move
                && (!mark[g][h])) // haven't been here before
        {
            mark[g][h] = 1;
            dir = next direction to try;
            add (i, j, dir) to top of stack;
            i = g; j = h; dir = north;
        }
    }
}
cout << "no path found" << endl;
