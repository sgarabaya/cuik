package main

import (
	"cuik-cmd/config"
	"database/sql"
)

func Try[T any](res T, err error) T {
	OrPanic(err)
	return res
}

func OrPanic(err error) {
	if err != nil {
		panic(err)
	}
}

func Connect() *sql.DB { return Try(sql.Open("mysql", config.ConnectionString)) }

func RunStatements(db *sql.DB, statements []string) {
	tx := Try(db.Begin())

	for _, statement := range statements {
		Try(tx.Exec(statement))
	}

	OrPanic(tx.Commit())
}
