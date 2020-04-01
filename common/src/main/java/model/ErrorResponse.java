package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Builder
public class ErrorResponse {

  private List<String> message;

  @Tolerate
  public ErrorResponse() {
    message = new ArrayList<>();
  }


  public void with(ErrorResponse errorResponse) {
    if (errorResponse == null) {
      return;
    }

    if (this.message == null) {
      this.message = new ArrayList<>();
    }

    this.message.addAll(errorResponse.getMessage());
  }

  public boolean hasErrors() {
    return !this.message.isEmpty();
  }
}
