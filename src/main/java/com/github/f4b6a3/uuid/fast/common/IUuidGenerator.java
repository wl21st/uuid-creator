package com.github.f4b6a3.uuid.fast.common;

import java.util.UUID;

/**
 * Uuid generator interface.
 *
 * @author sfuser
 */
public interface IUuidGenerator {

    /**
     * Generate a new single entry of the UUID.
     *
     * @return generated single entry.
     */
    UUID generate();


}
