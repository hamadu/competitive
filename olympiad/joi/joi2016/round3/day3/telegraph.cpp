#include <bits/stdc++.h>
using namespace std;

typedef long long ll;
const int MAXN = 100010;
const ll INF = 10000000000LL;
int n;
int from[MAXN];
int value[MAXN];
vector<pair<int,int>> graph[MAXN];
int visited[MAXN];
int csize[MAXN];
int cid[MAXN];
bool done[MAXN];
bool tried[MAXN];
ll totalCost[MAXN];

// === Union-Find ===
int uf_rank[MAXN];
int uf_parent[MAXN];
int uf_cnt[MAXN];
void uf_init(int n) {
  for (int i = 0 ; i < n ; i++) {
    uf_rank[i] = 0;
    uf_parent[i] = i;
    uf_cnt[i] = 1;
  }
}

int uf_find(int a) {
  if (uf_parent[a] == a) {
    return a;
  }
  uf_parent[a] = uf_find(uf_parent[a]);
  return uf_parent[a];
}

void uf_unite(int a, int b) {
  a = uf_find(a);
  b = uf_find(b);
  if (a == b) {
    return;
  }
  if (uf_rank[a] < uf_rank[b]) {
    uf_parent[a] = b;
    uf_cnt[b] += uf_cnt[a];
    uf_cnt[a] = uf_cnt[b];
  } else {
    uf_parent[b] = a;
    uf_cnt[a] += uf_cnt[b];
    uf_cnt[b] = uf_cnt[a];
    if (uf_rank[a] == uf_rank[b]) {
        uf_rank[a]++;
    }
  }
}

bool uf_issame(int a, int b) {
  return uf_find(a) == uf_find(b);
}
// ===

ll solve() {
  memset(visited, -1, sizeof(visited));
  memset(csize, -1, sizeof(csize));
  memset(cid, -1, sizeof(cid));
  memset(done, false, sizeof(done));
  memset(tried, false, sizeof(done));
  memset(totalCost, 0, sizeof(totalCost));
  for (int i = 0 ; i < n ; i++) {
    for (pair<int,int> e : graph[i]) {
      totalCost[i] += e.second;
    }
  }

  uf_init(n);
  for (int i = 0 ; i < n ; i++) {
    uf_unite(i, from[i]);
  }

  int ckind = 0;
  int maxSize = 0;
  for (int i = 0 ; i < n ; i++) {
    if (visited[i] != -1 || tried[uf_find(i)]) {
      continue;
    }
    tried[uf_find(i)] = true;
    int now = i;
    int time = 0;
    while (visited[now] == -1) {
      visited[now] = time;
      now = from[now];
      time++;
    }
    if (csize[now] == -1) {
      int cs = time - visited[now];
      maxSize = max(maxSize, cs);
      for (int k = 0 ; k < cs ; k++) {
        csize[now] = cs;
        cid[now] = ckind;
        now = from[now];
      }
      ckind++;
    }
  }
  if (maxSize == n) {
    return 0;
  }

  ll minCost = 0;
  for (int i = 0 ; i < n ; i++) {
    if (cid[i] == -1) {
      ll maxWeight = 0;
      for (pair<int,int> e : graph[i]) {
        maxWeight = max(maxWeight, (ll)e.second);
      }
      minCost += totalCost[i] - maxWeight;
    } else {
      if (done[cid[i]]) {
        continue;
      }
      done[cid[i]] = true;

      int head = i;
      ll cycleCost = 0;
      ll minCycleCutter = INF;
      ll minCycleEdge = INF;
      bool hasCutCycle = false;
      for (int k = 0 ; k < csize[i] ; k++) {
        ll nonCycleMax = 0;
        ll cycleMax = 0;
        for (pair<int,int> e : graph[head]) {
          if (cid[e.first] == -1) {
            nonCycleMax = max(nonCycleMax, (ll)e.second);
          } else {
            minCycleEdge = min(minCycleEdge, (ll)e.second);
            cycleMax = max(cycleMax, (ll)e.second);
          }
        }
        if (nonCycleMax >= 1) {
          if (nonCycleMax <= cycleMax) {
            cycleCost += totalCost[head] - cycleMax;
            minCycleCutter = min(minCycleCutter, cycleMax - nonCycleMax);
          } else {
            cycleCost += totalCost[head] - nonCycleMax;
            hasCutCycle = true;
          }
        }
        head = from[head];
      }
      if (!hasCutCycle) {
        cycleCost += min(minCycleEdge, minCycleCutter);
      }
      minCost += cycleCost;
    }
  }
  return minCost;
}

int main(int argc, char const *argv[]) {
  scanf("%d", &n);
  for (int i = 0 ; i < n ; i++) {
    scanf("%d %d", &from[i], &value[i]);
    from[i]--;
    graph[from[i]].push_back(make_pair(i, value[i]));
  }

  cout << solve() << endl;
  /* code */
  return 0;
}
