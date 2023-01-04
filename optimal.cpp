#include <bits/stdc++.h>
using namespace std;

int predict(int pg[], vector<int>& fr, int pn, int index)
{
	int res = -1, farthest = index;
	for (int i = 0; i < fr.size(); i++) {
		int j;
		for (j = index; j < pn; j++) {
			if (fr[i] == pg[j]) {
				if (j > farthest) {
					farthest = j;
					res = i;
				}
				break;
			}
		}

		if (j == pn)
			return i;
	}

	// return (res == -1) ? 0 : res;

    if(res==-1)return 0;
    else return res;
}

void optimalPage(int pg[], int pn, int fn)
{
	vector<int> fr;

	int hit = 0;
	for (int i = 0; i < pn; i++) {

		if (find(fr.begin(),fr.end(),pg[i])!=fr.end()) {
			hit++;
		} 
        else if (fr.size() < fn)
			fr.push_back(pg[i]);
		else {
			int j = predict(pg, fr, pn, i + 1);
			fr[j] = pg[i];
		}
	}
	cout << "No. of hits = " << hit << endl;
	cout << "No. of misses = " << pn - hit << endl;
    
}

int main()
{
	int pg[] = { 7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2 };
	int pn = sizeof(pg) / sizeof(pg[0]);
	int fn = 4;
	optimalPage(pg, pn, fn);
	return 0;
}

