package me.piomar.pompon;

import java.util.Optional;

/**
 * Interface implemented by POM elements that can be ordered.
 */
public interface Orderable {

    Optional<String> isUnordered();

    // void makeOrder();

}
