package sqlcomponentizer.dbserializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBColumn {

    String name();
    boolean isPrimaryKey() default false;
    boolean isForeignKey() default false;
    String foreignKeyReferences() default "";

}
