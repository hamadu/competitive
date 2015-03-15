# http://arc034.contest.atcoder.jp/submissions/349677
n = gets.to_i
scores = (1..n).map do |i|
  a,b,c,d,e = gets.chomp.split(' ').map(&:to_i)
  a+b+c+d+e*110.0/900.0
end

p scores.max
