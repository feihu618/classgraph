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

import nonapi.io.github.classgraph.ScanSpec;
import nonapi.io.github.classgraph.classpath.ClasspathOrder;
import nonapi.io.github.classgraph.utils.LogNode;
import nonapi.io.github.classgraph.utils.ReflectionUtils;

/**
 * Fallback ClassLoaderHandler. Tries to get classpath from a range of possible method and field names.
 */
class FallbackClassLoaderHandler implements ClassLoaderHandler {

    /* (non-Javadoc)
     * @see nonapi.io.github.classgraph.classloaderhandler.ClassLoaderHandler#handledClassLoaders()
     */
    @Override
    public String[] handledClassLoaders() {
        // The actual string "*" is unimportant here, it is ignored
        return new String[] { "*" };
    }

    /* (non-Javadoc)
     * @see nonapi.io.github.classgraph.classloaderhandler.ClassLoaderHandler#getEmbeddedClassLoader(java.lang.ClassLoader)
     */
    @Override
    public ClassLoader getEmbeddedClassLoader(final ClassLoader outerClassLoaderInstance) {
        return null;
    }

    /* (non-Javadoc)
     * @see nonapi.io.github.classgraph.classloaderhandler.ClassLoaderHandler#getDelegationOrder(java.lang.ClassLoader)
     */
    @Override
    public DelegationOrder getDelegationOrder(final ClassLoader classLoaderInstance) {
        return DelegationOrder.PARENT_FIRST;
    }

    /* (non-Javadoc)
     * @see nonapi.io.github.classgraph.classloaderhandler.ClassLoaderHandler#handle(nonapi.io.github.classgraph.ScanSpec, java.lang.ClassLoader, nonapi.io.github.classgraph.classpath.ClasspathOrder, nonapi.io.github.classgraph.utils.LogNode)
     */
    @Override
    public void handle(final ScanSpec scanSpec, final ClassLoader classLoader,
            final ClasspathOrder classpathOrderOut, final LogNode log) {
        boolean valid = false;
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "getClassPath", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "getClasspath", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "classpath", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "classPath", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.invokeMethod(classLoader, "cp", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.getFieldVal(classLoader, "classpath", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.getFieldVal(classLoader, "classPath", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.getFieldVal(classLoader, "cp", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "getPath", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "getPaths", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.invokeMethod(classLoader, "path", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "paths", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.getFieldVal(classLoader, "paths", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.getFieldVal(classLoader, "paths", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "getDir", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "getDirs", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.invokeMethod(classLoader, "dir", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.invokeMethod(classLoader, "dirs", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.getFieldVal(classLoader, "dir", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.getFieldVal(classLoader, "dirs", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "getFile", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "getFiles", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.invokeMethod(classLoader, "file", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "files", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.getFieldVal(classLoader, "file", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.getFieldVal(classLoader, "files", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "getJar", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "getJars", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.invokeMethod(classLoader, "jar", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.invokeMethod(classLoader, "jars", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.getFieldVal(classLoader, "jar", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.getFieldVal(classLoader, "jars", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "getURL", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "getURLs", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "getUrl", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(
                ReflectionUtils.invokeMethod(classLoader, "getUrls", false), classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.invokeMethod(classLoader, "url", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.invokeMethod(classLoader, "urls", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.getFieldVal(classLoader, "url", false),
                classLoader, log);
        valid |= classpathOrderOut.addClasspathEntryObject(ReflectionUtils.getFieldVal(classLoader, "urls", false),
                classLoader, log);
        if (log != null) {
            log.log("FallbackClassLoaderHandler " + (valid ? "found" : "did not find")
                    + " classpath entries in unknown ClassLoader " + classLoader);
        }
    }
}
