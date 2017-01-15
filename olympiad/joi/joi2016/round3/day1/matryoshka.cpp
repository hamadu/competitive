#include <bits/stdc++.h>
#define ALL(x) x.begin(),x.end()

using namespace std;
const int MAXN = 400020;
const int MAXQ = 200010;
int n, q;

struct matryoshka {
  int r;
  int h;
  bool operator<(const matryoshka &o) const {
    return h == o.h ? r > o.r : h < o.h;
  }
};

struct query {
  int rmin;
  int hmax;
  int idx;
  bool operator<(const query &o) const {
    return hmax == o.hmax ? rmin > o.rmin : hmax < o.hmax;
  }
};

vector<matryoshka> mats;
vector<query> queries;
map<int,int> xmap;

int ans[MAXQ];

// ====
const int MAXSEG = 1<<20;
const int ROWHEAD = (MAXSEG>>1)-1;
int segment[MAXSEG];

void segment_add(int p, int v) {
  int idx = ROWHEAD+p;
  segment[idx] += v;
  while (idx >= 1) {
    idx = (idx-1)/2;
    segment[idx] += v;
  }
}

int segment_sum(int fr, int to, int idx, int l, int r) {
  if (r <= fr || to <= l) {
    return 0;
  }
  if (fr <= l && r <= to) {
    return segment[idx];
  }
  int med = (l+r)/2;
  return segment_sum(fr, to, idx*2+1, l, med) + segment_sum(fr, to, idx*2+2, med, r);
}

int segment_sum(int fr, int to) {
  return segment_sum(fr, to, 0, 0, ROWHEAD+1);
}

int segment_find_lower(int p) {
  int sum = segment_sum(0, p);
  if (sum == 0) {
    return -1;
  }

  int head = 0;
  int len = ROWHEAD+1;
  int subsum = 0;
  int idx = 0;
  while (len >= 1) {
    if (head+len >= p) {
      len /= 2;
      idx = idx*2+1;
      continue;
    }
    if (subsum+segment[idx] >= sum) {
      len /= 2;
      idx = idx*2+1;
      continue;
    }
    head += len;
    subsum += segment[idx];
    idx++;
  }
  return head;
}

// ====

void solve() {
  // sort
  sort(ALL(mats));
  sort(ALL(queries));

  // compress
  vector<int> posx;
  for (int i = 0 ; i < n ; i++) {
    posx.push_back(mats[i].r);
  }
  for (int i = 0 ; i < q ; i++) {
    posx.push_back(queries[i].rmin);
  }
  sort(ALL(posx));
  posx.erase(unique(ALL(posx)), posx.end());
  int xn = posx.size();
  for (int i = 0 ; i < xn ; i++) {
    xmap.insert(make_pair(posx[i], i));
  }
  for (int i = 0 ; i < n ; i++) {
    mats[i].r = xmap[mats[i].r];
  }
  for (int i = 0 ; i < q ; i++) {
    queries[i].rmin = xmap[queries[i].rmin];
  }

  int ni = 0, qi = 0;
  while (true) {
    if (ni < n && qi < q) {
      matryoshka mat = mats[ni];
      query que = queries[qi];
      if ((mat.h < que.hmax) || (mat.h == que.hmax && mat.r >= que.rmin)) {
        int lpos = segment_find_lower(mat.r);
        if (lpos >= 0) {
          segment_add(lpos, -1);
        }
        segment_add(mat.r, 1);
        ni++;
      } else {
        // query que.rmin
        ans[que.idx] = segment_sum(que.rmin, ROWHEAD+1);
        qi++;
      }
    } else if (qi < q) {
      query que = queries[qi];
      ans[que.idx] = segment_sum(que.rmin, ROWHEAD+1);
      qi++;
    } else {
      break;
    }
  }

  for (int i = 0 ; i < q ; i++) {
    cout << ans[i] << endl;
  }
}

int main(int argc, char const *argv[]) {
  scanf("%d %d", &n, &q);
  for (int i = 0 ; i < n ; i++) {
    int r, h;
    scanf("%d %d", &r, &h);
    mats.push_back({r, h});
  }
  for (int i = 0 ; i < q ; i++) {
    int rmin, hmax;
    scanf("%d %d", &rmin, &hmax);
    queries.push_back({rmin, hmax, i});
  }
  solve();
  return 0;
}
