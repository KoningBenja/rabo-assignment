package org.benja.services.validator;

import java.util.List;

public interface ValidatingFunction<T> {
    boolean couldBeApplied(T obj);
    void validate(List<T> obj);
}
