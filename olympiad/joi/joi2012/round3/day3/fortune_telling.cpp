#include <bits/stdc++.h>
using namespace std;

#define ALL(x) x.begin(),x.end()
#define DBG(x) cout << #x << " = " << x << endl
typedef long long ll;

// === segment tree
const int MAXSEG = 1<<19;
const int ROWHEAD = (MAXSEG>>1)-1;
ll segment[MAXSEG];
ll weight[MAXSEG];
int lazy[MAXSEG];

void segment_init(vector<int> &len) {
  memset(segment, 0, sizeof(segment));
  memset(lazy, 0, sizeof(lazy));
  memset(weight, 0, sizeof(weight));

  int n = len.size();
  for (int i = 0 ; i+1 < n ; i++) {
    weight[ROWHEAD+i] = len[i+1] - len[i];
  }
  for (int i = ROWHEAD-1 ; i >= 0 ; i--) {
    weight[i] = weight[i*2+1] + weight[i*2+2];
  }
}

void segment_compute(int idx) {
  if (idx < ROWHEAD) {
    segment[idx] = segment[idx*2+1] + segment[idx*2+2];
  }
}

void segment_propagate(int idx, int l, int r) {
  if (lazy[idx] == 1) {
    segment[idx] = weight[idx] - segment[idx];
    lazy[idx] = 0;
    if (idx < ROWHEAD) {
      lazy[idx*2+1] ^= 1;
      lazy[idx*2+2] ^= 1;
    }
  }
}

void segment_flip(int fr, int to, int idx, int l, int r) {
  segment_propagate(idx, l, r);

  if (to <= l || r <= fr) {
    return;
  }
  if (fr <= l && r <= to) {
    lazy[idx] = 1;
    segment_propagate(idx, l, r);
    return;
  }

  int med = (l+r)>>1;
  segment_flip(fr, to, idx*2+1, l, med);
  segment_flip(fr, to, idx*2+2, med, r);

  segment_compute(idx);
}

void segment_flip(int fr, int to) {
  segment_flip(fr, to, 0, 0, ROWHEAD+1);
}

const int MAXK = 100010;
int n;
int m;
int k;
int data[MAXK][4];

struct operation {
  int fx;
  int tx;
  int y;
  bool operator<(const operation& o) const {
    return y < o.y;
  }
};
vector<operation> ops;

map<int,int> xmap;
map<int,int> ymap;
vector<int> xlen;
vector<int> ylen;
int tmp[MAXK*2];

void compress(map<int,int> &tomap, vector<int> &tolen, int p0, int p1, int max) {
  for (int i = 0 ; i < k ; i++) {
    tmp[i*2] = data[i][p0]-1;
    tmp[i*2+1] = data[i][p1];
  }
  tmp[2*k] = 0;
  tmp[2*k+1] = max;
  sort(tmp, tmp+2*k+2);

  int midx = 2*k+2;
  int local = 0;
  for (int i = 0 ; i < midx ; ) {
    int j = i;
    while (j < midx && tmp[i] == tmp[j]) {
      j++;
    }
    tomap.insert(pair<int, int>(tmp[i], local));
    tolen.push_back(tmp[i]);
    local++;
    i = j;
  }
  tolen.push_back(max);
}

ll solve() {
  compress(xmap, xlen, 2, 3, m);

  for (int i = 0 ; i < k ; i++) {
    int fx = xmap[data[i][2]-1];
    int tx = xmap[data[i][3]];
    ops.push_back({ fx, tx, data[i][0]-1 });
    ops.push_back({ fx, tx, data[i][1] });
  }
  sort(ALL(ops));

  segment_init(xlen);

  ll total = 1LL * n * m;
  ll sum = 0;
  for (int i = 0 ; i < 2*k ;) {
      int j = i;
      while (j < 2*k && ops[i].y == ops[j].y) {
        j++;
      }
      while (i < j) {
        segment_flip(ops[i].fx, ops[i].tx);
        i++;
      }
      ll to = (i == 2*k) ? n : ops[i].y;
      ll dy = to - ops[i-1].y;
      sum += dy * segment[0];
  }
  return total-sum;
}

int main(int argc, char const *argv[]) {
  cin >> n >> m >> k;
  for (int i = 0 ; i < k ; i++) {
    for (int j = 0 ; j < 4 ; j++) {
      cin >> data[i][j];
    }
  }
  cout << solve() << endl;
  return 0;
}
