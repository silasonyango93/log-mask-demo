package com.kcb.interview.silasonyango.logmask.starter.core;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcb.interview.silasonyango.logmask.starter.config.PIIMaskingProperties;
import java.util.List;
import org.junit.jupiter.api.Test;

class PIIMaskingServiceTest {

  @Test
  void masksConfiguredFieldsInNestedObjectsAndLists() {
    PIIMaskingProperties properties = new PIIMaskingProperties();
    properties.setEnabled(true);
    properties.setFields(List.of("email", "phoneNumber"));
    properties.setMaskStyle(PIIMaskingProperties.MaskStyle.FULL);
    properties.setMaskCharacter("*");

    PIIMaskingService service = new PIIMaskingService(new ObjectMapper(), properties);

    Demo demo = new Demo();
    demo.setEmail("john.doe@example.com");
    demo.setPhoneNumber("123456789");
    demo.getTags().add("public");

    String masked = service.maskForLogging(demo);

    assertThat(masked).doesNotContain("john.doe@example.com");
    assertThat(masked).doesNotContain("123456789");
    assertThat(masked).contains("********");
    assertThat(masked).contains("public");

    // original object must remain unmodified
    assertThat(demo.getEmail()).isEqualTo("john.doe@example.com");
    assertThat(demo.getPhoneNumber()).isEqualTo("123456789");
  }

  static class Demo {

    private String email;
    private String phoneNumber;
    private java.util.List<String> tags = new java.util.ArrayList<>();

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getPhoneNumber() {
      return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
    }

    public java.util.List<String> getTags() {
      return tags;
    }

    public void setTags(java.util.List<String> tags) {
      this.tags = tags;
    }
  }
}

