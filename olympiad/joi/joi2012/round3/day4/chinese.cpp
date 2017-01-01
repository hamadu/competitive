#include <bits/stdc++.h>
using namespace std;

#define ALL(x) x.begin(),x.end()
#define DBG(x) cout << #x << " = " << x << endl
typedef long long ll;

const int MAXN = 100010;
int n;
int want[MAXN];
int ans[MAXN];

int getp(int idx, int rotate) {
  return (want[idx%(n-1)]-rotate+n) % n;
}

int getn(int idx, int rotate) {
  return (n-want[idx%(n-1)]+rotate) % n;
}

void solve() {
  for (int i = 0 ; i < n-1 ; i++) {
    want[i]--;
    want[i] = (want[i] - (i + 1) + n) % n;
  }
  sort(want, want+n-1);

  int pmax = 0;
  int nmax = 0;
  int half = -1;
  for (int i = 0 ; i < n-1 ; i++) {
    if (getp(pmax, 0) <= getp(i, 0)) {
      pmax = i;
    }
    if (getn(nmax, 0) <= getn(i, 0)) {
      nmax = i;
    }
    if (getp(i, 0) < n/2) {
      if (half == -1 || getp(half, 0) <= getp(i, 0)) {
        half = i;
      }
    }
  }
  if (half == -1) {
    half = 0;
    for (int i = 0 ; i < n-1 ; i++) {
      if (getp(half, 0) == getp(i, 0)) {
        half = i;
      } else {
        break;
      }
    }
  }

  for (int k = 0 ; k < n ; k++) {
    int ans = min(k, n-k);
    for (int i = 0 ; i < n-1 ; i++)  {
      DBG(getp(i, k));
    }
    DBG(getp(half, k));
    DBG(getn(half+1, k));
    DBG("===");

    int best = min(getp(pmax, k), getn(nmax, k));
    int goleft = getp(half, k);
    int goright = getn(half+1, k);
    if (goleft <= n/2 && goright <= n-n/2) {
      best = min(best, min(goleft, goright)*2+max(goleft, goright));
    }

    cout << ans + best << endl;

    // rotate
    if (want[0] != want[n-2]) {
      int nowPmax = getp(pmax, k+1);
      while (true) {
        int nextPmax = getp(pmax+1, k+1);
        if (nowPmax <= nextPmax) {
          nowPmax = nextPmax;
          pmax++;
        } else {
          break;
        }
      }

      int nowNmax = getn(nmax, k+1);
      while (true) {
        int nextNmax = getn(nmax+1, k+1);
        if (nowNmax <= nextNmax) {
          nowNmax = nextNmax;
          nmax++;
        } else {
          break;
        }
      }

      int nowNhalf = getp(half, k+1);
      while (true) {
        int nextNhalf = getp(half+1, k+1);
        if (nowNhalf >= n/2 || (nowNhalf <= nextNhalf && nextNhalf < n/2)) {
          nowNhalf = nextNhalf;
          half++;
        } else {
          break;
        }
      }
    }
  }
}


int main(int argc, char const *argv[]) {
  cin >> n;
  for (int i = 0; i < n-1 ; i++) {
    cin >> want[i];
  }
  solve();
  return 0;
}
