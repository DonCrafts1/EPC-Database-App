This app was built for a secondary school student-run environmental group. They handled and sent off the waste in the school's recycling bins.
The app was built to streamline the waste tracking and presentation process. It includes features such as:
- Naming of individual bins, location, categories
- Adding bin records (including info such as weight measured, notes, images)
- User authentication, roles (Owner, Helper)
- Data analytics
- Report generation

The app is currently being used by the group.

Basic database diagram:

<img width="710" alt="Screenshot 2023-03-22 at 10 07 30 PM" src="https://github.com/DonCrafts1/EPC-Database-App/assets/24728602/ab536137-b2f3-4430-85c3-47d510a12524">

The MySQL file you can use to initialize the schema is in the Github repo.

I allow this Github project to be modified for your own needs - although there is some code I adapted for my own:
- https://stackoverflow.com/questions/29489366/how-to-add-button-in-javafx-table-view / For creating buttons in each row of TableView for editing bins
- And a lot of documentation e.g. docs.oracle.com/javafx/2/api/javafx/scene/chart/PieChart.html / openjfx.io/javadoc/11/javafx.controls/javafx/scene/control/TabPane.html etc.

It is made using JavaFX (and Java, of course) for the app and MySQL for the database. I have used Netbeans and Maven to build the project and manage dependencies. 

