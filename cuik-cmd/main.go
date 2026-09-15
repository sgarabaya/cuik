package main

import (
	"fmt"

	arg "github.com/alexflint/go-arg"
	_ "github.com/go-sql-driver/mysql"
)

func main() {
	var args struct {
		Verb   string `arg:"positional,required" help:"The action to run, can be 'migrate', 'list' or 'status'"`
		Target string `help:"The target migration to apply"`
	}
	arg.MustParse(&args)

	switch args.Verb {
	case "migrate":
		ApplyMigrations("cuik", args.Target)
	case "list":
		fmt.Println("Available migrations:")
		for _, name := range MigrationNames() {
			fmt.Printf("> %s\n", name)
		}
	case "status":
		fmt.Println("Input:", args.Verb)
	default:
	}
}
