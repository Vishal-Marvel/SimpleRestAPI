# Student REST API
### A Simple Spring Boot Application for Student Management
This Application Made with:
* **SpringBoot** : 3.0.4
* **Spring Security** : 6.0

This Application Has the following Features
* Admin User Authentication using JWT (Security [Config](src/main/java/com/example/SampleRestApi/config/SecurityConfig.java), Security [Service](src/main/java/com/example/SampleRestApi/security) and [User Model](src/main/java/com/example/SampleRestApi/models/User/User.java))
* Student Management CRUD REST API (Student [Controller](src/main/java/com/example/SampleRestApi/controller/StudentController.java) and [Model](src/main/java/com/example/SampleRestApi/models/Student.java))
* Student Marks Management CRUD REST API (Mark [Controller](src/main/java/com/example/SampleRestApi/controller/MarksController.java) and [Model](src/main/java/com/example/SampleRestApi/models/Mark.java))
* Student Fees Management CRUD REST API (Fees [Controller](src/main/java/com/example/SampleRestApi/controller/FeeController.java) and [Model](src/main/java/com/example/SampleRestApi/models/Fee.java))
* Fee Payment Management CRUD REST API (Payment [Controller](src/main/java/com/example/SampleRestApi/controller/PaymentController.java) and [Model](src/main/java/com/example/SampleRestApi/models/Payment.java))







