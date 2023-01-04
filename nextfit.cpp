
#include <bits/stdc++.h>
using namespace std;

// Function to allocate memory to blocks as per Next fit
// algorithm
void NextFit(int blockSize[], int m, int processSize[], int n)
{
	// Stores block id of the block allocated to a
	// process
	int allocation[n], j = 0;

	// Initially no block is assigned to any process
	memset(allocation, -1, sizeof(allocation));

	// pick each process and find suitable blocks
	// according to its size ad assign to it
	for (int i = 0; i < n; i++) {

		// Do not start from beginning
		while (j < m) {

			if (blockSize[j] >= processSize[i]) {

				// allocate block j to p[i] process
				allocation[i] = j;

				// Reduce available memory in this block.
				blockSize[j] -= processSize[i];

				break;
			}

			// mod m will help in traversing the blocks from
			// starting block after we reach the end.
			j = (j + 1) % m;
		}
	}

	cout << "\nProcess No.\tProcess Size\tBlock no.\n";
	for (int i = 0; i < n; i++) {
		cout << " " << i + 1 << "\t\t" << processSize[i]
			<< "\t\t";
		if (allocation[i] != -1)
			cout << allocation[i] + 1;
		else
			cout << "Not Allocated";
		cout << endl;
	}
}

// Driver program
int main()
{
	int blockSize[] = { 5, 10, 20 };
	int processSize[] = { 10, 20, 5 };
	int m = sizeof(blockSize) / sizeof(blockSize[0]);
	int n = sizeof(processSize) / sizeof(processSize[0]);

	NextFit(blockSize, m, processSize, n);

	return 0;
}
