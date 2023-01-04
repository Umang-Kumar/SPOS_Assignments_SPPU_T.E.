#include<bits/stdc++.h>
using namespace std;

void pageFaults(int pages[], int n, int capacity)
{
	unordered_set<int> s;//log n

    // s={1,2,3,4}
	
    unordered_map<int, int> indexes;
    // indexs[7]=0;  log n
    // map<string,int>m;

    // m["mohit"]=integer
	
    // Start from initial page

	int page_faults = 0,hit=0;
	for (int i=0; i<n; i++)
	{
		if (s.size() < capacity)
		{
			if (s.find(pages[i])==s.end())
			{
				s.insert(pages[i]);

				// increment page fault
				page_faults++;
			}
            else{ 
                hit++;
            }
        // 1st poiter  2 3 4   

        // 1    
        //     4
        //  2

        //  1 2 4 
		// s.find(1);
        //s.find(5)=

			indexes[pages[i]] = i;
          
            // indexs[7]=0;    log n

		}
		else
		{
			if (s.find(pages[i]) == s.end())
			{
				int lru = INT_MAX, val;
                
				for (auto it=s.begin(); it!=s.end(); it++)
				{
                    // *it

					if (indexes[*it] < lru)
					{
						lru = indexes[*it];
						val = *it;
					}
				}

				s.erase(val);

				s.insert(pages[i]);

				page_faults++;
			}
            else
            {
                hit++;
            }
            
			indexes[pages[i]] = i;
		}
	}
	cout<<"page_faults";
	cout<<page_faults<<"\nhits "<<hit<<"\n";
}

// Driver code
int main()
{
	int pages[] = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2};
	int n = sizeof(pages)/sizeof(pages[0]);
	int capacity = 4;
	pageFaults(pages, n, capacity);
	return 0;
}
