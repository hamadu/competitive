package main

import (
	"fmt"
)

func main() {
	var n int
	fmt.Scanf("%d", &n)

	if n <= 59 {
		fmt.Println("Bad")
	} else if n <= 89 {
		fmt.Println("Good")
	} else if n <= 99 {
		fmt.Println("Great")
	} else {
		fmt.Println("Perfect")
	}
}
