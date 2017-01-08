#include <bits/stdc++.h>
using namespace std;

const int MAXN = 505;
int n;
int cards[MAXN][3];
int dp[2][MAXN][MAXN][2];
bool match[MAXN][MAXN];

int solve() {
  memset(match, false, sizeof(match));
  for (int i = 0 ; i < n; i++) {
    for (int j = 0 ; j < n ; j++) {
      if (cards[i][0] == cards[j][0] || cards[i][1] == cards[j][1]) {
        match[i][j] = true;
      }
    }
  }

  memset(dp, -1, sizeof(dp));
  dp[0][1][1][0] = 0;

  int bestScore = 0;
  for (int i = 0 ; i <= n ; i++) {
    int fr = i % 2;
    int to = 1 - fr;
    int left = n-i;
    memset(dp[to], -1, sizeof(dp[to]));
    for (int l1 = 0 ; l1 <= n ; l1++) {
      for (int l2 = 0 ; l2 <= n ; l2++) {
        for (int flg = 0 ; flg <= 1 ; flg++) {
          if (dp[fr][l1][l2][flg] == -1) {
            continue;
          }
          int base = dp[fr][l1][l2][flg];
          bestScore = max(bestScore, base);
          if (i == n) {
            continue;
          }

          int top = -1;
          int idx1 = 0;
          int idx3 = 2;
          int wl2 = l2;
          if (i >= 1) {
            top =  (flg == 1) ? l2 : 2+i-1;
            idx1 = (flg == 1) ? 2+i-1-l1 : 2+i-l1-l2;
            wl2 =  (flg == 1) ? 1 : l2;
            idx3 = 2+i;
          }
          if (left >= 1 && (top == -1 || match[top][idx1])) {
            dp[to][wl2][idx1][1] = max(dp[to][wl2][idx1][1], base+cards[idx1][2]);
          }
          if (left >= 3 && (top == -1 || match[top][idx3])) {
            dp[to][l1][wl2+1][0] = max(dp[to][l1][wl2+1][0], base+cards[idx3][2]);
          }
        }
      }
    }
  }
  return bestScore;
}

int main(int argc, char const *argv[]) {
  cin >> n;
  for (int i = 0 ; i < n ; i++) {
    for (int j = 0; j < 3; j++) {
      cin >> cards[i][j];
    }
  }
  cout << solve() << endl;
  return 0;
}
