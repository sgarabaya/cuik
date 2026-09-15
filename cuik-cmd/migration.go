package main

import (
	"database/sql"
	"fmt"
)

type migration struct {
	name     string
	up, down string
}

func ensureDatabase(db *sql.DB, name string) {
	res := Try(db.Exec(fmt.Sprintf("CREATE DATABASE IF NOT EXISTS `%s`;", name)))
	count := Try(res.RowsAffected())

	if count > 0 {
		fmt.Printf("Created `%s`", name)
	}
}

func loadMigrations() []migration {
	return nil
}

// Public interface for Migrations
func ApplyMigrations(dbName, target string) {
	db := Connect()
	defer db.Close()
	ensureDatabase(db, dbName)

	RunStatements(db, []string{
		fmt.Sprintf("USE `%s`;", dbName),
		"CREATE TABLE IF NOT EXISTS `__Migrations`" +
			"(" +
			"	`name` VARCHAR(256) PRIMARY KEY," +
			"	`applied` TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
			"	`up` TEXT NOT NULL," +
			"	`down` TEXT NOT NULL" +
			");",
	})

	queryResult := Try(db.Query("SELECT `name`,`up`,`down` FROM `__Migrations` ORDER BY `name` DESC LIMIT 1;"))

	queryResult.Next()
	var name string
	OrPanic(queryResult.Scan(&name))

	fmt.Println(name)
}

func MigrationNames() []string {
	var names []string

	migrations := loadMigrations()

	for _, m := range migrations {
		names = append(names, m.name)
	}

	return names
}
