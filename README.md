# Watches Dealer GUI

A JavaFX desktop watch-store demo with customer registration and login, a
catalog and shopping cart, checkout, and transaction history. The app also
includes admin screens for managing watch brands and products. It uses MySQL for
users, inventory, carts, and transaction records.

## Stack

- Java 11, JavaFX, and JDBC
- MySQL or MariaDB
- MySQL Connector/J JDBC driver

## Set up the database

Create the `watchesdealer` database and its sample tables and records using
`query.txt` or `create+insert.sql` in a local MySQL-compatible server. The
connection helper currently uses localhost:3306, database name `watchesdealer`,
username `root`, and an empty password. Update the connection settings in the
Java connection class to match your local database.

Add the JavaFX SDK and MySQL Connector/J to the project classpath. The project
includes Eclipse Java and JavaFX metadata, so it can be imported into Eclipse
and configured with those libraries.

## Run

Launch `application.Main` from the IDE. The initial screen opens the login flow;
database setup must be complete before the app starts.

The included SQL data is for local demonstration only. Replace the sample
credentials and database settings before using the app with any real data.
