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

    QUESTION_NOT_FOUND("你找的问题不见了，要不要换一个试试?");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
