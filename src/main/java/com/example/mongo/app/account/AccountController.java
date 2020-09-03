package com.example.mongo.app.account;


import com.example.mongo.app.account.AccountCreateForm.Confirm;
import com.example.mongo.app.account.AccountCreateForm.CreateAccount;
import com.example.mongo.app.common.StringUtils;
import com.example.mongo.app.common.datatables.DataTablesInput;
import com.example.mongo.app.common.datatables.DataTablesOutput;
import com.example.mongo.domain.model.authentication.Account;
import com.example.mongo.domain.model.authentication.AccountImage;
import com.example.mongo.domain.model.authentication.LoggedInUser;
import com.example.mongo.domain.model.common.TempFile;
import com.example.mongo.domain.repository.authentication.AccountImageRepository;
import com.example.mongo.domain.service.authentication.AccountService;
import com.example.mongo.domain.service.authentication.AccountSharedService;
import com.example.mongo.domain.service.common.FileUploadSharedService;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.message.ResultMessages;

import javax.validation.groups.Default;
import java.io.IOException;
import java.util.*;

import static com.example.mongo.app.common.constants.MessageKeys.E_SL_AC_5001;

@Slf4j
@Controller
@RequestMapping("account")
public final class AccountController {

    @Autowired
    private FileUploadSharedService fileUploadSharedService;

    @Autowired
    private AccountSharedService accountSharedService;

    @Autowired
    private AccountImageRepository accountRepository;

    @Autowired
    private Mapper beanMapper;

    @Autowired
    private AccountService accountService;

    @ModelAttribute
    public AccountCreateForm setUpAccountCreateForm() {
        return new AccountCreateForm();
    }

    @GetMapping
    public String view(@AuthenticationPrincipal LoggedInUser userDetails,
                       Model model) {
        Account account = userDetails.getAccount();
        model.addAttribute("account", account);
        return "account/view";
    }

    @GetMapping("/image")
    @ResponseBody
    public ResponseEntity<byte[]> showImage(
            @AuthenticationPrincipal LoggedInUser userDetails)
            throws IOException {
        AccountImage userImage = accountSharedService.getImage(userDetails.getUsername());
        HttpHeaders headers = new HttpHeaders();
        if (userImage.getExtension().equalsIgnoreCase("png")) {
            headers.setContentType(MediaType.IMAGE_PNG);
        } else if (userImage.getExtension().equalsIgnoreCase("gif")) {
            headers.setContentType(MediaType.IMAGE_GIF);
        } else if (userImage.getExtension().equalsIgnoreCase("jpg")) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        }
        return new ResponseEntity<byte[]>(userImage.getBody().getData(), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/create", params = "form")
    public String createForm() {
        return "account/accountCreateForm";
    }

    @PostMapping(value = "/create", params = "redo")
    public String redoCreateForm(AccountCreateForm form) {
        return "account/accountCreateForm";
    }

    @PostMapping(value = "/create", params = "confirm")
    public String createConfirm(
            @Validated({Confirm.class, Default.class}) AccountCreateForm form,
            BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return createForm();
        }
        if (accountSharedService.exists(form.getUsername())) {
            model.addAttribute(ResultMessages.error().add(E_SL_AC_5001));
            return createForm();
        }
        try {
            TempFile tempFile = new TempFile();
            tempFile.setBody(new Binary(BsonBinarySubType.BINARY, form.getImage().getBytes()));
            tempFile.setOriginalName(form.getImage().getOriginalFilename());
            String fileId = fileUploadSharedService.uploadTempFile(tempFile);
            form.setImageId(fileId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        redirectAttributes.addFlashAttribute("accountCreateForm", form);
        return "account/accountConfirm";
    }

    @PostMapping("/create")
    public String create(
            @Validated({CreateAccount.class, Default.class}) AccountCreateForm form,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return createForm();
        }
        Account account = beanMapper.map(form, Account.class);
        account.setRoles(Arrays.asList("USER"));

        String password = accountSharedService.create(account,
                form.getImageId());
        redirectAttributes.addFlashAttribute("firstName", form.getFirstName());
        redirectAttributes.addFlashAttribute("lastName", form.getLastName());
        redirectAttributes.addFlashAttribute("password", password);
        return "redirect:/account/create?complete";
    }

    @GetMapping(value = "/create", params = "complete")
    public String createComplete() {
        return "account/createComplete";
    }


    /**
     * 一覧画面の表示
     */
    @GetMapping(value = "list")
    public String list(Model model) {

        return "account/list";
    }


    @ResponseBody
    @RequestMapping(value = "/list/json", method = RequestMethod.GET)
    public DataTablesOutput<AccountListBean> getListJson(@Validated DataTablesInput input) {

        RowBounds rowBounds = new RowBounds(input.getStart(), input.getLength());
        List<Account> accountList = accountService.findByDatatablesInput(input);

        // 追加項目、HTMLエスケープ
        List<AccountListBean> accountListBeanList = new ArrayList<>();
        for (Account account : accountList) {
            AccountListBean accountListBean = beanMapper.map(account, AccountListBean.class);
            accountListBean.setOperations("<a href=\"http://www.stnet.co.jp\">参照</a>");


            accountListBean.setDT_RowId(account.getUsername() + "_");
            accountListBean.setDT_RowClass("abcclass");

            Map<String, String> attr = new HashMap<>();
            attr.put("width", "100px");
            accountListBean.setDT_RowAttr(attr);

            accountListBeanList.add(accountListBean);
        }

        DataTablesOutput<AccountListBean> output = new DataTablesOutput<>();
        output.setData(accountListBeanList);
        output.setDraw(input.getDraw());
        output.setRecordsTotal(accountService.countByDatatablesInput(null));
        output.setRecordsFiltered(accountService.countByDatatablesInput(input));

        return output;
    }

    @GetMapping("initdata")
    public String initData(Model model) {

        accountService.initMany(1000, 1000);

        return "account/initdataComplate";

    }

}
