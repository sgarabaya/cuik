const fs = require("fs");
const path = require("path");
const datos = require("./datos.json");

const sanitize = (str) => str.replaceAll("'", "\\'");

function generateEmployees() {
    const output = [
        "USE `cuik`;\n",
        "SET NAMES 'utf8';\n",
        "INSERT INTO `Employees`",
        "    (`id`, `created`, `updated`, `name`, `email`, `is_admin`, `password_hash`)",
        "VALUES",
    ];

    const entries = datos.employees;
    entries.sort((a, b) => a[3] < b[3] ? 1 : -1);

    const values = [];
    for (const e of entries) {
        const [id, created, updated, name, email, isAdmin, password_hash] = e;
        values.push(
            `   ('${id}', '${created}', '${updated}', '${sanitize(name)}', '${
                sanitize(email)
            }', ${isAdmin}, '${password_hash}')`,
        );
    }
    output.push(values.join(",\n"), ";");

    const fileName = path.join(
        __dirname,
        "..",
        "migrations",
        "9992_InsertEmployees.sql",
    );

    fs.writeFileSync(fileName, output.join("\n"), "utf-8");
}

function generateUsers() {
    const output = [
        "USE `cuik`;\n",
        "SET NAMES 'utf8';\n",
        "INSERT INTO `Users`",
        "    (`id`, `created`, `updated`, `name`, `email`, `password_hash`)",
        "VALUES",
    ];

    const entries = datos.users;
    entries.sort((a, b) => a[3] < b[3] ? 1 : -1);

    const values = [];
    for (const user of entries) {
        const [id, created, updated, name, email, password_hash] = user;
        values.push(
            `   ('${id}', '${created}', '${updated}', '${sanitize(name)}', '${
                sanitize(email)
            }', '${password_hash}')`,
        );
    }
    output.push(values.join(",\n"), ";");

    const fileName = path.join(
        __dirname,
        "..",
        "migrations",
        "9991_InsertUsers.sql",
    );

    fs.writeFileSync(fileName, output.join("\n"), "utf-8");
}

generateEmployees();
generateUsers();
