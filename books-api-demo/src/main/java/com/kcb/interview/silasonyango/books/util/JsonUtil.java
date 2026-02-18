package com.kcb.interview.silasonyango.books.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcb.interview.silasonyango.logmask.starter.core.PIIMaskingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JsonUtil {

  private final ObjectMapper objectMapper;

  public <T> String toJsonString(T object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error("Error parsing object: error -> {}", e.getMessage());
      return null;
    }
  }
}