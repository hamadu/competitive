gets
pleased = 0
time = 0
gets.split(' ').map(&:to_i).sort.each do |wait|
  if time <= wait
    pleased += 1
    time += wait
  end
end
puts pleased
