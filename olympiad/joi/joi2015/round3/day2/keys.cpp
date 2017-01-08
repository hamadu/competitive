#include <bits/stdc++.h>
using namespace std;

#define ALL(x) x.begin(),x.end()

const int INF = 1100000000;
const int MAXN = 2010;
int n, m, k;
int staff[MAXN][2];
vector<pair<int,int>> events;
vector<pair<int,int>> graph[MAXN];
int value[MAXN];
bool visited[MAXN];
int dp[MAXN][MAXN][2];
int head;

void doit(int from) {
  int par = -1;
  while (true) {
    visited[from] = true;
    pair<int,int> edge;
    pair<int,int> ledge;
    bool hasPar = false;
    bool hasNext = false;

    // cerr << "===" << endl;
    // for (pair<int,int> e : graph[from]) {
    //   cerr << from << "," << e.first << ">>" << e.second << endl;
    // }

    for (pair<int,int> e : graph[from]) {
      if (e.first == par) {
        hasPar = true;
        ledge = e;
        continue;
      }
      hasNext = true;
      edge = e;
    }
    for (int ki = 0 ; ki <= k ; ki++) {
      for (int last = 0 ; last <= 1 ; last++) {
        int base = dp[head][ki][last];
        if (base == -1) {
          continue;
        }
        if (ki+1 <= k) {
          int add = base + value[from];
          if (hasPar && last == 1) {
            add += ledge.second;
          }
          int tl = hasNext ? 1 : 0;
          dp[head+1][ki+1][tl] = max(dp[head+1][ki+1][tl], add);
        }
        dp[head+1][ki][0] = max(dp[head+1][ki][0], base);
      }
    }
    if (!hasNext) {
      head++;
      break;
    }
    head++;
    par = from;
    from = edge.first;
  }
}

int solve() {
  for (int i = 0 ; i < n ; i++) {
    events.push_back(make_pair(staff[i][0], i+1));
    events.push_back(make_pair(staff[i][1], -(i+1)));
  }
  sort(ALL(events));

  memset(value, 0, sizeof(value));

  int en = events.size();
  int all = events[0].first + m - events[en-1].first;
  for (int i = 0 ; i+1 < en ; i++) {
    int e1 = events[i].second;
    int e2 = events[i+1].second;
    int span = events[i+1].first - events[i].first;
    if (abs(e1) == abs(e2)) {
      value[abs(e1)] += span;
      continue;
    }
    if (e1 >= 1 && e2 >= 1) {
      value[abs(e1)] += span;
    } else if (e1 >= 1 && e2 < 0) {
      graph[abs(e1)].push_back(make_pair(abs(e2), span));
      graph[abs(e2)].push_back(make_pair(abs(e1), span));
    } else if (e1 < 0 && e2 >= 1) {
      all += span;
    } else if (e1 < 0 && e2 < 0) {
      value[abs(e2)] += span;
    }
  }

  for (int i = 0 ; i <= n ; i++) {
    visited[i] = false;
  }

  memset(dp, -1, sizeof(dp));
  dp[0][0][0] = 0;
  head = 0;
  for (int i = 1 ; i <= n ; i++) {
    if (!visited[i] && graph[i].size() <= 1) {
      doit(i);
    }
  }

  int ret = 0;
  for (int ki = 0 ; ki <= k ; ki++) {
    for (int last = 0 ; last <= 1 ; last++) {
      ret = max(ret, dp[n][ki][last]);
    }
  }
  return ret+all;
}

int main(int argc, char const *argv[]) {
  cin >> n >> m >> k;
  for (int i = 0 ; i < n ; i++) {
    cin >> staff[i][0] >> staff[i][1];
  }
  cout << solve() << endl;
  return 0;
}
