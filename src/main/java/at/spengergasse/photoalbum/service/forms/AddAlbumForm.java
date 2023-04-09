package at.spengergasse.photoalbum.service.forms;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor

public class AddAlbumForm {

    @NotBlank @Size(min=2, max=64)
    private String name;

    public boolean getRestricted() {
        return false;
    }

}
