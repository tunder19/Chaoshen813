package com.example.administrator.chaoshen.info;

public class BaseInfo {
    private Head head;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    /*private String returnCode;
    private String error;



    public String getCode() {
        return returnCode;
    }

    public void setCode(String code) {
        this.returnCode = code;
    }

    public String getErrorMsg() {
        return error;
    }

    public void setErrorMsg(String errorMsg) {
        this.error = errorMsg;
    }*/

    /**
     * returnCode : 1
     * error : 成功
     */


    public class Head {

        private String code ;
        private String msg;

        public String getCode() {
            return code ;
        }

        public void setCode(String returnCode) {
            this.code  = returnCode;
        }

        public String getErrorMsg() {
            return msg;
        }

        public void setErrorMsg(String error) {
            this.msg = error;
        }
    }


}
