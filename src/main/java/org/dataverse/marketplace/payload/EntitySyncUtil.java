package org.dataverse.marketplace.payload;

import java.util.HashSet;
import java.util.Set;

public class EntitySyncUtil {

    public interface EntityBuilder<D, E> {
        E build(D dto);
        boolean matches(D dto, E entity);
    }

    // TODO: currently only work with not null existing (entities); consisder adding a Consumer to handle null
    public static <D, E> void syncEntities(Set<E> existing, Set<D> incomingDTOs, EntityBuilder<D, E> builder) {
        Set<E> toKeep = new HashSet<>();

        if (incomingDTOs != null) {
            for (D dto : incomingDTOs) {
                boolean matched = false;
                for (E entity : existing) {
                    if (builder.matches(dto, entity)) {
                        toKeep.add(entity);
                        matched = true;
                        break;
                    }
                }
                if (!matched) {
                    E newEntity = builder.build(dto);
                    toKeep.add(newEntity);
                }
            }
        }

        existing.clear();
        existing.addAll(toKeep);        
    }
   
}
