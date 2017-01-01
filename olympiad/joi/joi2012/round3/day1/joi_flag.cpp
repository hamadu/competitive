#include <bits/stdc++.h>
using namespace std;

#define ALL(x) x.begin(),x.end()
#define DBG(x) cout << #x << " = " << x << endl
typedef long long ll;
const int INF = 1000000010;

const int MAXF = 150000;
int cost[MAXF][4]; // j,o,i,joi
int flag[MAXF][5]; // level
int fn = 0;

const int MAXN = 1024;
int k,n;
int perm[24][4];

int touch() {
  return fn++;
}

void make(int idx, int level, int fx, int fy, int tx, int ty, int x, int y, int ch) {
  bool inplace = (fx <= x && x < tx && fy <= y && y < ty);
  flag[idx][0] = level;
  if (level >= 1) {
    if (!inplace) {
      return;
    }
    if (flag[idx][1] == -1) {
      flag[idx][1] = touch(); // TL
      flag[idx][2] = touch(); // TR
      flag[idx][3] = touch(); // BL
      flag[idx][4] = touch(); // BR
    }
    const int cids[4] = {flag[idx][1], flag[idx][2], flag[idx][3], flag[idx][4]};
    const int medX = (fx+tx)/2;
    const int medY = (fy+ty)/2;
    make(flag[idx][1], level-1, fx, fy, medX, medY, x, y, ch);
    make(flag[idx][2], level-1, medX, fy, tx, medY, x, y, ch);
    make(flag[idx][3], level-1, fx, medY, medX, ty, x, y, ch);
    make(flag[idx][4], level-1, medX, medY, tx, ty, x, y, ch);

    for (int f = 0 ; f <= 2 ; f++) {
      cost[idx][f] = 0;
      for (int c = 0 ; c < 4 ; c++) {
        cost[idx][f] += cost[cids[c]][f];
      }
    }

    int minCost = INF;
    for (int p = 0 ; p < 24 ; p++) {
      int a = cids[perm[p][0]];
      int b = cids[perm[p][1]];
      int c = cids[perm[p][2]];
      int d = cids[perm[p][3]];
      minCost = min(minCost, cost[a][0] + cost[b][1] + cost[c][2] + cost[d][3]);
    }
    cost[idx][3] = minCost;
  } else {
    if (inplace) {
      cost[idx][0] = cost[idx][1] = cost[idx][2] = 1;
      cost[idx][ch] = cost[idx][3] = 0;
    }
  }
}

int main(int argc, char const *argv[]) {
  int pid = 0;
  for (int i = 0 ; i < 4 ; i++) {
    for (int j = 0 ; j < 4 ; j++) {
      for (int k = 0 ; k < 4 ; k++) {
        for (int l = 0; l < 4 ; l++) {
          if (i == j || i == k || i == l || j == k || j == l || k == l) {
            continue;
          }
          perm[pid][0] = i;
          perm[pid][1] = j;
          perm[pid][2] = k;
          perm[pid][3] = l;
          pid++;
        }
      }
    }
  }

  for (int i = 0 ; i < MAXF ; i++) {
    cost[i][0] = cost[i][1] = cost[i][2] = cost[i][3] = 0;
    flag[i][1] = -1;
  }
  cin >> k >> n;
  fn = 1;
  for (int i = 0 ; i < n ; i++) {
    int x,y;
    char c;
    cin >> x >> y >> c;
    make(0, k, 0, 0, 1<<k, 1<<k, x-1, y-1, ((c == 'J') ? 0 : (c == 'O' ? 1 : 2)));
  }
  cout << cost[0][3] << endl;

  return 0;
}
