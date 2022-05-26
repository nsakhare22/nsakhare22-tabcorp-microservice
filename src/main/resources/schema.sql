CREATE TABLE customer (
  id INTEGER PRIMARY KEY NOT NULL,
  firstname VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  location VARCHAR(255) NOT NULL);

CREATE TABLE product (
  code VARCHAR(255) NOT NULL PRIMARY KEY,
  cost VARCHAR(255) NOT NULL,
  status VARCHAR(255) NOT NULL);

CREATE TABLE transaction (
  tabID INTEGER generated always as identity PRIMARY KEY,
  customerID INTEGER NOT NULL,
  quantity INTEGER NOT NULL,
  product_code VARCHAR(255) NOT NULL,
  time VARCHAR(255) NOT NULL);

CREATE TABLE transaction_cost (
  id INTEGER PRIMARY KEY NOT NULL,
  code VARCHAR(255) NOT NULL,
  quantity INTEGER NOT NULL,
  total_amount INTEGER NOT NULL);