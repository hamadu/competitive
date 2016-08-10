# コンテスト中の発想できなかった／致命的ミス全集

### [MultiplicationTable3](https://community.topcoder.com/stat?c=problem_statement&pm=14244&rd=16710)
*2, +1 しながら数を作るという発想が出てこなかった(2016/05/10)

### [FindingKids](https://community.topcoder.com/stat?c=problem_statement&pm=13890&rd=16624)
カートの相対位置が変わらない → 位置を二分探索 という発想が出てこなかった(2016/05/19)

### [DistinguishableSetDiv1](TBA)
1. 計算量の見積もりミスで20分溶かした(半分全列挙+突き合わせで常勝！ → O(2^m) ではなく O(2^m*n))
2. 「補集合を数える」という発想が出てこなかった(2016/07/09)

### [Codeforces #363 Div1C LRU](http://codeforces.com/contest/698/problem/C)
遷移図からの行列の累乗で出来ると勘ぐり1時間捨てた：**ダメそうな方針は早めに捨てること**

### [Codeforces #364 Div1B Connecting Universities](http://codeforces.com/contest/700/problem/B)
複数の点対の移動距離 → 使う辺のコストの合計 という発想が出てこなかった

### [AGC002 D - Stamp Rally](http://agc002.contest.atcoder.jp/tasks/agc002_d)
初見での感想：答えでまとめて二分探索しちゃおう(2016/07/31)
 -> コンテスト中思ったこと：無理っぽい？(クエリが偏るとやばそう)
 -> **無理じゃないゾ！！！！！(深さが高々logMになるから、クエリを見る回数もQlogM回で済む！！偏りとか関係ない！！)**

### [CSAcademy #010-D Subarray Medians](https://csacademy.com/contest/round-10/#task/subarray-medians)
逆からやるって発想が出てこなかった。毎回忘れる、ほんとひで
発想としては逆からやると構築済みの何かが得られて、左に戻して値を削除するのが楽、という