package com.example.demo.domain.meta;

/**
 * @author xhliu
 * @create 2022-04-27-16:34
 **/
public class Response {
    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Response OK = new Builder().withCode("1000").withMessage("成功").build();
    public static Response FAILED = new Builder().withCode("404").withMessage("失败").build();

    public static final class Builder {
        private String code;
        private String message;

        private Builder() {
        }

        public static Builder aResponse() {
            return new Builder();
        }

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Response build() {
            Response response = new Response();
            response.setCode(code);
            response.setMessage(message);
            return response;
        }
    }
}
