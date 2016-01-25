ROW_SCORE = (1..2).map do
  gets.split(' ').map(&:to_i)
end
COL_SCORE = (1..3).map do
  gets.split(' ').map(&:to_i)
end
SUM = [ROW_SCORE, COL_SCORE].flatten.inject(:+)
MEMO = {}

def compute_score(map)
  chokudai = 0
  for r in 0..2
    for c in 0..2
      chokudai += ROW_SCORE[r][c] if r <= 1 && map[r][c] == map[r+1][c]
      chokudai += COL_SCORE[r][c] if c <= 1 && map[r][c] == map[r][c+1]
    end
  end
  chokudai
end

def game(map, depth)
  return MEMO[map] unless MEMO[map].nil?
  if depth == 9
    MEMO[map] = SUM - compute_score(map)
    return MEMO[map]
  end

  best = 0
  mark = (depth % 2) * 2 - 1

  for r in 0..2
    for c in 0..2
      if map[r][c] == 0
        map[r][c] = mark
        result = SUM - game(map, depth+1)
        if result > best
          best = result
        end
        map[r][c] = 0
      end
    end
  end

  MEMO[map] = best
  best
end

map = [[0,0,0],[0,0,0],[0,0,0]]
chokudai = game(map, 0)
naoko = SUM - chokudai

puts chokudai
puts naoko

