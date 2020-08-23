package com.example.mongo.app.account;


import com.example.mongo.app.common.validation.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.gfw.common.validator.constraints.Compare;
import org.terasoluna.gfw.common.validator.constraints.Compare.Operator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Compare(left = "email", right = "confirmEmail", operator = Operator.EQUAL, requireBoth = true, node = Compare.Node.ROOT_BEAN)
public class AccountCreateForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull                    //null禁止
    @NotContainControlChars     //制御文字禁止
    @Size(min = 4, max = 128)   //4文字以上128文字以下
    private String username;

    @NotNull
    @NotContainControlChars
    @Size(min = 1, max = 128)
    private String firstName;

    @NotNull
    @NotContainControlChars
    @Size(min = 1, max = 128)
    private String lastName;

    @NotNull
    @NotContainControlChars
    @Size(min = 1, max = 128)
    @DomainRestrictedEmail(allowedDomains = {"domainexample.co.jp", "somedomainexample.co.jp"}, allowSubDomain = true)
    private String email;

    @NotNull
    @NotContainControlChars
    private String confirmEmail;

    @NotNull
    @NotContainControlChars
    @DomainRestrictedURL(allowedDomains = {"jp"})
    private String url;

    @UploadFileRequired(groups = Confirm.class)
    @UploadFileNotEmpty(groups = Confirm.class)
    @UploadFileMaxSize
    @FileExtension(extensions = {"jpg", "png", "gif"})
    @FileNamePattern(pattern = "[a-zA-Z0-9_-]+\\.[a-zA-Z]{3}")
    private MultipartFile image;

    @NotNull(groups = CreateAccount.class)
    @Size(max = 40)
    private String imageId;

    @NotNull
    @NotContainControlCharsExceptNewlines
    private String profile;

    public static interface Confirm {
    }

    public static interface CreateAccount {
    }

}
