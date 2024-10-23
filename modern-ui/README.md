# Mini POS

### Environment Preparation

#### Download
- Download **[JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)** and install
- Download **[JavaFX SDK 21](https://gluonhq.com/products/javafx/)** and extract anywhere you want to
- Download **[MySQL Community Edition](https://dev.mysql.com/downloads/mysql/8.0.html)** and install

#### Database
- Enter MySQL server with command `mysql -u <user> -p<password>`
- Create database `CREATE DATABASE modern_pos_db`
- Grant priviliges `GRANT ALL PRIVILEGES ON modern_pos_db.* TO 'modernadm'@'localhost';`
- generate table from `env-preparation/modern-pos.mwb`

### Run the Application
Open Main class and `right click` > `Run Configurations` > `Arguments` . In the `VM Arguments` box add this<br /><br />
`--module-path /path/to/JavaFX/sdk/lib --add-modules=javafx.controls,javafx.fxml,javafx.graphics`