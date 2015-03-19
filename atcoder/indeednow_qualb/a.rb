# http://indeednow-qualb.contest.atcoder.jp/submissions/362752
x1,y1 = gets.split.map(&:to_i)
x2,y2 = gets.split.map(&:to_i)
puts (x1-x2).abs+(y1-y2).abs+1
