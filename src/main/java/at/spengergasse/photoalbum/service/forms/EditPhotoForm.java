package at.spengergasse.photoalbum.service.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EditPhotoForm {

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
