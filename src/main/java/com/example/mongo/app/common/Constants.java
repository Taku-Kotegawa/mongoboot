package com.example.mongo.app.common;

public final class Constants {

    public static final class ROLE {
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
    }

    public static final class STATUS {
        public static final Integer DRAFT = 0;
        public static final Integer VALID = 1;
        public static final Integer INVALID = 2;
    }

    public static final class OPERATION {
        public static final String CREATE = "create";
        public static final String UPDATE = "update";
        public static final String DELETE = "delete";
        public static final String INVALID = "invalid";
        public static final String LIST = "list";
        public static final String VIEW = "view";
    }

    public static final class BUTTON {
        public static final String GOTOLIST = "gotoList";
        public static final String GOTOUPDATE = "gotoUpdate";
        public static final String CREATE = "create";
        public static final String SAVE_AS_DRAFT = "saveAsDraft";
        public static final String CANCEL_DARFT = "cancelDraft";
        public static final String SAVE = "save";
        public static final String INVALID = "invalid";
        public static final String DELETE = "delete";
        public static final String VIEW = "view";
    }
    
    
}
