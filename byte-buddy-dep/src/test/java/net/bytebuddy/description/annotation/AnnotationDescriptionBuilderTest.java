package net.bytebuddy.description.annotation;

import org.junit.Test;

import java.lang.annotation.Annotation;

public class AnnotationDescriptionBuilderTest {

    private static final String FOO = "foo", BAR = "bar", BAZ = "baz";

    @Test(expected = IllegalArgumentException.class)
    public void testNonMatchingAnnotationValue() throws Exception {
        AnnotationDescription.Builder.ofType(Qux.class).define(FOO, new QuxBaz.Instance());
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("unchecked")
    public void testNonMatchingEnumerationArrayValue() throws Exception {
        AnnotationDescription.Builder.ofType(Foo.class).defineEnumerationArray(BAR, (Class) Bar.class, Bar.FIRST, FooBar.SECOND);
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("unchecked")
    public void testNonMatchingAnnotationArrayValue() throws Exception {
        AnnotationDescription.Builder.ofType(Foo.class).defineAnnotationArray(BAZ, (Class) Qux.class, new Qux.Instance(), new QuxBaz.Instance());
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("unchecked")
    public void testNonAnnotationType() throws Exception {
        AnnotationDescription.Builder.ofType((Class) Object.class);
    }

    @Test(expected = IllegalStateException.class)
    public void testIncompleteAnnotation() throws Exception {
        AnnotationDescription.Builder.ofType(Foo.class).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnknownProperty() throws Exception {
        AnnotationDescription.Builder.ofType(Foo.class).define(FOO + BAR, FOO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateProperty() throws Exception {
        AnnotationDescription.Builder.ofType(Foo.class).define(BAZ, FOO).define(BAZ, FOO);
    }

    public enum Bar {
        FIRST,
        SECOND
    }

    public enum FooBar {
        FIRST,
        SECOND
    }

    public @interface Foo {

        Bar foo();

        Bar[] bar();

        Qux qux();

        String baz();
    }

    public @interface Qux {

        class Instance implements Qux {

            public Class<? extends Annotation> annotationType() {
                return Qux.class;
            }
        }
    }

    public @interface QuxBaz {

        class Instance implements QuxBaz {

            public Class<? extends Annotation> annotationType() {
                return QuxBaz.class;
            }
        }
    }
}
