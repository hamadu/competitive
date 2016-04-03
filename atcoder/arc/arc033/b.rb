# http://arc033.contest.atcoder.jp/submissions/358180
require 'set'

gets
a = Set.new(gets.split(' ').map(&:to_i))
b = Set.new(gets.split(' ').map(&:to_i))
p (a & b).length * 1.0 / (a | b).length
