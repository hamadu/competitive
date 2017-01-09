#include "navigation_lib.h"
#include <bits/stdc++.h>
using namespace std;

const int MAXN = 100010;

//== A
vector<int> graph[MAXN];

void dfs(int now, int v, int par) {
  Flag(now, v);

  for (int to : graph[now]) {
    if (to == par) {
      continue;
    }
    dfs(to, (to < now) ? v^1 : v, now);
  }
}

void Anna(int K, int N, int T, int A[], int B[]) {
  for (int i = 0 ; i < N-1 ; i++) {
    graph[A[i]].push_back(B[i]);
    graph[B[i]].push_back(A[i]);
  }
  dfs(T, 0, -1);
}

//== B
void Bruno(int K, int S, int F, int L, int P[], int Q[]) {
  int idx = -1;
  for (int i = 0 ; i < L ; i++) {
    if (S < P[i] && F != Q[i]) {
      idx = i;
    } else if (S > P[i] && F == Q[i]) {
      idx = i;
    }
  }
  Answer(idx == -1 ? S : P[idx]);
}
