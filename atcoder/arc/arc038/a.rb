sum = 0
gets
gets.split.map(&:to_i).sort.reverse.each_slice(2) { |x|
  sum += x[0]
}
puts sum
