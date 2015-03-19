# http://indeednow-qualb.contest.atcoder.jp/submissions/362755
def rotate(s)
  [s.last] + s[0..-2]
end

def find(s, t, x)
  return -1 if x >= s.length
  return x if s == t
  find(rotate(s), t, x+1)
end

s = gets.chomp.chars.to_a
t = gets.chomp.chars.to_a
puts find(s, t, 0)
