n, a, b = gets.split(' ').map(&:to_i)
pos = 0
n.times do
  s, d = gets.split(' ')
  d = [b, [a, d.to_i].max].min
  if s == 'East'
    pos += d
  else
    pos -= d
  end
end

if pos > 0
  puts "East #{pos.abs}"
elsif pos < 0
  puts "West #{pos.abs}"
else
  puts '0'
end
