package com.kcb.interview.silasonyango.logmask.starter.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kcb.interview.silasonyango.logmask.starter.config.PIIMaskingProperties;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class PIIMaskingService {

  private final ObjectMapper objectMapper;
  private final PIIMaskingProperties properties;
  private final Set<String> fieldNamesLowerCase;

  public PIIMaskingService(ObjectMapper objectMapper, PIIMaskingProperties properties) {
    this.objectMapper = objectMapper;
    this.properties = properties;
    this.fieldNamesLowerCase = new HashSet<>();
    for (String f : properties.getFields()) {
      fieldNamesLowerCase.add(f.toLowerCase(Locale.ROOT));
    }
  }

  public String maskForLogging(Object source) {
    if (!properties.isEnabled() || source == null) {
      try {
        return objectMapper.writeValueAsString(source);
      } catch (JsonProcessingException e) {
        return String.valueOf(source);
      }
    }

    try {
      JsonNode root = objectMapper.valueToTree(source);
      JsonNode masked = maskNode(root);
      return objectMapper.writeValueAsString(masked);
    } catch (JsonProcessingException e) {
      return String.valueOf(source);
    }
  }

  private JsonNode maskNode(JsonNode node) {
    if (node == null || node.isNull()) {
      return node;
    }

    if (node.isObject()) {
      ObjectNode obj = (ObjectNode) node;
      Iterator<Map.Entry<String, JsonNode>> fields = obj.fields();

      while (fields.hasNext()) {
        Map.Entry<String, JsonNode> entry = fields.next();
        String fieldName = entry.getKey();
        JsonNode child = entry.getValue();

        if (fieldNamesLowerCase.contains(fieldName.toLowerCase(Locale.ROOT))
            && child.isValueNode()) {
          String masked = applyMask(child.asText());
          obj.put(fieldName, masked);
        } else {
          obj.set(fieldName, maskNode(child));
        }
      }
      return obj;
    }

    if (node.isArray()) {
      ArrayNode arrayNode = (ArrayNode) node;

      for (int i = 0; i < arrayNode.size(); i++) {
        JsonNode child = arrayNode.get(i);
        arrayNode.set(i, maskNode(child));
      }

      return arrayNode;
    }
    return node;
  }

  private String applyMask(String value) {
    if (value == null) {
      return null;
    }
    String maskChar = properties.getMaskCharacter();
    PIIMaskingProperties.MaskStyle style = properties.getMaskStyle();

    return switch (style) {
      case FULL -> maskChar.repeat(value.length());
      case PARTIAL -> {
        if (value.length() <= 2) {
          yield maskChar.repeat(value.length());
        }
        int visible = Math.min(2, value.indexOf("@") > 0 ? 1 : 2);
        String visiblePrefix = value.substring(0, visible);
        yield visiblePrefix + maskChar.repeat(value.length() - visible);
      }
      case LAST4 -> {
        if (value.length() <= 4) {
          yield maskChar.repeat(value.length());
        }
        String last4 = value.substring(value.length() - 4);
        yield maskChar.repeat(value.length() - 4) + last4;
      }
    };
  }
}

