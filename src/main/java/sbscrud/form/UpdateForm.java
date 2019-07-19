package sbscrud.form;

import lombok.Getter;
import lombok.Setter;
import sbscrud.validation.GroupOrder.Group1;

import javax.validation.constraints.Email;

@Getter
@Setter
public class UpdateForm {

    private String newFirstName;

    private String newLastName;

    @Email(groups = Group1.class)
    private String newMailAddress;

}
