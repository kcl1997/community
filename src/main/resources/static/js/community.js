// 展开二级评论
function collapseComments(e){


    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);

    //折叠二级评论
    if(comments.hasClass("in")){
        comments.removeClass("in");
        e.classList.remove("active");
        return;
    }
    //展开二级评论
    var subCommentContainer = $("#comment-"+id);
    comments.addClass("in");
    e.classList.add("active");

    //避免重复append
    if(subCommentContainer.children().length == 1){
        //子元素长度 == 1,加载数据
        $.getJSON("/comment/"+id,function (data) {
            // console.log(data);
            $.each(data.data.reverse(),function(index,comment){
                 var mediaLeftElement =$("<div/>",{
                    "class":"media-left"
                }).append($("<img/>",{
                    "class":"media-object img-rounded img_avatar",
                    "src":comment.user.avatarUrl
                }));

                var mediaBodyElement =$("<div/>",{
                    "class":"media-body"
                }).append($("<h5/>",{
                    "class":"media-heading",
                    "style":"cursor:pointer; color: #55a6aa;",
                    "html":comment.user.name
                })).append($("<div/>",{
                    "html":comment.content
                })).append($("<div/>",{
                    "class":"menu"
                })).append($("<span/>",{
                    "class":"pull-right text-desc",
                    "html": moment(comment.gmtCreate).format('YYYY-MM-DD h:mm:ss a')
                }));


                var mediaElement = $("<div/>",{
                    "class":"media"
                }).append(mediaLeftElement).append(mediaBodyElement);

                var commentElement = $("<div/>",{
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                }).append(mediaElement);
                subCommentContainer.prepend(commentElement);
            });
        });
    }

}


//发布新评论
function post(){
    var questionId  = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}
//发布二级评论
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);

}

function comment2target(targetId,type,content){
    if(!content){
        alert("不能回复空内容（前段校验)");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        dataType: 'json',
        success:function (response) {
            if(response.code == 200){
                window.location.reload();
                //$("#comment_section").hide();
            }else{
                //未登录
                if(response.code == 2003){
                    var isAccepted = confirm(response.message);
                    //点击确认登录,跳转页面
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.18039b8df20e807b&redirect_uri=http://localhost:9002/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }else{
                    alert(response.message);
                }
            }
            console.log(response)
        }
    });


}




