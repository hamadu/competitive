c = gets.strip
max = eval(c)

lpos = [0]
rpos = [c.length]
c.chars.each_with_index do |ci, idx|
  if ci == '*'
    lpos.push idx + 1
    rpos.push idx
  end
end

lpos.each do |l|
  rpos.each do |r|
    next if l > r
    exp = c[0...l] + '(' + c[l...r] + ')' + c[r...c.length]
    max = [max, eval(exp)].max
  end
end
p max