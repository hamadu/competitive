#include <bits/stdc++.h>
using namespace std;

const int INF = 1000000000;
const int MAXL = 1048576;
const int MAXL2 = 1048576 * 2 + 10;
int k;
int n;
string s;
int J[MAXL2];
int O[MAXL2];
int I[MAXL2];
int dp[2][MAXL];

int solve() {
  J[0] = O[0] = I[0] = 0;
  for (int i = 0 ; i < 2*n ; i++) {
    char cc = s[i%n];
    J[i+1] = J[i] + (cc == 'J' ? 1 : 0);
    O[i+1] = O[i] + (cc == 'O' ? 1 : 0);
    I[i+1] = I[i] + (cc == 'I' ? 1 : 0);
  }

  memset(dp, 0, sizeof(dp));
  for (int ki = 1 ; ki <= k ; ki++) {
    int to = ki%2;
    int fr = 1-to;
    int per = 1<<((ki-1)*2);
    for (int i = 0 ; i < n ; i++) {
      int cost = 0;
      cost += per-(J[i+per]  -J[i]);
      cost += per-(O[i+per*2]-O[i+per]);
      cost += per-(I[i+per*3]-I[i+per*2]);
      cost += dp[fr][(i+per*3)%n];
      dp[to][i] = cost;
    }
  }

  int ret = INF;
  for (int i = 0 ; i < n ; i++) {
    ret = min(ret, dp[k%2][i]);
  }
  return ret;
}

int main(int argc, char const *argv[]) {
  cin >> k;
  n = 1<<(2*k);
  cin >> s;

  cout << solve() << endl;
  return 0;
}
