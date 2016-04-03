A,B,C = gets.split(" ").map(&:to_i).freeze
PLUS = gets.split(" ").map(&:to_i).freeze
MULT = gets.split(" ").map(&:to_i).freeze

exit(1) if A > 8 || B > 8 || C > 8

DP = Array.new(2**(A+B)+1, -1)
DP[2**(A+B)] = 0

def rec(p)
  return DP[p] if DP[p] >= 0

  leftA = PLUS.each_with_index.select do |_,i|
    p & (1<<i) == 0
  end
  leftB = MULT.each_with_index.select do |_,i|
    p & (1<<(A+i)) == 0
  end

  base = 1.0 / (leftA.length + leftB.length + C)
  DP[p] = 0
  leftA.each do |v,i|
    tp = p | 1<<i
    DP[p] += (rec(tp) + v) * base
  end
  leftB.each do |v,i|
    tp = p | 1<<(A+i)
    DP[p] += (rec(tp) * v) * base
  end
  DP[p]
end

p rec(0)
