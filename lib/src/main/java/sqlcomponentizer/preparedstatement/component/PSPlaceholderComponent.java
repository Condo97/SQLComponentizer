package sqlcomponentizer.preparedstatement.component;

import java.util.List;

public interface PSPlaceholderComponent extends PSComponent {

    /***
     * Gets the ordered placeholder objects for a component. This is just a List of Objects. If used alongside the getComponentString method, a PreparedStatement can be constructed by filling the placeholders with the values IN ORDER from this method.
     *
     * @return - The ordered object list that corresponds to the placeholders given in getComponentString, in order
     */
    List<Object> getOrderedPlaceholderValues();
}
