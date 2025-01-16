# Spring 6 Reactive Mongo

This project is based on the examples given by [John Thompson](https://github.com/springframeworkguru) and his training [[NEW] Spring Boot 3, Spring Framework 6: Beginner to Guru](https://www.udemy.com/course/spring-framework-6-beginner-to-guru)

**Section 31:** Spring Data MongoDB

## How to add the basic Mongo Configuration

A `@Configuration` class must be created with the next code:

```java
@Configuration
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

    @Override
    protected String getDatabaseName() {
        return "my-db-name";
    }
}
```

## How to add the basic Mongo Authentication

Inside the `MongoConfig` class, override the next method:

```java
@Override
protected void configureClientSettings(MongoClientSettings.Builder builder) {
    builder.credential(MongoCredential.createCredential("root","admin", "example".toCharArray()))
            .applyToClusterSettings(settings -> settings.hosts(
                    (
                            Collections.singletonList(
                                    new ServerAddress("localhost", 27017)
                            )
                    )
            ));
}
```

## WebFlux.fn

* It is a `Spring WebFlux` functionality
* It lets use functions instead `@RestController` or `@RequestMapping` to define routes

## Versiong Tags

`0.1.0:` A version which uses Docker as temporal container for the Test execution
`0.2.0:` A version which uses Docker Compose to create a MongoDB and run the App
`1.0.0:` First MongoDB version finished

## Query Methods

In this [link](https://docs.spring.io/spring-data/mongodb/docs/current-SNAPSHOT/reference/html/#mongodb.repositories.queries) can be seen the keywords to declare query methods in signature.

## About testing Mono and Flux

The class `AtomicReference` is a good way for testing the Reactive responses but there are better ways.