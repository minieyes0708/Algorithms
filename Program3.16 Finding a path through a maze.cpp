struct items {
    int x, y, dir;
};
void path(int m, int p)
// Output a path (if any) in the maze; maze[0][i] = maze[m+1][i] =
// maze[j][0] = maze[j][p+1] = 1, 0 <= i <= p+1, 0 <= j <= m+1.
{
    // start at (1,1)
    mark[1][1] = 1;
    Stack<items> stack(m*p);
    items temp;
    temp.x = 1; temp.y = 1; temp.dir = E;
    stack.Add(temp);

    while (!stack.IsEmpty()) // stack not empty
    {
        temp = *stack.Delete(temp); // unstack
        int i = temp.x; int j = temp.y; int d = temp.dir;
        while (d < 8) // move forwared
        {
            int g = i + move[d].a; int h = j + move[d].d;
            if ((g == m) && (h == p)) { // reached exit
                // output path
                cout << stack;
                cout << i << " " << j << endl; // last two squares on the path
                cout << m << " " << p << endl;
                return;
            }
            if ((!maze[g][h]) && (!mark[g][h])) { // new position
                mark[g][h] = 1;
                temp.x = i; temp.y = j; temp.dir = d + 1;
                stack.Add(temp); // stack it
                i = g; j = h; d = N; // move to (g, h)
            }
            else d++; // try next direction
        }
    }
    cout << "no path in maze" << endl;
}
