package main

import(
  "fmt"
)

func solve(n, k int64) {
  var all float64 = float64(n * n * n)
  var ptn int64 = 1 + 3 * (n - 1) + 6 * (k - 1) * (n - k)
  fmt.Println(float64(ptn) / all)
}

func main() {
  var n, k int64
  fmt.Scan(&n, &k)
  solve(n, k)
}
