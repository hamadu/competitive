def dfs(g, h, now, parent)
  have = h[now] == 1
  cost = 0
  g[now].each do |to|
    next if to == parent
    val,has = dfs(g, h, to, now)
    if has
      cost += val + 2
      have = true
    end
  end
  [cost, have]
end

n,x = gets.split.map(&:to_i)
h = gets.split.map(&:to_i)
g = 1.upto(n).map { |_| [] }
1.upto(n-1).each do |_|
  a,b = gets.split.map(&:to_i).map { |k| k-1 }
  g[a].push b
  g[b].push a
end

puts dfs(g, h, x-1, -1)[0]
