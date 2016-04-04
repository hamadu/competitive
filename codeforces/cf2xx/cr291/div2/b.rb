require 'set'

def gcd(a, b)
  return a if b == 0
  gcd(b, a%b)
end

a = Set.new
n,x,y = gets.split.map(&:to_i)
1.upto(n).map do |_|
  ex,ey = gets.split.map(&:to_i)
  dx = ex - x
  dy = ey - y
  if dx < 0
    dx *= -1
    dy *= -1
  end
  if dx == 0
    a << [0,1]
  elsif dy == 0
    a << [1,0]
  else
    g = gcd(dx,dy).abs
    a << [dx/g, dy/g]
  end
end

p a

puts a.size
