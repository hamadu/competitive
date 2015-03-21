# http://arc031.contest.atcoder.jp/submissions/359517
def paint(a, x, y)
  a[x][y] = 'x'
  paint(a, x+1, y) if x < 9 && a[x+1][y] == 'o'
  paint(a, x-1, y) if x > 0 && a[x-1][y] == 'o'
  paint(a, x, y+1) if y < 9 && a[x][y+1] == 'o'
  paint(a, x, y-1) if y > 0 && a[x][y-1] == 'o'
end

def isok(a, x, y)
  cp = a.map do |line|
    line.clone
  end
  cp[x][y] = 'o'
  cnt = 0
  (0...10).to_a.product((0...10).to_a).each do |px,py|
    if cp[px][py] == 'o'
      paint(cp, px, py)
      cnt += 1
    end
  end
  cnt == 1
end

a = 1.upto(10).map do |_|
  gets.chomp.chars.to_a
end

ok = false
(0...10).to_a.product((0...10).to_a).each do |x,y|
  ok = true if isok(a, x, y)
end

puts ok ? 'YES' : 'NO'
