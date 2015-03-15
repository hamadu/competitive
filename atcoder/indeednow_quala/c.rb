# http://indeednow-quala.contest.atcoder.jp/submissions/358146
n = gets.to_i
s = (1..n).map { |_| gets.to_i }.select { |x| x >= 1}.sort.reverse
n = s.length

p = Array.new(n, -1)
ptr = 0
(0...n).each do |i|
  ptr = i if i >= 1 && p[i-1] != p[i]
  p[i] = ptr
end

(gets.to_i).times do |i|
  v = gets.to_i-1
  if v >= n-1
    puts 0
    next
  end
  if v == -1
    puts s.first+1
    next
  end
  if s[v] != s[v+1]
    puts s[v+1]+1
    next
  end
  puts s[v]+1
end
