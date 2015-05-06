class Solver
  def initialize(map)
    @map = map
    @memo = map.map { |row|
      Array.new(row.length, 0)
    }
  end

  def game(i, j)
    return 1 if i >= @map.length || j >= @map[0].length
    return 1 if @map[i][j] == '#'
    return @memo[i][j] if @memo[i][j] != 0

    win = (game(i+1, j) == -1) || (game(i, j+1) == -1) || (game(i+1, j+1) == -1)
    result = win ? 1 : -1
    @memo[i][j] = result
    result
  end
end

h, _ = gets.split.map(&:to_i)
map = 1.upto(h).map { |_| gets.chomp.chars.to_a }

solver = Solver.new(map)
result = solver.game(0, 0)
if result == 1
  puts 'First'
else
  puts 'Second'
end
