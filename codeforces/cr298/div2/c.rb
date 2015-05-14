n,a = gets.split.map(&:to_i)
d = gets.split.map(&:to_i)
sum = d.inject(:+)

ans = d.map do |di|
  less = [0, a - (sum - di) - 1].max
  more = [0, di - (a - (n - 1))].max

  less + more
end

puts ans.join(' ')
