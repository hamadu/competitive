#include <bits/stdc++.h>
using namespace std;

#define ALL(x) x.begin(),x.end()
#define DBG(x) cout << #x << " = " << x << endl
typedef long long ll;
typedef pair<int,int> yx;

const int INF = 1000000010;
const int MAXN = 500010;
struct fish {
  int size;
  int color;
  bool operator<(const fish& o) const {
    return size == o.size ? color < o.color : size < o.size;
  }
};
struct xyz {
  int x, y, z;
  bool operator<(const xyz& o) const {
    if (z != o.z) return z > o.z;
    if (x != o.x) return x < o.x;
    return y < o.y;
  }
};

int n;
vector<fish> fishes;
vector<xyz> boxes;
set<yx> hoge;

void readFishes() {
  cin >> n;
  for (int i = 0 ; i < n ; i++) {
    int x;
    char c;
    cin >> x >> c;
    int cid = (c == 'R') ? 0 : (c == 'G' ? 1 : 2);
    fishes.push_back({x, cid});
  }
  sort(ALL(fishes));
}

void listRects() {
  int fr = 0;
  int to = 0;
  int colorSets[3] = {0,0,0};
  bool hasNew = false;
  while (fr < n) {
    hasNew = false;
    while (to < n && fishes[to].size < fishes[fr].size * 2) {
      colorSets[fishes[to].color]++;
      hasNew = true;
      to++;
    }
    // add
    if (hasNew) {
      boxes.push_back({colorSets[0]+1, colorSets[1]+1, colorSets[2]+1});
    }

    colorSets[fishes[fr].color]--;
    fr++;
  }
  sort(ALL(boxes));
}


ll solve() {
  hoge.insert(make_pair(0, INF)); // first
  hoge.insert(make_pair(INF, 0)); // last

  ll sum = 0;
  ll lastz = INF;
  ll men = 0;
  int cn = boxes.size();

  for (int i = 0 ; i < cn ; ) {
    int j = i;
    while (j < cn && boxes[i].z == boxes[j].z) {
      j++;
    }

    sum += men * (lastz - boxes[i].z);
    for (int k = i ; k < j ; k++) {
      xyz l = boxes[k];
      set<yx>::iterator it = hoge.lower_bound(make_pair(l.y, l.x));
      ll headY = it->first;
      ll headX = it->second;
      if (l.y <= headY && l.x <= headX) {
        continue;
      }

      it--;
      while (it->second <= l.x) {
        ll hy = it->first;
        ll hx = it->second;
        men += (hx-headX) * (l.y - hy);
        headX = hx;
        hoge.erase(it--);
      }
      men += (l.x - headX) * (l.y - it->first);
      hoge.insert(make_pair(l.y, l.x));
    }
    lastz = boxes[i].z;
    i = j;
  }

  sum += men * lastz;
  return sum - 1;
}

int main(int argc, char const *argv[]) {
  readFishes();
  listRects();

  cout << solve() << endl;
  return 0;
}
