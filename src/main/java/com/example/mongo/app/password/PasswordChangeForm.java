package com.example.mongo.app.password;


import com.example.mongo.app.common.validation.ConfirmOldPassword;
import com.example.mongo.app.common.validation.NotReusedPassword;
import com.example.mongo.app.common.validation.StrongPassword;
import lombok.Data;
import org.terasoluna.gfw.common.validator.constraints.Compare;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Compare(left = "newPassword", right = "confirmNewPassword", operator = Compare.Operator.EQUAL)
@StrongPassword(usernamePropertyName = "username", newPasswordPropertyName = "newPassword")
@NotReusedPassword(usernamePropertyName = "username", newPasswordPropertyName = "newPassword")
@ConfirmOldPassword(usernamePropertyName = "username", oldPasswordPropertyName = "oldPassword")
public class PasswordChangeForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String username;

    @NotNull
    private String oldPassword;

    @NotNull
    private String newPassword;

    @NotNull
    private String confirmNewPassword;

}
