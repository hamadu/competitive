def dfs(now, graph)
  return 1 if graph[now].size == 0
  salaries = graph[now].map { |c| dfs(c, graph) }
  salaries.min + salaries.max + 1
end

n = gets.to_i
graph = (1..n).map { |_| [] }

(1..n-1).each do |child|
  parent = gets.to_i - 1
  graph[parent].push child
end

puts dfs(0, graph)