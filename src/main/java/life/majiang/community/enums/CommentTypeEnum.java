package life.majiang.community.enums;

/**
 * 项目名： community
 * 包名:    life.majiang.community.enums
 * 文件名   CommentTypeEnum
 * 创建者
 * 创建时间: 2020/3/30 9:42 PM
 * 描述  ${TODO}
 */
public enum  CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    public static boolean isExist(Integer type) {
        for(CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()){
            if(commentTypeEnum.getType() == type) return true;
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    private Integer type;



}

