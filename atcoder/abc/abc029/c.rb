def dfs(len, str)
  if len == 0
    puts str
    return
  end
  %w(a b c).each { |c| dfs(len - 1, str + c) }
end

n = gets.to_i
dfs(n, '')
