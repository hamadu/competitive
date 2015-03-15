# http://indeednow-quala.contest.atcoder.jp/submissions/358124
INDEEDNOW = 'indeednow'.chars.sort.freeze

(gets.to_i).times do |_|
  puts (gets.chomp.chars.sort == INDEEDNOW) ? 'YES' : 'NO'
end
