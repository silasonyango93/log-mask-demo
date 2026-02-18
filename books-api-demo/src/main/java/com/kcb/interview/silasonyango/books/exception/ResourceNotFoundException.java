package com.kcb.interview.silasonyango.books.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ResourceNotFoundException extends RuntimeException {

  private String message;

  public ResourceNotFoundException(String message) {
    super(message);
    this.message = message;
  }
}
