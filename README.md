# Hibernate-CSV Project

This is a Hibernate project that utilizes various technologies to create relational databases and read data from a CSV
file.
The subject of the project is the relational database mapping between continent, country, county and city.

---

## Technologies Used:

* ###### Java 20
* ###### JDK 20
* ###### Apache Maven 3.9.1
* ###### MySQL Connector 8.0.32
* ###### Lombok 1.18.26
* ###### Hibernate 6.2.1.Final
* ###### OpenCSV 5.7.1

---

## Project Description

The purpose of this project is to demonstrate the use of Hibernate ORM to create and manage relational databases,
as well as read data from a CSV file.

The project uses the following components:

* Entity classes: These classes define the structure of the database tables and their relationships.
  They are annotated with Hibernate annotations to map the classes to database tables and columns.


* Hibernate Configuration: This configuration file specifies the database connection properties and other settings
  required for Hibernate to interact with the database.


* DAO (Data Access Object) classes: These classes provide the methods for reading and writing data to the database using
  Hibernate APIs.


* CSV Reader: This component reads data from a CSV file and maps it to Java objects using the OpenCSV library.
  The Java objects are then persisted to the database using Hibernate APIs.

---

## Setup

Clone this repository to your local machine.`git clone https://github.com/CarinaPorumb/HibernateCsvProject `


Run the project by executing the `Main` method in the `Main` class.
