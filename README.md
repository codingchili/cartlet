# webshoppe [![Build Status](https://travis-ci.org/codingchili/cartlet.svg?branch=master)](https://travis-ci.org/codingchili/cartlet)

A webshop project from 2015 rebooted, demo https://www.youtube.com/watch?v=gUCBCB0Om-8 

![alt text](https://raw.githubusercontent.com/codingchili/webshoppe/master/scrapbook/4-tons-of-fixes.PNG "Current snapshot version")

[(swish payments!)](https://raw.githubusercontent.com/codingchili/webshoppe/master/scrapbook/3-swish-payments.png "Current snapshot version")

### Background

The project is written as a java EE webshop application with the following stack
* MySQL
* Bootstrap
* HTML5
* JSP/JSTL

While we could have replaced these with something never and more interesting, I think
it would be more fun/challenging to keep the stack in place. I like to have some diversity
in my projects, not everything has to be NoSQL and SPA :) I'm not much for EE, application
servers, servlets and all that enterprisey stuff. 

Challenges
- performance
  - [x] make sure to upgrade to latest MySQL DB / driver.
  - [x] analyze existing queries, check for missing/bad indexes.
  - [x] find the fastest goddamn application server there is.
  - [ ] server side rendering causes database calls to block.
    - even worse, all our DB calls are synchronous and serialized.
- security 
  - [x] tons of forms here, we need some solid CSRF protection.
  - [x] zero protection against XSS in place.
  - [x] payment security; not required for simple swish integrations.
  - [x] password hashing: uses PBKDF2, barely passable, upgrade to Argon2
  - [x] prevent session fixation - regenerate session id.
  - [x] use constant time compare for passwords / csrf token
- mobile support
  - [x] we use bootstrap so it shouldn't be too hard.
  - [x] upgrade bootstrap from v3 to v4.
  - [x] add a favicon / pwa manifest.
- containerless deployment
  - [x] tom EE / undertow / ? (i will NEVER touch spring.)
- payment
  - [x] there is no existing payment implementation.
  - [x] lets start with swish, and just use a URI / QR for payments.
  - [ ] maybe later we can explore more options, Ether etc?
- workflow
  - [ ] order management - update order status
  - [ ] handling of refunds / cancelled orders
  - [ ] prevent orders from never getting completed when items out of stock

### Building
Super easy, 

```
./gradlew jar
```

Produces a standalone jar with an embedded application server.

If you want docker,
```
./gradlew jar && docker build .
```

### Installing

Needs at least one MySQL server, we are using 8.0.12 for development.

Preload a new database with the file `database.sql`.

The default configuration looks like,
```
{
  "jdbcUrl" : "jdbc:mysql://localhost:3306/webshop?useSSL=false",
  "databaseUser" : "root",
  "databasePass" : "",
  "swishReceiver" : "07372151522"
}
``` 
This is my development settings, you you will need to place a file called `application.json` beside your
jar, with values that matches your environment.

Start the application with,
```
java -jar <fileName>.jar
```

for docker,
```
docker run -it -p 8080:8080 <imageName>
```

## Contributing
Contributions are always welcome! pull requests, code reviews, new issues, comments on existing issues etc.
