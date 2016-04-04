n = gets.to_i
cars = 1.upto(n).select do |_|
  collisions = gets.split(' ').map(&:to_i)
  !collisions.include?(1) && !collisions.include?(3)
end

puts cars.length
puts cars.join(' ')
