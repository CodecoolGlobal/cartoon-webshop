### About the project
On this project, our task was to build an online shop with Java, where users can browse products, add them into a shopping cart, checkout items and make payments.

During the implementation, we could practice what we learned about OOP with Java, Unit tests, Logging etc.


### Install & config
With importing this project to IntelliJ as a **Maven project**, IntelliJ can auto-install the dependencies based on the pom.xml

We use PostgreSQL to store data. We need to **create a new database** (called e.g. cartoon-shop). After that, we need to add a **'connection.properties'** file in the following folder: src/main/resources, which contains:
- url= localhost:5432  --> we need to use the right host and port here
- database=  --> name of the database (cartoon-shop)
- user= --> name of the user
- password=  --> password :)

We have to set our **run configurations**:
- Run > Edit configurations
- Press the + button in the top-left corner
- Choose Maven
- Give it a nice name (eg. Run Jetty)
- Type into the Command Line input box: jetty:run
- Press Apply and OK

After the first running of the application, the Initializer will automatically run the initDB.sql file to create the database tables and fill it up with some data.

### Available features
After starting the server, the application is available on [http://localhost:8080/](http://localhost:8080/).

Main features we implemented:
- Filter by Category or by Supplier is available on the main page
- Search by keyword is also available here
- We can add products to our shopping cart
- On the Shopping cart page, we can still change quantities
- On the Checkout page we can fill the form with our personal data
- On the Payment page we can pay with Credit card, PayPal or even with Crypto
- We can choose between themes on the main page, we really recommend the dark one :)
- There is a customer service available, it helps by answering with Chuck Norris jokes :)