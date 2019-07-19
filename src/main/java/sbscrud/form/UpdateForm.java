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

    @Email(groups = Group1.class, message = "メールアドレスの形式が正しくありません。")
    private String newMailAddress;

}
