#include<bits/stdc++.h>
using namespace std;
const int N=1001;
int accumlator[N];

void first_fit(int n,vector<pair<int,int>> memo_box,int m,vector<int> process)
{
	for(int i=0;i<m;i++){
		int indx=-1;
		for(int j=0;j<n;j++){
			if(memo_box[j].first>=process[i]){
				indx=j;		
				break;
			}		
		}	
		if(indx!=-1){
			accumlator[i]=memo_box[indx].second;
			memo_box[indx].first-=process[i];
			memo_box[indx].second+=process[i];
					
		}
	}

	for(int i=0;i<n;i++){
		//cout<<memo_box[i].first<<" "<<memo_box[i].second<<'\n';	
	}
}

void worst_fit(int n,vector<pair<int,int>> memo_box,int m,vector<int> process)
{
	for(int i=0;i<m;i++){
		int indx=-1;
		for(int j=0;j<n;j++){
			if(memo_box[j].first>=process[i]){
				if(indx==-1)
				indx=j;	
				else if(memo_box[indx].first<memo_box[j].first) indx=j;	
			}		
		}	
		if(indx!=-1){
			accumlator[i]=memo_box[indx].second;
			memo_box[indx].first-=process[i];
			memo_box[indx].second+=process[i];	
		}
	}
	for(int i=0;i<n;i++){
		//cout<<memo_box[i].first<<" "<<memo_box[i].second<<'\n';	
	}
}

void best_fit(int n,vector<pair<int,int>> memo_box,int m,vector<int> process)
{
	for(int i=0;i<m;i++){
		int indx=-1;
		for(int j=0;j<n;j++){
			if(memo_box[j].first>=process[i]){
				if(indx==-1)
				indx=j;	
				else if(memo_box[indx].first>memo_box[j].first) indx=j;	
			}		
		}	
		if(indx!=-1){
			accumlator[i]=memo_box[indx].second;
			memo_box[indx].first-=process[i];
			memo_box[indx].second+=process[i];		
		}
	}
	for(int i=0;i<n;i++){
		//cout<<memo_box[i].first<<" "<<memo_box[i].second<<'\n';	
	}
}

void next_fit(int n,vector<pair<int,int>> memo_box,int m,vector<int> process)
{
	int temp=0;
	int loc=memo_box[0].second;
	for(int i=0;i<m;i++){
		int indx=-1;
		if(temp>=n){
			accumlator[i]=loc;
			loc+=process[i];		
		}
		while(temp<n){
			if(memo_box[temp].first>=process[i]){
				indx=temp;		
				break;
			}
			temp++;		
		}	
		if(indx!=-1){
			accumlator[i]=memo_box[indx].second;
			memo_box[indx].first-=process[i];
			memo_box[indx].second+=process[i];	
		}
	}
}

int main()
{
	int n;
	cin>>n;
	vector<pair<int,int>> memo_box(n);
	for(int i=0;i<n;i++)cin>>memo_box[i].first>>memo_box[i].second;
	vector<pair<int,int>>free_memo(n);
	for(int i=0;i<n;i++){
		int locS=memo_box[i].first,locE=memo_box[i+1].first,memo=memo_box[i].second;
		free_memo[i].first=locE-(locS+memo);
		free_memo[i].second=locS+memo;	
	}
	int m;
	cin>>m;
	vector<int> process(m);
	for(int i=0;i<m;i++)cin>>process[i];
	for(int i=0;i<m;i++)accumlator[i]=-1;
	first_fit(n,free_memo,m,process);
	cout<<"first_fit\n";
	for(int i=0;i<m;i++){
		if(accumlator[i]==-1)cout<<"Not enough space  ";
		else		
		cout<<accumlator[i]<<" ";
	}
	cout<<'\n';
	cout<<'\n';
	cout<<"best_fit\n";
	for(int i=0;i<m;i++)accumlator[i]=-1;
	best_fit(n,free_memo,m,process);
	for(int i=0;i<m;i++){
		if(accumlator[i]==-1)cout<<"Not enough space  ";
		else		
		cout<<accumlator[i]<<" ";
	}
	cout<<'\n';
		cout<<'\n';
	cout<<"worst_fit\n";
	for(int i=0;i<m;i++)accumlator[i]=-1;
	worst_fit(n,free_memo,m,process);
	for(int i=0;i<m;i++){
		if(accumlator[i]==-1)cout<<"Not enough space  ";
		else		
		cout<<accumlator[i]<<" ";
	}
		cout<<'\n';
	cout<<'\n';
	cout<<"next_fit\n";
	for(int i=0;i<m;i++)accumlator[i]=-1;
	next_fit(n,free_memo,m,process);
	for(int i=0;i<m;i++){
		if(accumlator[i]==-1)cout<<"Not enough space  ";
		else		
		cout<<accumlator[i]<<" ";
	}
	cout<<'\n';
}
//
