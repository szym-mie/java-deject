> ░▀▀█░█▀█░█░█░█▀█░░░░░█▀▄░█▀▀░▀▀█░█▀▀░█▀▀░▀█▀
> ░░░█░█▀█░▀▄▀░█▀█░▄▄▄░█░█░█▀▀░░░█░█▀▀░█░░░░█░
> ░▀▀░░▀░▀░░▀░░▀░▀░░░░░▀▀░░▀▀▀░▀▀░░▀▀▀░▀▀▀░░▀░   


### About

*java-deject* is a stunningly minimalistic dependency injection library.

Compared to similar libraries *java-deject* offers:

- safe, reliable error reporting,
- simple injection strategy,
- what you declare is what you get.

### Getting started

#### Installation

`TODO: no package uploaded`

#### Injection

To make a class injectable, you need to annotate one of its constructors with `@Inject`:

```java
import com.szymmie.deject.annotations.Inject;

class Example {
    @Inject
    public Example(Logger logger, User user, Integer value) {
        // ...
    }    
}
```

You can make only one constructor injectable in any class - otherwise an exception will be thrown.

We need to specify how to get parameters values. There are a few binding method available:

##### by type - `@ByType`

Resolve provider only by parameter type. 
This is the default strategy, you can safely omit this annotation.

##### by type and name - `@ByName("name")`

Resolve provider by parameter type and specified name.
Names are unique in their type namespaces, e.g. an `Object` provider named `"value"`
is not the same as a `Long` provider named `"value"`.

##### by injection - `@ByInject`

Try to create parameter value by injection. The type must be injectable.
This won't work if two classes try to inject each other, creating a cycle.

Let's see the completed example:

```java
import com.szymmie.deject.annotations.Inject;
import com.szymmie.deject.annotations.ByType;
import com.szymmie.deject.annotations.ByInject;
import com.szymmie.deject.annotations.ByName;

class Example {
    @Inject
    public Example(
            @ByType Logger logger,
            @ByInject User user,
            @ByName("value") Integer value
    ) {
        // ...
    }
}
```

```java
import com.szymmie.deject.annotations.Inject;
import com.szymmie.deject.annotations.ByName;

class User {
    @Inject
    public User(
            @ByName("login") String login, 
            @ByName("where") String where
    ) {
        // ...
    }
}
```

#### Providers

A module contains all provider methods being used.
Provider method must:
- be *public*;
- be annotated with `@Provides`,
- accept zero parameters,
- return exactly the type you need.

If you want a named provider then add `@AsNamed("name")`.

```java
import com.szymmie.deject.annotations.Provides;
import com.szymmie.deject.annotations.AsNamed;

class MyModule extends Module {
    @Provides
    public Logger logger() {
        return new PlainLogger();
    }

    @Provides
    @AsNamed("value")
    public Integer value() {
        return 5;
    }
    
    @Provides
    @AsNamed("login")
    public String login() {
        return "me";
    }
    
    @Provides
    @AsNamed("where")
    public String where() {
        return "home";
    }
}
```

You can shape your module class however you want - including custom constructor, fields etc.

#### Creating an object

A container is used for creating instances. All you need to do is pass your module.
You can then request a new instance to be created.

```java
import com.szymmie.deject.Module;
import com.szymmie.deject.ModuleContainer;

class Main {
    public static void main(String[] args) {
        Module module = new MyModule();
        ModuleContainer container = new ModuleContainer(module);
        
        Example example = container.create(Example.class);
    }
}
```

### Demos

You can find small demos in `/src/test/java/com/szymmie/deject/demo` directory.