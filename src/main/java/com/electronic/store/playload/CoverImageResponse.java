package com.electronic.store.playload;

import lombok.*;
import org.springframework.http.HttpStatus;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoverImageResponse {


    private String coverImageName;
    private String message;
    private boolean success;
    private HttpStatus status;
}
