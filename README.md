# Friends Management API demo

## Note
- Test site: http://54.169.182.244:9000/swagger-ui.html

## API Document

### API Authentication
In real production environment, API needs authentication. The following steps are the common API authentication desgin:

- When user login, get a JWT signed by server side, which contains user account info (user id/email)
- Each API sent by user, need to add JWT in HTTP headers
- On processing user request, server side will validate the JWT
- For each API, can append an uid query string, then use the uid to check whether it matches with the user id in JWT

### Desgin details

#### User Stories

**1. As a user, I need an API to create a friend connection between two email addresses.**

**API: POST /friendship/connection**

The API should receive the following JSON request:

```
{
  friends:
    [
      'andy@example.com',
      'john@example.com'
    ]
}
```

The API should return the following JSON response on success:

```
{
  "success": true
}
```

Please propose JSON responses for any errors that might occur.

**2. As a user, I need an API to retrieve the friends list for an email address.**

**API: POST /friendship/connection/all**

The API should receive the following JSON request:

```
{
  email: 'andy@example.com'
}
```

The API should return the following JSON response on success:

```
{
  "success": true,
  "friends" :
    [
      'john@example.com'
    ],
  "count" : 1   
}
```

Please propose JSON responses for any errors that might occur.

**3. As a user, I need an API to retrieve the common friends list between two email addresses.**

**API: POST /friendship/connection/common**

The API should receive the following JSON request:

```
{
  friends:
    [
      'andy@example.com',
      'john@example.com'
    ]
}
```

The API should return the following JSON response on success:

```
{
  "success": true,
  "friends" :
    [
      'common@example.com'
    ],
  "count" : 1   
}
```

Please propose JSON responses for any errors that might occur.

**4. As a user, I need an API to subscribe to updates from an email address.**

**API: POST /friendship/subscribe**

Please note that "subscribing to updates" is NOT equivalent to "adding a friend connection".

The API should receive the following JSON request:

```
{
  "requestor": "lisa@example.com",
  "target": "john@example.com"
}
```

The API should return the following JSON response on success:

```
{
  "success": true
}
```

Please propose JSON responses for any errors that might occur.

**5. As a user, I need an API to block updates from an email address.**

**API: POST /friendship/block**

Suppose "andy@example.com" blocks "john@example.com":

- if they are connected as friends, then "andy" will no longer receive notifications from "john"
- if they are not connected as friends, then no new friends connection can be added
- (Potential requirement) if "andy" wants to connect to "john", the new connection will be added, and "andy" will no longer block "john"

The API should receive the following JSON request:

```
{
  "requestor": "andy@example.com",
  "target": "john@example.com"
}
```

The API should return the following JSON response on success:

```
{
  "success": true
}
```

Please propose JSON responses for any errors that might occur.

**6. As a user, I need an API to retrieve all email addresses that can receive updates from an email address.**

**API: POST /friendship/update-recipients**

Eligibility for receiving updates from i.e. "john@example.com":
- has not blocked updates from "john@example.com", and
- at least one of the following:
  - has a friend connection with "john@example.com"
  - has subscribed to updates from "john@example.com"
  - has been @mentioned in the update

The API should receive the following JSON request:

```
{
  "sender":  "john@example.com",
  "text": "Hello World! kate@example.com"
}
```

The API should return the following JSON response on success:

```
{
  "success": true
  "recipients":
    [
      "lisa@example.com",
      "kate@example.com"
    ]
}
```

Please propose JSON responses for any errors that might occur.

## DB schema

```sql
CREATE TABLE `friend_connection` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_a` varchar(255) DEFAULT NULL,
  `user_b` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `friendship_filter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `filter_type` varchar(255) DEFAULT NULL,
  `filter_object` varchar(255) DEFAULT NULL,
  `filter_subject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## how to build
```bash
$./gradlew clean build
```

## Database Schema Setup

use 'schema.sql' to setup mysql DB schema