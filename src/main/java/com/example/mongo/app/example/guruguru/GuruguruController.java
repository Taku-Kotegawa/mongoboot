package com.example.mongo.app.example.guruguru;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("guruguru")
public class GuruguruController {

    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @ModelAttribute
    public GuruguruForm setUp() {
        GuruguruForm guruguruForm = new GuruguruForm();
        return guruguruForm;
    }

    /**
     * RadioButtonの選択項目の定義
     *
     * @return
     */
    private List<ListItem> getRadioOptions() {
        List<ListItem> items = new ArrayList<>();
        items.add(new ListItem("2", "はい"));
        items.add(new ListItem("1", "いいえ"));
        return items;
    }

    private List<ListItem> getSelectOptionsblank() {
        List<ListItem> items = getSelectOptions();
        items.add(0, new ListItem("", "　"));
        return items;
    }

    private List<ListItem> getSelectOptions() {
        List<ListItem> items = new ArrayList<>();
        items.add(new ListItem("op1", "オプション１"));
        items.add(new ListItem("op2", "オプション２"));
        items.add(new ListItem("op3", "オプション３"));
        items.add(new ListItem("op4", "オプション４"));
        return items;
    }

    private void setOptionsToModel(Model model) {
        model.addAttribute("radio002Options", getRadioOptions());
        model.addAttribute("selectoptionsblank", getSelectOptionsblank());
        model.addAttribute("selectoptions", getSelectOptions());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String guruguruGet(GuruguruForm form, BindingResult bindingResult, Model model) {
        setOptionsToModel(model);
        return "example/guruguru";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String guruguruPost(@Validated GuruguruForm form, BindingResult bindingResult, Model model) {
        setOptionsToModel(model);

        Map attributes = model.asMap();
        List<ListItem> selectoptionsblank = (List) attributes.get("selectoptionsblank");
        List<ListItem> selectoptions = (List) attributes.get("selectoptions");
        addUniqueLabel(selectoptionsblank, form.getCombobox001());
        addUniqueLabel(selectoptions, form.getCombobox002());

        return "example/guruguru";
    }

    /**
     * @param list
     * @param addLabels
     */
    private void addUniqueLabel(List<ListItem> list, List<String> addLabels) {
        if (addLabels != null) {
            for (String label : addLabels) {
                if (list.stream().noneMatch(e -> label.equals(e.getValue()))) {
                    list.add(new ListItem(label, label));
                }
            }
        }
    }

    /**
     * @param list
     * @param addLabel
     */
    private void addUniqueLabel(List<ListItem> list, String addLabel) {
        if (addLabel != null) {
            ArrayList<String> addLabels = new ArrayList<>();
            addLabels.add(addLabel);
            addUniqueLabel(list, addLabels);
        }
    }
}
