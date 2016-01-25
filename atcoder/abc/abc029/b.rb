puts (0...12).map { |_| gets.include?('r') ? 1 : 0 }.inject(:+)
