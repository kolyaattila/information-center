package model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {

  List<String> message;

  public ErrorResponse with(ErrorResponse errorResponse) {
    if (message.isEmpty()) {
      return errorResponse;
    } else {
      this.message.addAll(errorResponse.getMessage());
      return this;
    }
  }

  public boolean hasErrors() {
    return !this.message.isEmpty();
  }
}
