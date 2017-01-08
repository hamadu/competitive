#include <bits/stdc++.h>
using namespace std;

typedef long long ll;

const int MAXN = 1000010;
int has[MAXN];
int b[MAXN];
int n;

ll solve() {
  for (int i = 0 ; i < MAXN ; i++) {
    has[i] = MAXN;
  }
  has[0] = -1;

  int canhave = 1;
  for (int i = 0 ; i < n ; i++) {
    if (b[i] >= canhave+2) {
      return 0;
    }
    canhave = max(canhave, b[i]+1);
  }

  int ml = 0;
  for (int i = 0 ; i < n ; i++) {
    ml = max(ml, b[i]);
    has[b[i]] = min(has[b[i]], i);
  }

  int h = -1;
  int req = -1;
  for (int x = 1 ; x+1 <= ml ; x++) {
    if (has[x] >= has[x+1]) {
      if (h != -1) {
        return 0;
      }
      h = x;
      req = has[x+1];
    }
  }


  ll sum = 0;
  if (h == -1) {
    int canPlaceUpTo = 1;
    for (int i = 0 ; i < n ; i++) {
      sum += canPlaceUpTo;
      if (b[i] == canPlaceUpTo) {
        canPlaceUpTo++;
      }
    }
    sum += canPlaceUpTo;

    // remove the duplicated list
    for (int i = 0 ; i < n ; ) {
      int j = i;
      while (j < n && b[i] == b[j]) {
        j++;
      }
      sum -= j-i;
      i = j;
    }
  } else {
    int canPlaceUpTo = 1;
    for (int i = 0 ; i < n ; i++) {
      if (canPlaceUpTo == h) {
        sum += req - i + 1;
        break;
      }
      if (b[i] == canPlaceUpTo) {
        canPlaceUpTo++;
      }
    }
  }
  return sum;
}

int main(int argc, char const *argv[]) {
  cin >> n;
  n--;
  for (int i = 0; i < n ; i++) {
    cin >> b[i];
  }
  cout << solve() << endl;
  return 0;
}
