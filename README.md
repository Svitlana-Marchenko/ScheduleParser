# ScheduleParser
## Project Description
This Java project is designed to parse and process naukma schedule data from Excel files (`.xlsx`). It is built using Maven and requires Java 17 or later.
## Prerequisites

Before running the project, make sure you have the following installed on your local machine:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) 17 or later
- [Apache Maven](https://maven.apache.org/download.cgi)

## Getting Started

1. Clone this repository to your local machine using Git:

   ```bash
   git clone https://github.com/Svitlana-Marchenko/ScheduleParser.git
   ```
   
## Running the Project

To run the project, you need to provide Excel files (`.xlsx`) for the parser. These files should be placed in the `resources` directory of the project.

## Using the Tester
The project includes a Main class with a tester. You can use the tester to process the test schedule files. Simply run the Main class, and it will execute the parser with the test files provided in the resources directory.

## Response Format (JSON File)
After running the parser, the program will generate an output JSON file named `output.json`. This file contains the parsed schedule information about the faculty, specialization, subject name, year of studying, and a detailed schedule for each group..

Here's an example of the response format:
```json
{
   "faculty" : "Факультет інформатики",
   "specialization" : "Інженерія програмного забезпечення",
   "subjectName" : "Розробка користувацького інтерфейсу (UI/UX), ас. О.В. Бітаєва",
   "yearOfStudying" : 3,
   "groups" : {
      "1.0" : [ {
         "day" : "Четвер",
         "time" : "15:00-16:20",
         "room" : "Дистанційно",
         "weeks" : "1-12"
      } ],
      "2.0" : [ {
         "day" : "Четвер",
         "time" : "16:30-17:50",
         "room" : "Дистанційно",
         "weeks" : "1-12"
      } ],
      "3.0" : [ {
         "day" : "Четвер",
         "time" : "18:00-19:20",
         "room" : "Дистанційно",
         "weeks" : "1-12"
      } ],
      "лекція" : [ {
         "day" : "Четвер",
         "time" : "13:30-14:50",
         "room" : "Дистанційно",
         "weeks" : "1-11"
      } ]
   }
}
```

## Project Structure

The project's structure and dependencies are defined in the `pom.xml` file. Make sure you have all the required dependencies mentioned in the `pom.xml` file to ensure the project runs correctly.

## Issues and Troubleshooting

If you encounter any issues or have trouble running the project, please check the following:

- Ensure you have Java 17 or later installed.
- Verify that Maven is installed and properly configured.
- Double-check the presence and format of your Excel files in the `target` directory.
- Review the project's dependencies and ensure they are correctly specified in the `pom.xml` file.

For additional support, please contact the project maintainer.
