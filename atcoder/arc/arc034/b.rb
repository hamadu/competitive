# http://arc034.contest.atcoder.jp/submissions/349690
n = gets.to_i
answers = (n-200..n).select do |xi|
  next if xi <= 0

  sum = xi.to_s.chars.map(&:to_i).reduce(:+)
  xi + sum == n
end

puts answers.length
puts answers
