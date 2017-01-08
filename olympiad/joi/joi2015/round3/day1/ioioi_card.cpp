#include <bits/stdc++.h>
using namespace std;

typedef long long ll;
typedef pair<ll, int> State;

const ll INF = 1000000000000000000LL;
const int MAXN = 100010;
const int MAXV = 500010;

int A;
int B;
int C;
int D;
int E;
int N;
int op[MAXN][2];
vector<int> graph[MAXV];
priority_queue<State, vector<State>, greater<State>> que;
ll dp[MAXV];

void path(int from) {
  for (int i = 0 ; i < MAXV ; i++) {
    dp[i] = INF;
  }
  dp[from] = 0;
  que.push(make_pair(0LL, from));
  while (!que.empty()) {
    State head = que.top();
    que.pop();
    ll cost = head.first;
    int now = head.second;
    if (dp[now] < cost) {
      continue;
    }
    for (int to : graph[now]) {
      ll toCost = cost + abs(now-to);
      if (dp[to] > toCost) {
        dp[to] = toCost;
        que.push(make_pair(toCost, to));
      }
    }
  }
}

ll solve() {
  for (int i = 0 ; i < N ; i++) {
    graph[op[i][0]].push_back(op[i][1]);
    graph[op[i][1]].push_back(op[i][0]);
  }

  path(A);
  ll ab = dp[A+B];
  ll ac = dp[A+B+C];
  ll ad = dp[A+B+C+D];
  path(A+B);
  ll bc = dp[A+B+C];
  ll bd = dp[A+B+C+D];
  path(A+B+C);
  ll cd = dp[A+B+C+D];

  ll ret = INF;
  ret = min(ret, ab+cd);
  ret = min(ret, ac+bd);
  ret = min(ret, ad+bc);
  return ret >= INF ? -1 : ret;
}

int main(int argc, char const *argv[]) {
  cin >> A >> B >> C >> D >> E;
  cin >> N;
  for (int i = 0; i < N ; i++) {
    cin >> op[i][0] >> op[i][1];
    op[i][0]--;
  }
  cout << solve() << endl;
  return 0;
}
