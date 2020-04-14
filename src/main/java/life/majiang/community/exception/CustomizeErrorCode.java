package life.majiang.community.exception;

/**
 * 项目名： community
 * 包名:    life.majiang.community.exception
 * 文件名   CustomizeErrorCode
 * 创建者
 * 创建时间: 2020/3/24 12:09 AM
 * 描述  每一次低啊用枚举类的枚举值都想当于调用一次枚举类的构造函数
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"你找的问题不见了，要不要换一个试试?"),
    //没有传递
    TARGET_PARENT_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后尝试"),
    SYSTEM_ERROR(2004,"服务器冒烟了，要不然您稍后再试试, （未知异常)"),
    TYPE_PARAM_WRONG(2005,"评论类型错误，不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在了，要不换个试试"),
    COMMENT_IS_EMPTY(2007,"输入的内容不能为空");

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
