package work.aaronskeels.javaknowledgedemos;

@SuppressWarnings("unused") // We don't care that local variables are never used because this is an informational class.
public class App 
{

    public static void main( String[] args )
    {
        //Concept 1: Abstraction - Hides complexity from the user and shows only relevant information.
        exemplifyAbstraction();
        //Concept 2: Interfaces - See "Abstraction" + DRY (Don't Repeat Yourself)
        exemplifyInterfaces();
        //Concept 3: Encapsulation - Binds data and its related methods together in a class, simultaneously protecting data.
        exemplifyEncapsulation();
        //Concept 4: Inheritance - Allows a class to inherit the features of another class. Aim for code reusability and readability
        exemplifyInheritance();
        //Concept 5: Polymorphism - Use the same structure in different forms. Reusability w/ a focus on flexibility and compartmentalization.
        exemplifyPolymorphism();
        //Concept 6: Association - Pretentious vocab. Remove from file?
        exemplifyAssociation();
    }

    /**
     * This function exemplifies the core concepts behind abstraction in Java. tldr;
     * - Defined by "abstract class" and "extends"
     * - Not possible to have abstract "fields" aka variables
     * - Abstract methods (meant to be defined within subclasses) are denoted via "abstract" prefix
     * - Default definitions are possible by omitting "abstract"
     * - Allows for "static" and "private" methods similar to interface. See {@link #exemplifyInterfaces()} for further info.
     * An important note is that abstraction ("extends") can only have one super
     * class, whereas interfaces ("implements") can have many.
     */
    public static void exemplifyAbstraction() {
        abstract class Animal {
            /*
             * Abstract variables are NOT a thing in java. Instead they use "fields"
             * which you can CHOOSE to manipulate within a child class, but can't be
             * forced syntactically. In my opinion, this is a huge weakness of the language
             * and a large conceptual inconsistency. You can get around it by using
             * getters and setters, but it feels weird that you have to "get around it".
             */
            //abstract int legCount;

            /*
             * Example of abstract functions. These will necessarily need to be defined
             * within any subclasses.
             */
            abstract int getAge();
            abstract String getName();
            abstract String getType(); //Possibly an Enum in a real implementation.
            abstract void move();

            /*
             * Example of an interlaced concrete function. This can be overridden by choice,
             * but can also be used with the super class's default definition.
             */
            public String toString() {
                return "(" + getType() + ")" + getName() + " age " + getAge();
            }
        }

        class Cow extends Animal {

            @Override
            void move() {
                //todo: Implement move mechanics
            }
            @Override
            int getAge() {
                //todo: Implement age mechanics
                return 0;
            }
            @Override
            String getName() {
                return "Cowwy";
            }

            @Override
            String getType() {
                return "Cow";
            }

        }
    }

    /**
     * This function exemplifies the core concepts behind interfaces in Java. tldr;
     * - Defined via "interface" and "implements"
     * - Originally, interfaces were 100% abstract (aside from fields aka variables)
     * - Then "default" got implemented which creates a default method definition not requiring override in subclasses
     * - Then "static" got implemented which creates methods only usable referencing the original interface, not subclasses
     * - Then "private" got implemented which creates unaccessible code outside of the interface definition itself
     */
    public static void exemplifyInterfaces() {
        interface Animal {
            int getAge();
            String getName();
            String getType(); //Possibly an Enum in a real implementation.
            void move();
        }

        interface Flyable {
            /*
             * Default interface methods do not NEED to be overridden by children.
             */
            default void fly() {
                //todo: implement fly mechanics
            }

            /*
             * Static interface methods can only be used with reference to the interface itself, and no implementing subclasses.
             */
            static void staticUtilityMethod() {
                System.out.println("This is a static method only visible on the interface itself, not subclasses.");
            }

            private void privateUtilityMethod() {
                System.out.println("This is a private method only visible WITHIN the interface itself.");
            }
        }

        /*
         * Notice interfaces allow numerous "implements" super classes unlike "extends"
         */
        class Crow implements Animal, Flyable {

            @Override
            public int getAge() {
                //todo: Implement age mechanics
                return 0;
            }

            @Override
            public String getName() {
                return "Crowwy";
            }

            @Override
            public String getType() {
                return "Crow";
            }

            @Override
            public void move() {
                fly();
            }
            
        }

        Crow crow = new Crow();
        //crow.staticUtilityMethod(); // Error: Not allowed
        //Crow.staticUtilityMethod(); // Error: Not allowed
        Flyable.staticUtilityMethod(); // No Error: Allowed

        //crow.privateUtilityMethod(); // Error: Not allowed
        //Crow.privateUtilityMethod(); // Error: Not allowed
        //Flyable.privateUtilityMethod(); // Error: Not allowed
    }

    /**
     * This function exemplifies some core concepts behind encapsulation in Java. tldr;
     * - "public" is visible and modifiable anywhere
     * - "protected" is visible within the same class, package, and subclasses (even if in different packages)
     * - "private" is visible within the same class. This definition is confusing because it doesn't mean direct class.
     *      It means same "parent" class. Nested class definitions are all tied back to the most parent class of the heirarchy
     *      for "private" definitions. Weird implementation Java. I don't like that.
     * - "final" has different functions depending on the context.
     *      For classes, it means the class can not be inhereted
     *      For methods, it means the method can not be overridden by subclasses
     *      For variables, it means they can not have their value changed.
     *          Note: Final, unassigned fields in classes ARE possible and MUST be assigned in their constructor and can never change from that value.
     *          Note: Java handles "final" similar to js "const" in that if a variable is an object (not primitive type), you can still modify that objects fields.
     *              You simply can't reassign which object that original variable points to. There is no way, as of my knowledge, to permanently LOCK a value and
     *              all sub-values down permanently. The only workaround is creating a one-off wrapper class which is implemented instead.
     */
    public static void exemplifyEncapsulation() {
        class Encapsulation {
            //Public fields can be accessed directly
            public String publicField = "I'm public!";
            
            //Private fields can not be accessed at all by default
            private String privateField = "I'm private!";

            //Private fields can be accessed if getters and/or setters are defined. Both don't need to be defined.
            private String getterSetterField = "";

            public String getGetterSetterField() {
                return getterSetterField;
            }
        }

        final Encapsulation encap = new Encapsulation();
        //Read public field: Allowed
        System.out.println(encap.publicField);
        //Write public field: Allowed
        encap.publicField = "Rewritten";

        //Read private field: Not allowed
        // Note: It doesn't actually throw a compiler error here because Encapsulation and App classes are both within the same file and heirarchy
        //System.out.println(encap.privateField);
        //Write private field: Not allowed
        // Note: It doesn't actually throw a compiler error here because Encapsulation and App classes are both within the same file and heirarchy
        //encap.privateField = "Rewritten";

        //Read getter private field: Allowed
        System.out.println(encap.getGetterSetterField());
        //Write setter private field: Unimplemented here so not allowed to prove options
        //encap.setGetterSetterField("Rewritten");


    }

    /**
     * This function exemplifies some core concepts behind inheritance in Java. tldr;
     * - You can "extends" inheritence "unlimited" chains down, but you can only "extends" one class per layer
     * - Anonymous one-off classes which extend a parent class are possible and syntactically exemplified below.
     *      Side note: This introduces us into "instance initializer blocks" which run prior to constructors and are functionally unique.
     *      Anonymous classes can not have defined constructors. They default to automatically running the parent object's constructor. This
     *      works syntactically because to define the subclass you must pass in the arguments suitable to the parent class's constructor.
     */
    public static void exemplifyInheritance() {
        class Car {
            public String brand, model;
            public int year;
            public void drive() {
                //todo: Implement drive mechanics
            }
        }
        class Ford extends Car {
            public boolean hasAirbagRecall; //Ford is notorious for this
            public final boolean hasTransmissionIssues = true; //For is notorious for this too
            public Ford() {
                super();
                brand = "Ford";
            }
        }

        /*
         * This is one example to create a reusable template which is the second layer of inheritence
         */
        class MyCar extends Ford {
            public MyCar() {
                super();
                model = "Focus";
                year = 2020;
                hasAirbagRecall = true;
            }
        }

        /*
         * This is an alternative example to create an anonymous, one time definition as the second layer of inheritence
         */
        Ford myCar = new Ford() {
            //Introduction to instance initializer blocks. They run prior to constructors and have unique functionality. Anonymous classes have no definable constructors.
            {
                model = "Focus";
                year = 2020;
                hasAirbagRecall = true;
            }
        };
    }

    /**
     * This function exemplifies some core concepts behind polymorphism in Java. tldr;
     * - Overloading is creating many versions of the same method, each with different parameter setups.
     *      Note: Unlike other languages, in Java you must call the function with exactly matching parameter type/amount (no omission)
     *      Note: Java allows overloading with the spread operator "..." as the last parameter
     *          Side note: Overloading with a single Object... parameter allows ANY parameters not fitting more explicit overloads to be successful. Use w/ caution.
     * - Overriding is redefining a method within a subclass.
     *      Note: It is NOT syntactically necessary to use "@Override" when doing so, though this helps IDE's catch bugs
     *      Note: It is VERY annoying to try to get parental implementations of methods. If you override, fighting the intended functionality/culture is rough.
     */
    public static void exemplifyPolymorphism() {
        class Polymorphism {
            private int runNumber = 0;
            void run() {
                System.out.println(runNumber + ": run()");
                runNumber++;
            }
            void run(String x) {
                System.out.println(runNumber + ": run(String x)");
                runNumber++;
            }
            void run(int x) {
                System.out.println(runNumber + ": run(int x)");
                runNumber++;
            }
            void run(String x, int y) {
                System.out.println(runNumber + ": run(String x, int y)");
                runNumber++;
            }
            //This final method is a unique version which will catch ANY attempts to run this function that don't fit the other specific examples.
            // Including this ceases all compiler warnings/errors and may yield problems, although in some instances it may be necessary. Use with caution.
            void run(Object... x) {
                System.out.println(runNumber + ": run(Object... x)");
                runNumber++;
            }
            void overrideMe() {
                System.out.println("I am from the parent class");
            }
        }
        Polymorphism x = new Polymorphism() {
            @Override
            void overrideMe() {
                System.out.println("I am from the child class");
            }
            //The method below does nothing because anonymous classes can't add new methods, but if this were a normal class declaration this would yield call to parent method.
            void overrideMe_Polymorphism() {
                super.overrideMe();
            }
        };
        x.run();
        x.run("1");
        x.run(1);
        x.run("1", 1);
        x.run(1, "1");
        x.run(x);
        x.run(1, 1);
        ((Polymorphism) x).overrideMe(); // Despite the type cast, this still runs the child class's method
        x.overrideMe(); // This also runs the child class's method
        // Explicitly coding in a reference to the "Polymorphism" version of the method is the ONLY way to call it from reference to the child class,
        //  but this is not realistically possible via anonymous class because you can't add new methods to that. The only way to allow references to
        //  each tier of subclass's implementation (which is sacrilegious) would be to explicitly define each class in the chain (not anonymous).
        //x.overrideMe_Polymorphism(); //This would be possible with a full class declaration instead of an anonymous one.
    }

    /**
     * This function doesn't have much if any utility. Remove from file?
     */
    public static void exemplifyAssociation() {
        /*
         * This is more ideological and conceptual than functional
         * Associations: Two things are related in some way.
         * Aggregation: A one way association (A human contains food to exist, food does not need human)
         * Composition: A two way association though still heirarchical (A class contains students, whilst students also require a classroom)
         */
    }
    
}
