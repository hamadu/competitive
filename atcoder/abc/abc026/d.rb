def compute(a, b, c, t)
  a * t + b * Math.sin(c * t * Math::PI)
end

a, b, c = gets.split(' ').map { |s| s.to_i }

tmin = (100 - b) / a.to_f
tmin -= tmin % (2.0 / c.to_f)
tmin -= 0.5 / c.to_f
if tmin < 0
  tmin += 2.0 / c.to_f
end
tmax = tmin
while compute(a, b, c, tmax) <= 100 do
  tmax += 2 / c.to_f
end
tmin = tmax - 2 / c.to_f
100.times do
  med = (tmin + tmax) / 2
  if compute(a, b, c, med) <= 100
    tmin = med
  else
    tmax = med
  end
end
puts tmin



