#include <bits/stdc++.h>
using namespace std;

#define ALL(x) x.begin(),x.end()

const int MAXN = 200010;
const int MAXM = 200010;
int n, m;
int value[MAXN];
int query[MAXM][3];
int ans[MAXM];

// ===
typedef set<pair<int,int>> ss;
ss segments;

void segment_init() {
  segments.insert(make_pair(-1, -1));
  segments.insert(make_pair(MAXN, MAXN));
}

void segment_add(int p) {
  pair<int,int> mp = make_pair(p,p);
  ss::iterator left = segments.lower_bound(mp);
  if (*left.second)


}
// ===

void solve() {
  memset(ans, -1, sizeof(ans));

  int BUCKET = 500;
  for (int i = 0 ; i < m ; i += BUCKET) {
    vector<int> cng;
    vector<pair<int,int>> que;
    for (int k = i ; k < min(m, i+BUCKET) ; k++) {
      if (query[k][0] == 2) {
        cng.push_back(k);
      } else {
        que.push_back(make_pair(-query[k][0], k));
      }
    }
    sort(ALL(que));

    for (pair<int,int> q : que) {
      int h = -q.first;
      int v = q.second;



    }



    // apply all changes
    for (int k : cng) {
      value[query[k][1]] = query[k][2];
    }
  }

  for (int i = 0;  i < m ; i++) {
    if (ans[i] != -1) {
      cout << ans[i] << endl;
    }
  }

}

int main(int argc, char const *argv[]) {
  scanf("%d %d", &n, &m);
  for (int i = 0 ; i < n ; i++) {
    scanf("%d", &value[i]);
  }
  for (int i = 0 ; i < m ; i++) {
    scanf("%d %d", &query[i][0], &query[i][1]);
    if (query[i][0] == 2) {
      query[i][1]--;
      scanf("%d", &query[i][2]);
    }
  }
  solve();
  return 0;
}
