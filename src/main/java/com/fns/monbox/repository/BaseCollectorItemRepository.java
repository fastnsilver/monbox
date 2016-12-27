package com.fns.monbox.repository;

import com.fns.monbox.model.CollectorItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

/**
 * Base {@link CollectorItem} repository that provides methods useful for any {@link CollectorItem}
 * implementation.
 *
 * @param <T> Class that extends {@link CollectorItem}
 */
public interface BaseCollectorItemRepository<T extends CollectorItem> extends CrudRepository<T, String> {

    /**
     * Finds all {@link CollectorItem}s that are enabled.
     *
     * @return list of {@link CollectorItem}s
     */
    List<T> findByEnabledIsTrue();

    /**
     * Finds the {@link CollectorItem} for a given collector and options. This should represent a unique
     * instance of a {@link CollectorItem} for a given {@link com.fns.monbox.model.Collector}.
     *
     * @param collectorId {@link com.fns.monbox.model.Collector} id
     * @param options options
     * @return a {@link CollectorItem}
     */
    T findByCollectorIdAndOptions(String collectorId, Map<String, Object> options);
    
    List<T> findByCollectorIdAndEnabled(String collectorId, boolean enabled);

    List<T> findByCollectorIdAndNiceName(String collectorId, String niceName);
}
