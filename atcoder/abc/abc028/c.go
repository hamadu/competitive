package main

import (
	"fmt"
)

func solve(arr []int) {
	v541 := arr[0] + arr[3] + arr[4]
	v532 := arr[1] + arr[2] + arr[4]
	if v541 > v532 {
		fmt.Println(v541)
	} else {
		fmt.Println(v532)
	}
}

func main() {
	var a, b, c, d, e int
	fmt.Scan(&a, &b, &c, &d, &e)
	solve([]int{a, b, c, d, e})
}
