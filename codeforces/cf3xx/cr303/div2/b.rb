A = gets.strip.chars
B = gets.strip.chars

diff = 0
answer = []
A.zip(B).map do |a, b|
  if a == b
    answer.push a
  else
    if diff >= 0
      answer.push a
      diff -= 1
    else
      answer.push b
      diff += 1
    end
  end
end

if diff != 0
  puts "impossible"
else
  puts answer.join
end