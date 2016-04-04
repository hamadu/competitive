a = gets.strip.chars.each_with_index.map do |ci,i|
  d = ci.ord - '0'.ord
  if i == 0 && d == 9
    d
  elsif d >= 5
    9-d
  else
    d
  end
end
puts a.join
