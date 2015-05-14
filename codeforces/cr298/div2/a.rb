n = gets.to_i

if n <= 2
  puts "1\n1\n"
elsif n == 3
  puts "2\n1 3\n"
else
  e = 1.upto(n).select { |x| x % 2 == 0 }
  o = 1.upto(n).select { |x| x % 2 == 1 }
  puts n
  puts e.join(" ") + " " + o.join(" ")
end
