


# Overview

Instead of having a generic solution such as statsd, the idea for this monitoring tool is to track data in a structured manner for a customer of a product. This is an experimental idea that isn't really used yet, but something to explore as service usage is on the up trend for customers. How do you track usage compared to how much you bill a customer? What are the traffic patterns of customers? Do they stop using your product? 

The idea of this tool is to answer operation traffic patterns as well as monitoring if a customer usage goes down which may indicate the customer is starting to move away from a product. With the same structured data the answers to a lot of questions can be found.

## Question to answer

* What customer is changing the most in the system?
* What are the customer's peak traffic patterns?
* What servers handle the most of a customer's traffic?
* Usage of product for the customer over time?
* Time processing data for a customer?
* Is the customer not using the product as much? Usage growing or shrinking?

Support for plugins, maybe based on provider interfaces.

## Format

```
identity_id: "unique identifier",
data_type: "object type",
count: 1,
qualifiers: {
  time_took: 1000,
  state: "pending",
  label: "dbcall",
  change_type: "create"
}
```
Shorthand version:
```
identifier:data_type:count[t=1000,s=pending,l=dbcall,ct=create]

# example: 

customerxyz:account:1[t=1000,s=pending,l=dbcall,ct=create]

```

* `customer_id` - Unique identifier for a customer, Mandatory field
* `data_type` - Type of data you are tracking, eg Blog Post, Mandatory field
* `count` - How many of a given data type change for this event, Mandatory field
* `qualifiers` - Hash of values detailing more about the event, Optional fields
** `time_took` - Time it took to process the change in MS
** `state` - State of the event change, pending / complete / etc..
** `label` - Free form tag on the event, can be used to monitoring a category of events such as Email Processing, Bill Payment, etc...
** `change_type` - Crud based definition of the event, create, update, delete, etc....


# Building

* Test code `gradle test`
* Db setup `gradle flywayMigrate -i`

