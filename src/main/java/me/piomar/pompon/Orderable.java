package me.piomar.pompon;

import java.util.List;

/**
 * Interface implemented by POM elements that can be ordered.
 */
public interface Orderable {

    List<String> getOrderViolations();

    // void makeOrder();

}
