package com.curso.algasensors.temperature.processing;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.curso.algasensors.temperature.processing.utils.IdGenerator;
import com.curso.algasensors.temperature.processing.utils.UUIDv7Utils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UUIDv7Test {

    @Test
    void shouldGenerateUUIDv7() {
        UUID uuid = IdGenerator.generateTimeBasedUUID();
        OffsetDateTime uuidOffsetDateTime = UUIDv7Utils.extractOffsetDateTime(uuid).truncatedTo(ChronoUnit.MINUTES);
        OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        Assertions.assertThat(uuidOffsetDateTime).isEqualTo(now);

    }
}
