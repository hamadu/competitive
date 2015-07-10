n = gets.to_i
cnt = 0
left = -9999999999

trees = 1.upto(n).map do |_|
  gets.split(' ').map(&:to_i)
end

trees.each_with_index do |tree, idx|
  if left < tree[0] - tree[1]
    cnt += 1
    left = tree[0]
  elsif idx == n-1 || tree[0] + tree[1] < trees[idx+1][0]
    cnt += 1
    left = tree[0] + tree[1]
  else
    left = tree[0]
  end
end

puts cnt