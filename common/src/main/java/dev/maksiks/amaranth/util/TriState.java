/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package dev.maksiks.amaranth.util;

public enum TriState {
    /**
     * Represents the boolean value {@code true}.
     */
    TRUE,
    /**
     * Represents a "default" value, often used as a fallback.
     */
    DEFAULT,
    /**
     * Represents the boolean value {@code false}.
     */
    FALSE;

    // Helper methods for use in patches

    public boolean isTrue() {
        return this == TRUE;
    }

    public boolean isDefault() {
        return this == DEFAULT;
    }

    public boolean isFalse() {
        return this == FALSE;
    }
}
