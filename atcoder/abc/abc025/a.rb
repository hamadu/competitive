s = gets.chomp!.chars
ord = gets.to_i

res = s.product(s)[ord-1]
puts res.join
