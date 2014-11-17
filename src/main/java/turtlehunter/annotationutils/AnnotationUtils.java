package turtlehunter.annotationutils;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Set;

public class AnnotationUtils {

    private Class annotation;
    private ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
    private boolean useAnnotation = false;
    private String packageName;

    public AnnotationUtils() {

    }

    public AnnotationUtils fromClasspath() {
        configurationBuilder.addUrls(ClasspathHelper.forJavaClassPath());
        return this;
    }

    public AnnotationUtils fromClassLoader(ClassLoader classLoader) {
        configurationBuilder.addClassLoader(classLoader);
        return this;
    }

    public AnnotationUtils fromURL(URL[] urls){
        configurationBuilder.addUrls(urls);
        return this;
    }

    public AnnotationUtils fromURL(URL url) {
        configurationBuilder.addUrls(url);
        return this;
    }

    public AnnotationUtils forAnnotation(Class annotation) {
        this.annotation = annotation;
        this.useAnnotation = true;
        return this;
    }

    public AnnotationUtils forPackage(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public Set<Method> getMethods() {
        if(useAnnotation) {
            return new Reflections(configurationBuilder.setScanners(new MethodAnnotationsScanner())).getMethodsAnnotatedWith(annotation);
        }
        return null;
    }

    public Set<Class<?>> getClasses() {
        if (useAnnotation) {
            return new Reflections(configurationBuilder.setScanners(new FieldAnnotationsScanner())).getTypesAnnotatedWith(annotation);
        }
        return new Reflections(packageName).getSubTypesOf(Object.class); //should return everything
    }

    public Set<Field> getFields() {
        if (useAnnotation) {
            return new Reflections(configurationBuilder.addScanners(new FieldAnnotationsScanner())).getFieldsAnnotatedWith(annotation);
        }
        return null;
    }

    public ConfigurationBuilder getConfigurationBuilder() {
        return configurationBuilder;
    }

    //useless methods just to make the sentence look good

    public AnnotationUtils and() {
        return this;
    }

}
