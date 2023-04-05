package sqlcomponentizer.preparedstatement.component;

public interface PSComponent {

    /***
     * A ComponentString is a component of an SQL statement, including commands, clauses, and conditionals. If it contains placeholders, try using PSPlaceholderComponent
     *
     * @return - Complete string not including placeholders
     */
    String getComponentString();
}
