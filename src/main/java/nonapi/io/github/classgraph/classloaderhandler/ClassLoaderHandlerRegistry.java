/*
 * This file is part of ClassGraph.
 *
 * Author: Luke Hutchison
 *
 * Hosted at: https://github.com/classgraph/classgraph
 *
 * --
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Luke Hutchison
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package nonapi.io.github.classgraph.classloaderhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.github.classgraph.ClassGraphException;
import nonapi.io.github.classgraph.utils.LogNode;

/** The registry for ClassLoaderHandler classes. */
public class ClassLoaderHandlerRegistry {
    /**
     * Default ClassLoaderHandlers.
     */
    public static final List<ClassLoaderHandlerRegistryEntry> CLASS_LOADER_HANDLERS;

    static {
        //  If a ClassLoaderHandler is added to ClassGraph, it should be added to this list.
        final List<ClassLoaderHandlerRegistryEntry> builtInHandlers = Arrays.asList(
                // ClassLoaderHandlers for other ClassLoaders that are handled by ClassGraph
                new ClassLoaderHandlerRegistryEntry(AntClassLoaderHandler.class),
                new ClassLoaderHandlerRegistryEntry(EquinoxClassLoaderHandler.class),
                new ClassLoaderHandlerRegistryEntry(EquinoxContextFinderClassLoaderHandler.class),
                new ClassLoaderHandlerRegistryEntry(FelixClassLoaderHandler.class),
                new ClassLoaderHandlerRegistryEntry(JBossClassLoaderHandler.class),
                new ClassLoaderHandlerRegistryEntry(WeblogicClassLoaderHandler.class),
                new ClassLoaderHandlerRegistryEntry(WebsphereLibertyClassLoaderHandler.class),
                new ClassLoaderHandlerRegistryEntry(WebsphereTraditionalClassLoaderHandler.class),
                new ClassLoaderHandlerRegistryEntry(OSGiDefaultClassLoaderHandler.class),
                new ClassLoaderHandlerRegistryEntry(SpringBootRestartClassLoaderHandler.class),
                new ClassLoaderHandlerRegistryEntry(TomcatWebappClassLoaderBaseHandler.class),

                // For unit testing of PARENT_LAST delegation order
                new ClassLoaderHandlerRegistryEntry(ParentLastDelegationOrderTestClassLoaderHandler.class),

                // JPMS support
                new ClassLoaderHandlerRegistryEntry(JPMSClassLoaderHandler.class),

                // Java 7/8 support (list last, as fallback)
                new ClassLoaderHandlerRegistryEntry(URLClassLoaderHandler.class));

        final List<ClassLoaderHandlerRegistryEntry> registeredHandlers = new ArrayList<>(builtInHandlers);

        CLASS_LOADER_HANDLERS = Collections.unmodifiableList(registeredHandlers);
    }

    /** The fallback ClassLoaderHandler. Do not need to add FallbackClassLoaderHandler to the above list. */
    public static final ClassLoaderHandlerRegistryEntry FALLBACK_CLASS_LOADER_HANDLER = //
            new ClassLoaderHandlerRegistryEntry(FallbackClassLoaderHandler.class);

    /**
     * Lib dirs whose jars should be added to the classpath automatically (to compensate for some classloaders not
     * explicitly listing these jars as classpath elements).
     */
    public static final String[] AUTOMATIC_LIB_DIR_PREFIXES = {
            // Spring-Boot
            "BOOT-INF/lib/", "BOOT-INF/lib-provided/",
            // Tomcat
            "WEB-INF/lib/", "WEB-INF/lib-provided/",
            // Tomcat and others
            "lib/",
            // Extension dir
            "lib/ext/" //
    };

    /**
     * Automatic classfile prefixes (to compensate for some classloaders not explicitly listing these prefixes as
     * part of the classpath element URL or path).
     */
    public static final String[] AUTOMATIC_PACKAGE_ROOT_PREFIXES = {
            // Ant, Tomcat and others
            "classes/",
            // Ant
            "test-classes/",
            // Spring-Boot
            "BOOT-INF/classes/",
            // Tomcat
            "WEB-INF/classes/" // 
    };

    /**
     * Constructor.
     */
    private ClassLoaderHandlerRegistry() {
        // Cannot be constructed
    }

    /**
     * A list of fully-qualified ClassLoader class names paired with the ClassLoaderHandler that can handle them.
     */
    public static class ClassLoaderHandlerRegistryEntry {
        /** The names of handled ClassLoaders. */
        public final String[] handledClassLoaderNames;
        /** The ClassLoader class.. */
        public final Class<? extends ClassLoaderHandler> classLoaderHandlerClass;

        /**
         * Constructor.
         *
         * @param classLoaderHandlerClass
         *            The ClassLoaderHandler class.
         */
        private ClassLoaderHandlerRegistryEntry(final Class<? extends ClassLoaderHandler> classLoaderHandlerClass) {
            this.classLoaderHandlerClass = classLoaderHandlerClass;
            try {
                // Instantiate each ClassLoaderHandler in order to call the handledClassLoaders() method (this is
                // needed because Java doesn't support inherited static interface methods)
                this.handledClassLoaderNames = classLoaderHandlerClass.getDeclaredConstructor().newInstance()
                        .handledClassLoaders();
            } catch (final ReflectiveOperationException | ExceptionInInitializerError e) {
                throw ClassGraphException
                        .newClassGraphException("Could not instantiate " + classLoaderHandlerClass.getName(), e);
            }
        }

        /**
         * Instantiate a ClassLoaderHandler, or return null if the class could not be instantiated.
         *
         * @param log
         *            The log.
         * @return The ClassLoaderHandler instance.
         */
        public ClassLoaderHandler instantiate(final LogNode log) {
            try {
                // Instantiate a ClassLoaderHandler
                return classLoaderHandlerClass.getDeclaredConstructor().newInstance();
            } catch (final ReflectiveOperationException | ExceptionInInitializerError e) {
                if (log != null) {
                    log.log("Could not instantiate " + classLoaderHandlerClass.getName(), e);
                }
                return null;
            }
        }
    }
}
