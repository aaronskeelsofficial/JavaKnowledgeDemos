package work.aaronskeels.javaknowledgedemos;

public class App 
{
    public static void main( String[] args )
    {
        /*
         * Maven is for build automation and dependency management. It ALSO carries with it an implicit folder structure behind projects.
         *  - ROOT/src: Home to all source
         *  - ROOT/target: Home to all compilations
         *  - ROOT/src/main: Home to production
         *  - ROOT/src/test: Home to development
         *  - ROOT/src/~/java: Home to java code (can be overridden via build/sourceDirectory & build/testSourceDirectory tags)
         *  - ROOT/src/~/resources: Home to all resources which are not compile-prone java
         * Maven *can* be used for non-Java languages, but seems HEAVILY geared specifically to Java and using anything else appears to be a huge pain
         * There is assumed to be a main (production) execution and a "test" (development) execution
         * POM = Project Object Model
         * Maven only supports POM modelVersion 4.0.0 as a required tag
         * groupId does not FUNCTIONALLY need to match file/package structure or include "."s, more an ease-of-info tradition
         * Value placeholders (quasi-variables) are stored in <properties>
         * Plugin declarations go in "build/plugins", plugin HINTS/PREFERENCES go in "build/pluginManagement/plugins"
         *  - Plugins are often used for compiling source, running tests, and creating executables
         *  - (indirectly stated default plugins for some of those tasks default to Maven implementations)
         * Dependency ordering matters. Topmost/earlier declarations take priority in conflicts.
         * Add custom repos inside of <repositories> (though the documentation suggests that you should only use the Maven central repo)
         * Resource Filtering allows placeholders to be replaced in resource files during the build process
         */
    }
}
