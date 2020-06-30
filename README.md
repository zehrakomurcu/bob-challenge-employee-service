# Employee-Service

As its name suggests, this service is responsible for handling the employees of a company. The application must expose a REST API. It should contain endpoints to:
  - Create a department
    - Id (auto-increment)
    - Name
    
 - Create an employee with the following properties:
   - Uuid (generated automatically)
   - E-mail
   - Full name (first and last name)
   - Birthday (format YYYY-MM-DD)
   - Employee’s department
   
  - Get a specific employee by uuid (response in JSON Object format)
  - Update an employee
  - Delete an employee

Whenever an employee is created, updated or deleted, an event related to this action must be pushed in Kafka. This event will be listened to by the [`event-service`](https://github.com/takeaway/bob-challenge-event-service/).

#### Restrictions

 - The `email field` is unique, i.e. _2 employees cannot have the same email._
