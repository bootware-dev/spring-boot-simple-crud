package sbscrud.form;

import lombok.Getter;
import lombok.Setter;
import sbscrud.validation.GroupOrder.Group1;
import sbscrud.validation.GroupOrder.Group2;
import sbscrud.validation.UserExists;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class SignupForm implements Serializable {

    @Pattern(groups = Group1.class, regexp = "^\\w{3,32}$", message = "大小英数（アンダースコア、ハイフンを含む）3〜32文字で入力してください。")
    @UserExists(groups = Group2.class)
    private String username;

    @Email(groups = Group1.class, message = "メールアドレスの形式が正しくありません。")
    private String mailAddress;

    @Pattern(groups = Group1.class, regexp = "^\\w{3,32}$", message = "大小英数（アンダースコア、ハイフンを含む）3〜32文字で入力してください。")
    private String password1;

    private String password2;

    @Size(groups = Group1.class, min = 1, max = 32, message = "1〜32文字で入力してください。")
    private String firstName;

    @Size(groups = Group1.class, min = 1, max = 32, message = "1〜32文字で入力してください。")
    private String lastName;

    private boolean admin;

    private boolean validPassword;

    @AssertTrue(groups = {Group2.class}, message = "パスワードが一致しません。")
    public boolean isValidPassword() {
        if (password1 == null) return false;
        if (password2 == null) return false;
        return password1.equals(password2);
    }

}
