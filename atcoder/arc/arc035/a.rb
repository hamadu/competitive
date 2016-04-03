# http://arc035.contest.atcoder.jp/submissions/352605
def check(s, l, r)
  return true if l >= r
  return false if s[l] != '*' && s[r] != '*' && s[l] != s[r]
  check(s, l+1, r-1)
end

s = gets.chomp
puts (check(s, 0, s.length-1) ? 'YES' : 'NO')
