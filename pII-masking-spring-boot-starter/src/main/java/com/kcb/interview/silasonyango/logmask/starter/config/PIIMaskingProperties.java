package com.kcb.interview.silasonyango.logmask.starter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "pii.masking")
public class PIIMaskingProperties {

    /**
     * Enable / disable masking entirely.
     */
    private boolean enabled = true;

    /**
     * Field names to treat as sensitive (case-insensitive).
     */
    private List<String> fields = new ArrayList<>();

    /**
     * Masking style: FULL, PARTIAL, LAST4.
     */
    private MaskStyle maskStyle = MaskStyle.PARTIAL;

    /**
     * Character used when masking.
     */
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

