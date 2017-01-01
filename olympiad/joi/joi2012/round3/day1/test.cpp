#include <bits/stdc++.h>
using namespace std;

#define ALL(x) x.begin(),x.end()
#define DBG(x) cout << #x << " = " << x << endl
typedef long long ll;
typedef pair<int,int> yx;
set<yx> hoge;

int main(int argc, char const *argv[]) {
  hoge.insert(make_pair(1,10));
  hoge.insert(make_pair(2,8));
  hoge.insert(make_pair(4,4));

  set<yx>::iterator it = hoge.lower_bound(make_pair(2,4));
  cout << (*it).first << " " << (*it).second << endl;

  set<yx>::iterator it2 = hoge.lower_bound(make_pair(2,12));
  cout << (*it2).first << " " << (*it2).second << endl;

  return 0;
}
