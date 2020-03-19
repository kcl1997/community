package life.majiang.community.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 项目名： community
 * 包名:    life.majiang.community.dto
 * 文件名   PaginationDTO
 * 创建者
 * 创建时间: 2020/3/19 8:15 PM
 * 描述  ${TODO}
 */

@Data
public class PaginationDTO {

    //好办
    private List<QuestionDTO> questionDTOs;
    //简单计算
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;

    //当前页，直接得到
    private Integer page;
    // 12345
    private List<Integer> pages = new ArrayList<>();

    //总页数
    private Integer totalPage;

    public void setPaginationDTO(Integer totalPage, Integer page, Integer size) {
        this.totalPage = totalPage;
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;
        this.page = page;

        //设置pages
        //当前页前面有3个，后面有3页，如果页面符合要求的话
        pages.add(page);
        for(int i = 1; i <= 3; i++){
            if(page - i > 0) pages.add(0,page-i);
            if(page + i <= totalPage) pages.add(page+i);
        }


        //当page == 1时没有上一页
        if(page == 1) showPrevious = false;
        else showPrevious = true;

        //当是最后一页时，没有下一页
        if(page == totalPage) showNext = false;
        else showNext = true;


        //点击第5页，才会出现返回首页
        if(pages.contains(1)) showFirstPage = false;
        showFirstPage = true;

        //是否展示最后一页连接
        if(pages.contains(totalPage)) showEndPage = false;
        else showEndPage = true;

    }



}

