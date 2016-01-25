n = gets.to_i
a = (1..n).map { |_| gets.to_i }.sort.reverse
if a.length.odd?
  a.push 0
end

areas = a.each_slice(2).map do |outer,inner|
  (outer ** 2 - inner ** 2) * Math::PI
end
puts areas.inject(:+)
