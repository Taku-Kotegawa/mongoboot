package com.example.mongo.app.account;


import com.example.mongo.app.account.AccountCreateForm.Confirm;
import com.example.mongo.app.account.AccountCreateForm.CreateAccount;
import com.example.mongo.domain.model.authentication.Account;
import com.example.mongo.domain.model.authentication.AccountImage;
import com.example.mongo.domain.model.authentication.LoggedInUser;
import com.example.mongo.domain.model.common.TempFile;
import com.example.mongo.domain.repository.authentication.AccountImageRepository;
import com.example.mongo.domain.service.authentication.AccountSharedService;
import com.example.mongo.domain.service.common.FileUploadSharedService;
import com.github.dozermapper.core.Mapper;
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
import java.util.Arrays;

import static com.example.mongo.app.common.constants.MessageKeys.E_SL_AC_5001;

@Controller
@RequestMapping("account")
public final class AccountController {

    @Autowired
    private FileUploadSharedService fileUploadSharedService;

    @Autowired
    private AccountSharedService accountSharedService;

    @Autowired
    AccountImageRepository accountRepository;

    @Autowired
    private Mapper beanMapper;

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

//    @ResponseBody
//    @RequestMapping(value = "/list/json2", method = RequestMethod.GET)
//    public DataTablesOutput<AccountListBean> getUsers(@Valid DataTablesInput input) {
//
//        List<AccountListBean> accountListBeanList = new ArrayList<>();
//        DataTablesOutput<AccountListBean> output = new DataTablesOutput<>();
//
//        RowBounds rowBounds = new RowBounds(input.getStart(), input.getLength());
//
//
//        AccountExample example = new AccountExample();
//
//        // グローバルフィルタの入力値(input.getSearch().getValue())は、検索可能な項目に対するOR条件
//        // 大文字小文字の区別なし
//        if (!StringUtils.isEmpty(input.getSearch().getValue())) {
//            String gSearchWord = '%' + input.getSearch().getValue() + '%';
//            example.or().andUsernameLike(gSearchWord);
//            example.or().andFirstNameLike(gSearchWord);
//            example.or().andLastNameLike(gSearchWord);
//            example.or().andEmailLike(gSearchWord);
//            example.or().andUrlLike(gSearchWord);
//        }
//
//        // フィルードフィルタはAND条件
//        AccountExample.Criteria criteria = example.or();
//        for (Column column : input.getColumns()) {
//            if (column.getSearchable() && !StringUtils.isEmpty(column.getSearch().getValue())) {
//                String fSearchWord = '%' + column.getSearch().getValue() + '%';
//
//                switch (StringUtils.lowerCase(column.getData())) {
//                    case "username":
//                        criteria.andUsernameLike(fSearchWord);
//                        break;
//                    case "firstname":
//                        criteria.andFirstNameLike(fSearchWord);
//                        break;
//                    case "lastname":
//                        criteria.andLastNameLike(fSearchWord);
//                        break;
//                    case "email":
//                        criteria.andEmailLike(fSearchWord);
//                        break;
//                    case "url":
//                        criteria.andUrlLike(fSearchWord);
//                        break;
//                    default:
//                        throw new IllegalStateException("Unexpected value: " + StringUtils.lowerCase(column.getData()));
//                }
//            }
//        }
//
//        // 並び順
//        example.setOrderByClause(input.getOrderByClause());
//
//        // 追加項目、HTMLエスケープ
//        List<Account> accountList = accountSharedService.findAllByExample(example, rowBounds);
//        for (Account account : accountList) {
//            AccountListBean accountListBean = beanMapper.map(account, AccountListBean.class);
//            accountListBean.setOperations("<a href=\"http://www.stnet.co.jp\">参照</a>");
//
//
//            // 追加処理
//
//
//            accountListBeanList.add(accountListBean);
//        }
//        output.setData(accountListBeanList);
//
//
//        // 必要な情報をセット
//        output.setDraw(input.getDraw() + 1);
//        output.setRecordsTotal(accountSharedService.countByExample(new AccountExample()));
//        output.setRecordsFiltered(accountSharedService.countByExample(example));
//        output.setError("");
//
//        return output;
//    }

//    @ResponseBody
//    @RequestMapping(value = "/list/json", method = RequestMethod.GET)
//    public DataTablesOutput<AccountListBean> getListJson(@Valid DataTablesInput input) {
//
//        RowBounds rowBounds = new RowBounds(input.getStart(), input.getLength());
//        List<Account> accountList = accountExRepository.selectByExampleWithRowbounds(input, rowBounds);
//
//        // 追加項目、HTMLエスケープ
//        List<AccountListBean> accountListBeanList = new ArrayList<>();
//        for (Account account : accountList) {
//            AccountListBean accountListBean = beanMapper.map(account, AccountListBean.class);
//            accountListBean.setOperations("<a href=\"http://www.stnet.co.jp\">参照</a>");
//
//
//            accountListBean.setDT_RowId(account.getUsername() + "_");
//            accountListBean.setDT_RowClass("abcclass");
//
//            Map<String, String> attr = new HashMap<>();
//            attr.put("width", "100px");
//            accountListBean.setDT_RowAttr(attr);
//
//            accountListBeanList.add(accountListBean);
//        }
//
//        DataTablesOutput<AccountListBean> output = new DataTablesOutput<>();
//        output.setData(accountListBeanList);
//        output.setDraw(input.getDraw());
//        output.setRecordsTotal(accountExRepository.countByExample(null));
//        output.setRecordsFiltered(accountExRepository.countByExample(input));
//
//        return output;
//    }

}
