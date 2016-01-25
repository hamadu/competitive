def compute(n)
  ans = 0
  done = 1
  right = 0
  while n >= 1
    up = n / 10
    dg = n % 10
    if dg >= 2
      ans += (up + 1) * done
    elsif dg == 1
      ans += up * done + right + 1
    else
      ans += up * done
    end
    n /= 10
    right += done * dg
    done *= 10
  end
  ans
end

n = gets.to_i
puts compute(n)
