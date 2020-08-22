package com.example.mongo.app.common;

import java.beans.Introspector;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class StateMap {

    private final Map<String, Boolean> authMap = new HashMap<>();

    private final String DISABLED = "disabled";
    private final String READONLY = "readonly";
    private final String HIDDEN = "hidden";
    private final String INPUT = "input";
    private final String VIEW = "view";
    private final String[] attributes = {DISABLED, READONLY, HIDDEN, VIEW, INPUT};

    public StateMap setDisabledTrue(String fieldName) {
        return setAttribute(fieldName, DISABLED, true);
    }

    public StateMap setDisabledFalse(String fieldName) {
        return setAttribute(fieldName, DISABLED, false);
    }

    public StateMap setReadOnlyTrue(String fieldName) {
        return setAttribute(fieldName, READONLY, true);
    }

    public StateMap setReadOnlyFalse(String fieldName) {
        return setAttribute(fieldName, READONLY, false);
    }

    public StateMap setHiddenTrue(String fieldName) {
        return setAttribute(fieldName, HIDDEN, true);
    }

    public StateMap setHiddenFalse(String fieldName) {
        return setAttribute(fieldName, HIDDEN, false);
    }

    public StateMap setViewTrue(String fieldName) {
        return setAttribute(fieldName, VIEW, true);
    }

    public StateMap setViewFalse(String fieldName) {
        return setAttribute(fieldName, VIEW, false);
    }

    public StateMap setDisabledTrueAll() {
        return setAttributeAll(DISABLED, true);
    }

    public StateMap setDisabledFalseAll() {
        return setAttributeAll(DISABLED, false);
    }

    public StateMap setReadOnlyTrueAll() {
        return setAttributeAll(READONLY, true);
    }

    public StateMap setReadOnlyFalseAll() {
        return setAttributeAll(READONLY, false);
    }

    public StateMap setHiddenTrueAll() {
        return setAttributeAll(HIDDEN, true);
    }

    public StateMap setHiddenFalseAll() {
        return setAttributeAll(HIDDEN, false);
    }

    public StateMap setInputTrueAll() {

        return setAttributeAll(INPUT, true);
    }

    public StateMap setInputFalseAll() {
        return setAttributeAll(INPUT, false);
    }

    public StateMap setViewTrueAll() {
        return setAttributeAll(VIEW, true);
    }

    public StateMap setViewFalseAll() {
        return setAttributeAll(VIEW, false);
    }

    public StateMap addKey(String fieldName) {
        for (String attribute : attributes) {
            authMap.put(fieldName + "__" + attribute, new Boolean(false));
        }
        return this;
    }

    private StateMap setAttributeAll(String attribute, Boolean status) {
        for (Map.Entry<String, Boolean> entry : authMap.entrySet()) {
            if (entry.getKey().endsWith(attribute)) {
                entry.setValue(status);
            }
        }
        return this;
    }

    private StateMap setAttribute(String fieldName, String attribute, Boolean status) {
        String key = fieldName + "__" + attribute;
        if (authMap.get(key) != null) {
            authMap.put(key, status);
        } else {
            throw new IllegalArgumentException(key + " not found");
        }
        return this;
    }

    public StateMap(Class clazz, List<String> includeKeys, List<String> excludeKeys) {

        List<String> filedNames = getFileds(clazz);

        Iterator<String> it = filedNames.listIterator();
        while (it.hasNext()) {
            if (excludeKeys.contains(it.next())) {
                it.remove();
            }
        }
        filedNames.addAll(includeKeys);
        init(filedNames);
    }

    private StateMap init(List<String> fieldNames) {
        for (String fieldName : fieldNames) {
            for (String attribute : attributes) {
                authMap.put(fieldName + "__" + attribute, new Boolean(false));
            }
        }
        return this;
    }

    public List<String> getFileds(Class clazz) {
        return getFileds(clazz, "");
    }

    public List<String> getFileds(Class clazz, String parentClassName) {
        List<String> fieldNames = new ArrayList<>();

        if (clazz != null) {
            String prefix = "";
            if (parentClassName != null && !parentClassName.isEmpty()) {
                prefix = parentClassName + "-";
            }

            Method[] methods = clazz.getMethods();
            for (Method m : methods) {
                if (m.getName().startsWith("set")) {
                    Class fieldClass = m.getParameterTypes()[0];
                    String fieldName = Introspector.decapitalize(m.getName().substring(3));

                    if ("java.lang.String".equals(fieldClass.getName())
                            || "java.util.List".equals(fieldClass.getName())
                            || "java.util.Map".equals(fieldClass.getName())) {
                        // 何もしない

                    } else {
                        fieldNames.addAll(getFileds(fieldClass, fieldName));
                    }

                    fieldNames.add(prefix + fieldName);
                }
            }
        }

        return fieldNames;
    }

    public String getSignature(Method m) {
        String sig;
        try {
            Field gSig = Method.class.getDeclaredField("signature");
            gSig.setAccessible(true);
            sig = (String) gSig.get(m);
            if (sig != null) return sig;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder("(");
        for (Class<?> c : m.getParameterTypes())
            sb.append((sig = Array.newInstance(c, 0).toString()), 1, sig.indexOf('@'));
        return sb.append(')')
                .append(
                        m.getReturnType() == void.class ? "V" :
                                (sig = Array.newInstance(m.getReturnType(), 0).toString()).substring(1, sig.indexOf('@'))
                )
                .toString();
    }

    public Map<String, Boolean> asMap() {
        return authMap;
    }
}
