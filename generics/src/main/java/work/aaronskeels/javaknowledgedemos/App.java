package work.aaronskeels.javaknowledgedemos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App 
{
    public static void main( String[] args )
    {
        // Concept 1 - Generic Methods: Generalize method input params
        exemplifyGenericMethod();
        // Concept 2 - Specify Generalization: Allows for access to more specific methods/fields than "Object" type
        exemplifyBoundedGeneric();
        // Concept 3 - Extends/Super: Utilize upper and lower bounds to enhance generic type relationships
        exemplifyWildcard();
        // Concept 4 - Generic Classes: Class wide generalizations
        exemplifyGenericClass();
        // Concept 5 - Sacrilegious Tendency: Example of something I *ALWAYS* am drawn to like moth to light, but which is bad practice
        exemplifyCastingError();
        // Note: Primitives can not be used in generics. Use their "boxed" counterpart (eg int -> Integer)
    }

    /**
     * This function exemplifies how to operate generic methods in Java
     * Example Method Format:
     *  [Scope] [Generic Info] [Return Type] [Name] (Params) {}
     * Note: Generic vars can be used within both the return type and input params, along with the method body itself
     */
    public static void exemplifyGenericMethod() {
        class ExampleClass {
            // Single type
            public static <T> T getFirstElem (List<T> list) {
                return list.get(0);
            }
            // Multiple types
            public static <X, Y, Z> Z getTwoTieredKey (Map<X, Map<Y, Z>> map, X keyOne, Y keyTwo) {
                if (!map.containsKey(keyOne))
                    return null;
                
                Map<Y, Z> innerMap = map.get(keyOne);
                if (!innerMap.containsKey(keyTwo))
                    return null;
                
                return innerMap.get(keyTwo);
            }
        }
        // Initialize variables to perform example operations on later
        // Note: This data is nonsensical and only for example operations.
        List<Integer> list = Arrays.asList(10,11,12,13,14,15); // Read-only definition
        Map<String, Map<Character, Integer>> map = new HashMap<>();
        String dummy = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0;i < dummy.length();i++) {
            String substr = dummy.substring(0, i);
            Map<Character, Integer> innerMap = new HashMap<>();
            for (int j = 0;j < substr.length();j++) {
                innerMap.put(substr.charAt(j), j);
            }
            map.put(substr, innerMap);
        }
        // Run operations
        System.out.println("[exemplifyGenericMethod] " + ExampleClass.getFirstElem(list));
        System.out.println("[exemplifyGenericMethod] " + ExampleClass.getTwoTieredKey(map, "abcdef", 'b'));
    }

    /**
     * This function exemplifies how to bound generics to certain types. By default, they are assumed to be Object, so by bounding we can get
     * access to more specific class methods and fields than the most parent in the heirarchy (Object).
     */
    public static void exemplifyBoundedGeneric() {
        class ExampleClass {
            // Single bound
            public static <T extends Number> T getFirstNumberElem (List<T> list) {
                return list.get(0);
            }
            // Multiple bounds
            // Note: There is actually a slight issue with this code. "Comparable" should be parameterized, but doing so requires wildcards which
            //  will be covered next.
            @SuppressWarnings({"all"})
            public static <T extends Number & Comparable, K extends Number & Comparable> int customCompare (T numOne, K numTwo) {
                return numOne.compareTo(numTwo);
            }
        }
        List<Integer> list = Arrays.asList(10,11,12,13,14,15); // Read-only definition
        System.out.println("[exemplifyBoundedGeneric] " + ExampleClass.getFirstNumberElem(list));
        System.out.println("[exemplifyBoundedGeneric] " + ExampleClass.customCompare(1, 2));
    }

    /**
     * This function exemplifies how to utilize the wildcard in generics
     * Note: Wildcard syntax allows for "<? extends T>" and "<? super T>" where one is a subtype and the other is a supertype
     */
    public static void exemplifyWildcard() {
        class ExampleClass {
            public static double addNumbersAsDouble (List<? extends Number> list) {
                double sum = 0d;
                for (Number num : list) {
                    sum += num.doubleValue();
                }
                return sum;
            }
            // This is a fixed version of this method seen prior, as this version parameterizes Comparable via wildcard
            public static <T extends Number & Comparable<? super K>, K extends Number & Comparable<? super T>> int customCompare (T numOne, K numTwo) {
                return numOne.compareTo(numTwo);
            }
        }
        List<Integer> list = Arrays.asList(10,11,12,13,14,15); // Read-only definition
        System.out.println("[exemplifyWildcard] " + ExampleClass.addNumbersAsDouble(list));
        System.out.println("[exemplifyWildcard] " + ExampleClass.customCompare(1, 2));
    }

    /**
     * This function exemplifies how to utilize generic classes
     * Note: Generic syntax is:
     *  class [Name] [Generic Info] {}
     */
    public static void exemplifyGenericClass() {
        class CustomGenericClass <T> {
            private T data;

            public CustomGenericClass(T data) {
                this.data = data;
            }
            public void setData(T data) {
                this.data = data;
            }
            public T getData() {
                return data;
            }
        }
        CustomGenericClass<String> genericClass = new CustomGenericClass<>("Initial String");
        genericClass.setData("Rewritten String");
        System.out.println("[exemplifyGenericClass] " + genericClass.getData());
    }

    /**
     * This function exemplifies an issue I personally kept running into when first learning generics, but
     * that is an error and should NOT be attempted. The error I continuously ran into was trying to get
     * information on the type which was assigned to the generic from within the class itself in a wholly
     * encompassing method which could fork behavior depending on type. Java explicitly goes OUT OF ITS
     * WAY to make that impossible (edit: not anymore, read ahead). I have researched it and am not sure why,
     * aside from conceptual hills being fought for, despite it feeling pretty functional to provide that ability.
     * EDIT: Newer versions of Java (as of Java 19) must have changed this because historically I know this
     *  never worked, and yet now it does, though with compiler warnings. Type erasure always made this throw
     *  an error on execution even though IDE's never suggested any flaws, and yet now it runs. Strange.
     *  Either way, it has been made CLEAR that this is considered sacrilegious, so keep that in mind.
     */
    public static void exemplifyCastingError() {
        class GenericErrorClass <T extends Object> {
            private Class<T> type;
            private T data;

            @SuppressWarnings("all")
            public GenericErrorClass(T data) {
                this.type = (Class<T>) data.getClass();
                this.data = data;
            }
            public void doSomething() {
                if (Integer.class.isAssignableFrom(type)) {
                    System.out.println("[exemplifyCastingError] Type is an Integer!: " + data);
                } else if (type.isArray() && Integer.class.isAssignableFrom(type.getComponentType())) {
                    System.out.println("[exemplifyCastingError] Type is an Integer[]!: " + Arrays.toString((Object[]) data));
                } else if (Double.class.isAssignableFrom(type)) {
                    System.out.println("[exemplifyCastingError] Type is a Double!: " + data);
                } else {
                    System.out.println("[exemplifyCastingError] Type is something unaccounted for!: " + data);
                }
            }
        }
        GenericErrorClass<Integer> gEC_int = new GenericErrorClass<>(1);
        gEC_int.doSomething();
        GenericErrorClass<Double> gEC_double = new GenericErrorClass<>(1d);
        gEC_double.doSomething();
        GenericErrorClass<String> gEC_String = new GenericErrorClass<>("Test");
        gEC_String.doSomething();
        GenericErrorClass<Integer[]> gEC_Array = new GenericErrorClass<>(new Integer[] {1,2,3});
        gEC_Array.doSomething();
    }
}
