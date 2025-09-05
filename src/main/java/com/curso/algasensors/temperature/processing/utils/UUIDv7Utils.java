package com.curso.algasensors.temperature.processing.utils;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class UUIDv7Utils {

    private UUIDv7Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static OffsetDateTime extractOffsetDateTime(UUID uuid) {
        if (uuid == null) return null;
        long timestamp = uuid.getMostSignificantBits() >>> 16;
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }
}
