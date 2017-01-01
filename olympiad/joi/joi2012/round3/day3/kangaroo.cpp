#include <bits/stdc++.h>
using namespace std;

#define ALL(x) x.begin(),x.end()
#define DBG(x) cout << #x << " = " << x << endl
typedef long long ll;

const ll MOD = 1000000007;
const int MAXN = 310;
int n;
int a[MAXN];
int b[MAXN];
int rightTo[MAXN+1];
ll fact[MAXN];

void doit(int from, int to, ll dp[MAXN][MAXN]) {
  int len = to - from;
  dp[0][0] = 1;
  for (int i = 0 ; i < len ; i++) {
    for (int u = 0 ; u < len ; u++) {
      if (dp[i][u] == 0) {
        continue;
      }
      ll base = dp[i][u];
      ll choice = rightTo[to] - rightTo[to-1-i] - u;
      dp[i+1][u] = (dp[i+1][u] + base) % MOD;
      dp[i+1][u+1] = (dp[i+1][u+1] + base * choice % MOD) % MOD;
    }
  }
}

ll solve() {
  fact[0] = 1;
  for (int i = 1 ; i < MAXN ; i++) {
    fact[i] = (fact[i-1] * i) % MOD;
  }
  for (int top = 0 ; top < n ; top++) {
    int hoge = n;
    for (int i = 0 ; i < n ; i++) {
      if (a[top] < b[i]) {
        hoge = i;
        break;
      }
    }
    rightTo[top] = hoge;
  }
  rightTo[n] = n;

  ll sum = 0;
  for (int top = 0 ; top < n ; top++) {
    // match [0,top),[top+1,n) and [0,bottom[top]),[bottom[top],n)
    ll dpLeft[MAXN][MAXN];
    memset(dpLeft, 0, sizeof(dpLeft));
    doit(0, top, dpLeft);

    ll dpRight[MAXN][MAXN];
    memset(dpRight, 0, sizeof(dpRight));
    doit(top+1, n, dpRight);

    // we should count situations s.t.
    // - each kangaroo [0,top) is included in a kangaroo.
    // - each kangaroo [rightTo[top], n) includes a kangaroo.
    for (int j = 0 ; j <= top ; j++) {
      int nokori = n - rightTo[top] - j;
      if (nokori >= 0) {
        ll add = dpLeft[top][top-j] * dpRight[n-1-top][nokori] % MOD * fact[j] % MOD;
        sum += add;
      }
    }
  }
  return sum % MOD;
}

int main(int argc, char const *argv[]) {
  cin >> n;
  for (int i = 0;  i < n ; i++) {
    cin >> a[i] >> b[i];
  }
  sort(a, a+n);
  sort(b, b+n);

  cout << solve() << endl;
  return 0;
}
