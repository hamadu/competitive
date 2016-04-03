# http://arc035.contest.atcoder.jp/submissions/352621
MOD = 1_000_000_007

def solve_time(times)
  penalties_time = times.inject([0, 0]) { |(penalties, time), p| [penalties+time+p, time+p] }
  penalties_time[0]
end

def fact(x)
  (1..x).inject(:*)
end

def solve_ptn(group)
  problem_counts = group.keys.map { |key| group[key].size }
  problem_counts.inject(1) { |ptn, g| (ptn * fact(g)) % MOD }
end

n = gets.to_i
t = (1..n).map { |_| gets.to_i }.sort
group = t.group_by { |x| x }

puts solve_time(t)
puts solve_ptn(group)
