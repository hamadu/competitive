v1,v2 = gets.split.map(&:to_i)
t,d  = gets.split.map(&:to_i)

sum = v1 + v2
3.upto(t).each do |_|
  if v1 < v2
    v1 += d
    sum += v1
  else
    v2 += d
    sum += v2
  end
end

puts sum
