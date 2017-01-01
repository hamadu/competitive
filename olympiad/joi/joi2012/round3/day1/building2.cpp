#include <bits/stdc++.h>
using namespace std;

#define ALL(x) x.begin(),x.end()

const int MAXN = 100010;
int n,h[MAXN];
vector<int> graph[MAXN];
vector<int> up[MAXN],dw[MAXN];
int ptr[MAXN];
int sub[MAXN];

int getup(int v, int h) {
  return lower_bound(ALL(up[v]), h) - up[v].begin();
}

int getdown(int v, int h) {
  return lower_bound(ALL(dw[v]), h, greater<int>()) - dw[v].begin();
}

int dfs(int now, int par=-1) {
  int ret = 0;
  sub[now] = 1;
  bool hasChild = false;
  for (int to : graph[now]) {
    if (to == par) {
      continue;
    }
    hasChild = true;
    ret = max(ret, dfs(to, now));
    sub[now] += sub[to];
  }
  if (!hasChild) {
    up[now].push_back(h[now]);
    dw[now].push_back(h[now]);
    return 1;
  }

  // find largest child
  int bigChild = -1;
  for (int to : graph[now]) {
    if (to == par) {
      continue;
    }
    if (bigChild == -1 || sub[bigChild] < sub[to]) {
      bigChild = to;
    }
  }
  ptr[now] = ptr[bigChild];
  int my = ptr[now];

  // up + my + dw
  int cn = graph[now].size();
  int upmax = 0;
  int dwmax = 0;
  for (int i = 0 ; i < cn ; i++) {
    int to = graph[now][i];
    if (to == par) {
      continue;
    }
    int upcnt = getup(ptr[to], h[now]);
    int dwcnt = getdown(ptr[to], h[now]);
    ret = max(ret, upmax+dwcnt+1);
    ret = max(ret, dwmax+upcnt+1);
    upmax = max(upmax, upcnt);
    dwmax = max(dwmax, dwcnt);
  }

  // up + dw
  for (int i = 0 ; i < cn ; i++) {
    int to = graph[now][i];
    if (to == par || to == bigChild) {
      continue;
    }
    int ot = ptr[to];
    int upcnt = up[ot].size();
    int dwcnt = dw[ot].size();
    for (int l = 0 ; l < dwcnt ; l++) {
      ret = max(ret, getup(my, dw[ot][l])+l+1);
    }
    for (int l = 0 ; l < upcnt ; l++) {
      ret = max(ret, getdown(my, up[ot][l])+l+1);
    }
  }

  // merge
  for (int i = 0 ; i < cn ; i++) {
    int to = graph[now][i];
    if (to == par || to == bigChild) {
      continue;
    }
    int ot = ptr[to];
    int myupcnt = up[my].size();
    int upcnt = up[ot].size();
    for (int l = 0 ; l < min(upcnt, myupcnt) ; l++) {
      up[my][l] = min(up[my][l], up[ot][l]);
    }
    for (int l = myupcnt ; l < upcnt ; l++) {
      up[my].push_back(up[ot][l]);
    }

    int mydwcnt = dw[my].size();
    int dwcnt = dw[ot].size();
    for (int l = 0 ; l < min(dwcnt, mydwcnt) ; l++) {
      dw[my][l] = min(dw[my][l], dw[ot][l]);
    }
    for (int l = mydwcnt ; l < dwcnt ; l++) {
      dw[my].push_back(dw[ot][l]);
    }
  }

  int upidx = getup(my, h[now]);
  if (upidx < up[my].size()) {
    up[my][upidx] = h[now];
  } else {
    up[my].push_back(h[now]);
  }

  int dwidx = getdown(my, h[now]);
  if (dwidx < dw[my].size()) {
    dw[my][dwidx] = h[now];
  } else {
    dw[my].push_back(h[now]);
  }
  return ret;
}

int main(int argc, char const *argv[]) {
  cin >> n;
  for (int i = 0; i < n ; i++) {
    cin >> h[i];
  }
  for (int i = 0 ; i < n-1 ; i++) {
    int u, v;
    cin >> u >> v;
    u--;
    v--;
    graph[u].push_back(v);
    graph[v].push_back(u);
  }
  for (int i = 0; i < n ; i++) {
    ptr[i] = i;
  }

  cout << dfs(0) << endl;
  return 0;
}
