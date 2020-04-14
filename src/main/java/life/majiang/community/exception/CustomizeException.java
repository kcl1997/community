package life.majiang.community.exception;

/**
 * 项目名： community
 * 包名:    life.majiang.community.exception
 * 文件名   CustomizeException
 * 创建者
 * 创建时间: 2020/3/23 11:59 PM
 * 描述  ${TODO}
 */
public class CustomizeException extends RuntimeException {

    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }



    /**
     * RuntimeException 复写的方法
     * @return
     */
    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}

