package at.spengergasse.photoalbum.service.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EditAlbumForm {

    @NotBlank @Size(min=2, max=64)
    private String name;

    @NotNull
    private boolean restricted;
}
