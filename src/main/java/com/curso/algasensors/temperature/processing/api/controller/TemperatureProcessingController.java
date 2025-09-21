package com.curso.algasensors.temperature.processing.api.controller;

import com.curso.algasensors.temperature.processing.api.model.TemperatureLogOutput;
import com.curso.algasensors.temperature.processing.common.IdGenerator;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

import static com.curso.algasensors.temperature.processing.infrastructure.rabbitmq.RabbitMQConfig.EXCHANGE_NAME;

@RestController
@RequestMapping("/api/sensors/{sensorId}/temperatures/data")
@RequiredArgsConstructor
public class TemperatureProcessingController {

    private final RabbitTemplate rabbitTemplate;

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public void data(@PathVariable TSID sensorId, @RequestBody String input) {
        if(input == null || input.isBlank()) throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        double temperature;
        try {
            temperature = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        TemperatureLogOutput temperatureLog = TemperatureLogOutput.builder()
                .id(IdGenerator.generateTimeBasedUUID())
                .sensorId(sensorId)
                .registredAt(OffsetDateTime.now())
                .temperature(temperature)
                .build();
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setHeader("sensorId",temperatureLog.getSensorId().toString());
            return message;
        };
        rabbitTemplate.convertAndSend(
                EXCHANGE_NAME
                ,"",
                temperatureLog,
                messagePostProcessor);
    }
}
