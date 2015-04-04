keys = Hash.new(0)
answer = 0
gets
gets.chomp.chars.each_slice(2) do |k,d|
  keys[k] += 1
  if keys[d.downcase] >= 1
    keys[d.downcase] -= 1
  else
    answer += 1
  end
end

puts answer
