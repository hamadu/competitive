package main

import (
	"fmt"
)

func solve(s string) {
	degree := make([]int, 6)
	for _, v := range s {
		degree[v-'A'] += 1
	}
	for i := 0; i < len(degree); i++ {
		if i >= 1 {
			fmt.Print(" ")
		}
		fmt.Print(degree[i])
	}
	fmt.Println()
}

func main() {
	var s string
	fmt.Scan(&s)
	solve(s)
}
