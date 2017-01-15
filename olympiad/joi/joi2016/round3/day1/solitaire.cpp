#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
const int MOD = 1000000007;
const int MAXN = 4010;
int n;
int board[3][MAXN];
ll ncr[MAXN][MAXN];
ll fact[MAXN];

ll dp[MAXN][MAXN][2];
ll prevSum[MAXN][2];

ll way(int fr, int to, int total_places) {
  int L = to-fr;
  memset(dp[1], 0, sizeof(dp[1]));

  int prev_total = 0;
  for (int x = 0 ; x <= 2 ; x++) {
    prev_total += board[x][fr];
  }
  for (int after = 0 ; after <= prev_total-1 ; after++) {
    if (after == 0) {
      dp[1][prev_total-after-1][0] = fact[prev_total-1];
    } else {
      dp[1][prev_total-after-1][1] = fact[prev_total-1];
    }
  }


  for (int i = 2 ; i <= L ; i++) {
    memset(dp[i], 0, sizeof(dp[i]));

    prevSum[0][0] = prevSum[0][1] = 0;
    for (int j = 0 ; j <= total_places ; j++) {
      for (int k = 0 ; k <= 1 ; k++) {
        prevSum[j+1][k] = (prevSum[j][k] + dp[i-1][j][k]) % MOD;
      }
    }

    int here = 0;
    for (int x = 0 ; x <= 2 ; x++) {
      here += board[x][fr+i-1];
    }
    int otomo = here-1;
    int total = prev_total + here;

    for (int j = 0 ; j < total ; j++) {
      if (j >= otomo) {
        ll last = (prevSum[total][0] + prevSum[total][1] - prevSum[j-otomo][1] + MOD) % MOD;
        // ll last = 0;
        // for (int prev = 0 ; prev <= total ; prev++) {
        //   last += dp[i-1][prev][0];
        //   if (j-otomo <= prev) {
        //     last += dp[i-1][prev][1];
        //   }
        // }
        dp[i][j][0] = last * fact[otomo] % MOD * ncr[j][otomo] % MOD;
      }

      ll nonlast = 0;
      for (int after = 1 ; after <= otomo ; after++) {
        int before = otomo - after;
        ll ptn = ncr[j][before] % MOD * ncr[total-1-j][after] % MOD * fact[otomo] % MOD;
        ll sum = (j >= before) ? prevSum[j-before][0] : 0;
        // ll sum = 0;
        // for (int prev = 0 ; prev < j-before ; prev++) {
        //   sum += dp[i-1][prev][0];
        // }
        sum %= MOD;
        nonlast += ptn * sum % MOD;
        nonlast %= MOD;
      }
      dp[i][j][1] = nonlast;
    }
    prev_total = total;
  }

  ll sum = 0;
  for (int i = 0 ; i <= total_places ; i++) {
    sum += dp[L][i][0] + dp[L][i][1];
    sum %= MOD;
  }
  return sum;
}

ll solve() {
  if (board[0][0] == 1 || board[0][n-1] == 1 || board[2][0] == 1 || board[2][n-1] == 1) {
    return 0;
  }
  for (int i = 0 ; i+1 < n ; i++) {
    if (board[0][i] == 1 && board[0][i+1] == 1) {
      return 0;
    }
    if (board[2][i] == 1 && board[2][i+1] == 1) {
      return 0;
    }
  }

  int fr = 0;
  int left_empty = 0;
  for (int i = 0 ; i < 3; i++) {
    for (int j = 0 ; j < n ; j++) {
      left_empty += board[i][j];
    }
  }

  for (int i = 0 ; i < MAXN ; i++) {
    ncr[i][0] = ncr[i][i] = 1;
    for (int j = 1 ; j < MAXN ; j++) {
      ncr[i][j] = (ncr[i-1][j-1] + ncr[i-1][j]) % MOD;
    }
  }

  fact[0] = 1;
  for (int i = 1 ; i < MAXN ; i++) {
    fact[i] = (fact[i-1] * i) % MOD;
  }

  ll ret = 1;
  while (fr < n) {
    int consume = 0;
    if (board[1][fr] == 0) {
      consume = board[0][fr] + board[2][fr];
      ret *= ncr[left_empty][consume] * fact[consume] % MOD;
      ret %= MOD;
      left_empty -= consume;
      fr++;
      continue;
    }

    int to = fr;
    while (to < n && board[1][to] == 1) {
      for (int x = 0 ; x <= 2 ; x++) {
        consume += board[x][to];
      }
      to++;
    }

    ret *= way(fr, to, consume) * ncr[left_empty][consume] % MOD;
    ret %= MOD;
    left_empty -= consume;
    fr = to;
  }
  return ret;
}

int main(int argc, char const *argv[]) {
  cin >> n;
  for (int i = 0 ; i < 3; i++) {
    string s;
    cin >> s;
    for (int j = 0 ; j < n ; j++) {
      board[i][j] = s[j] == 'o' ? 0 : 1;
    }
  }
  cout << solve() << endl;
  return 0;
}
