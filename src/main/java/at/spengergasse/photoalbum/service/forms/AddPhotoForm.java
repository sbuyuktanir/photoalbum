package at.spengergasse.photoalbum.service.forms;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor

public class AddPhotoForm {

    @NotBlank @Size(min=2, max=64)
    private String name;

    @NotBlank @Size(min=2, max=64)
    private String fileName;

    @Positive @Max(4096)
    private Integer width;

    @Positive @Max(4096)
    private Integer height;

    @NotNull
    String photographerKey;
}
