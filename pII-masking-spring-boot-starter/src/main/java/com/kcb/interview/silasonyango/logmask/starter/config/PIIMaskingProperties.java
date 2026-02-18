package com.kcb.interview.silasonyango.logmask.starter.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pii.masking")
public class PIIMaskingProperties {


  private boolean enabled = true;
  private List<String> fields = new ArrayList<>();
  private MaskStyle maskStyle = MaskStyle.PARTIAL;
  private String maskCharacter = "*";

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public List<String> getFields() {
    return fields;
  }

  public void setFields(List<String> fields) {
    this.fields = fields;
  }

  public MaskStyle getMaskStyle() {
    return maskStyle;
  }

  public void setMaskStyle(MaskStyle maskStyle) {
    this.maskStyle = maskStyle;
  }

  public String getMaskCharacter() {
    return maskCharacter;
  }

  public void setMaskCharacter(String maskCharacter) {
    this.maskCharacter = maskCharacter;
  }

  public enum MaskStyle {
    FULL,
    PARTIAL,
    LAST4
  }
}

