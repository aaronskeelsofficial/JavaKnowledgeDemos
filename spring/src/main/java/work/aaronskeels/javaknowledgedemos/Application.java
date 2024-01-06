package work.aaronskeels.javaknowledgedemos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // This annotation adds the annotations "@Configuration", "@EnableAutoConfiguration", and "@ComponentScan"
/*
 * @Configuration: necessary for Spring
 * @EnableAutoConfiguration: Automatically configures a lot of ridiculous stuff that beginners (me) shouldn't have to worry about
 * @ComponentScan: Automatically searches for an initializes present components in the project (aka Controller)
 */
public class Application
{
    public static void main( String[] args )
    {
        /*
         * POJO vs JavaBean
         * - POJO (Plain Old Java Object): A class which functions fine in Java but which follows no conventions
         *      of a defined framework.
         * - JavaBean: A class which adheres to specific naming patterns and design principles
         *      public no-argument constructor (for deserialization purposes)
         *      all fields are private with getX/setX getters and setters (bool can be isX)
         *      implements Serializable to store the state
         * - Spring Bean: A class managed by the Spring IoC container
         * Spring talks seem to be intimiately coupled with "Bean" talks, though they are not necessarily related
         * Spring Framework Runtime:
         *      - Data Access/Integration
         *          - JDBC: Used to handle database interactions
         *          - ORM: Integrates Object-Relational Mapping frameworks w/ relational databases
         *          - OXM: Integrates Object-XML Mapping frameworks w/ XML representations
         *          - JMS: Provides foundation and templates for messaging
         *          - Transactions: Simplifies handling of db transactions ensuring data consistency and integrity
         *      - WEB (MVC/Remoting)
         *          - Web: Provides Model View Controller (MVC) and RESTful services
         *          - Servlet: Promote modular and extensible approach to handling HTTP req/res
         *          - Portlet: Promotes modular, reusable, and portable components for building portal-based web apps
         *          - Struts (Apache): This was listed on the Springs tutorial site but is actually an unrelated Apache thing so idk why it's here
         *      - AOP: This module is absolutely insane. They exit the land of Java and start modifying Java behavior via XML. What the heck why would you ever do that you freaks.
         *      - Aspects: Related to above point. Either the tutorials I looked at are wrong/misleading or this is gross. Don't touch this.
         *      - Instrumentation: Used for monitoring, profiling, and gathering metrics
         *      - Spring Core Container
         *          - Core: Foundational block of entire framework leaning into IoC and DI
         *          - Beans: Creates classes whose DI and lifecycles are managed by the IoC container
         *          - Context: Enhances Core facilitating tasks such as event handling, internationalization, and resource loading
         *          - SpEL (Expression Language): Similar to AOP this is insane. Why are they trying to decouple NORMAL JAVA into weirdness? What am I not understanding?
         *      - Test: Used for testing under the Spring Framework (hard to test without it when you take advantage of a lot of the framework's XML/annotation weirdness)
         * Spring Boot: Opinionated framework built on top of Spring for web development. Whereas Spring leans into customization and freedom (w/ lots of boilerplate
         *      and exhaustive configuration), Spring Boot leans into opinionated defaults and rapid development.
         * Project Initialization can be cloned or generated custom from https://start.spring.io
         */
        SpringApplication.run(Application.class, args);
    }
}
