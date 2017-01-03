#include <bits/stdc++.h>
using namespace std;

#define ALL(x) x.begin(),x.end()
#define DBG(x) cout << #x << " = " << x << endl
typedef long long ll;
typedef pair<int,int> P;

const int MAXN = 100010;
const int INF =  1000000000;
int n;
int want[MAXN];
int ansLeft[MAXN];
int ansRight[MAXN];

int getp(int idx, int rotate) {
  return (want[idx%(n-1)]-rotate+n) % n;
}

int getn(int idx, int rotate) {
  return (n-want[idx%(n-1)]+rotate) % n;
}

void solve(vector<int> diff, int ans[MAXN]) {
  int dn = diff.size();
  for (int i = 0 ; i <= dn ; i++) {
    diff.push_back(diff[i]+n);
  }

  priority_queue< P, vector<P>, greater<P> > costs;
  for (int i = 0 ; i < diff.size()-1 ; i++) {
    int idx = diff[i];
    int next = (n-(diff[i+1]-diff[i]));
    costs.push(make_pair(idx+next, idx));
  }

  for (int i = 0 ; i < n ; i++) {
    while (true) {
      P row = costs.top();
      if (i <= row.second) {
        ans[i] = min(ans[i], row.first-i);
        break;
      }
      costs.pop();
    }
  }

  int di = 0;
  for (int i = 0 ; i < n ; i++) {
    ans[i] = min(ans[i], diff[di+dn-1]-i);
    if (diff[di] == i) {
      di++;
    }
  }
}

void solve_left() {
  vector<int> diff;
  for (int i = 0 ; i < n-1 ; i++) {
    diff.push_back((want[i] - (i + 1) + n) % n);
  }
  sort(ALL(diff));
  diff.erase(unique(ALL(diff)), diff.end());
  solve(diff, ansLeft);
}

void solve_right() {
  vector<int> diff;
  for (int i = 0 ; i < n-1 ; i++) {
    diff.push_back((i + 1 - want[i] + n) % n);
  }
  sort(ALL(diff));
  diff.erase(unique(ALL(diff)), diff.end());
  solve(diff, ansRight);
}

int main(int argc, char const *argv[]) {
  cin >> n;
  for (int i = 0; i < n-1 ; i++) {
    cin >> want[i];
    want[i]--;
  }
  for (int i = 0 ; i < n ; i++) {
    ansLeft[i] = ansRight[i] = INF;
  }
  solve_left();
  solve_right();
  for (int i = 0 ; i < n ; i++) {
    cout << min(ansLeft[i], ansRight[(n-i)%n]) + min(i, n-i) << endl;
  }
  return 0;
}
