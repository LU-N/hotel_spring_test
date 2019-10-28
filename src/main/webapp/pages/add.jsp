<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加房间</title>
    <link href="/css/style.css" type="text/css" rel="stylesheet">
</head>
<body>
<div class="container">
    <form action="/room/add" method="post" enctype="multipart/form-data">
        <div class="left">
            <p>房间名称:&nbsp;&nbsp;<input type="text" name="name"></p>
            <p>房间图片:&nbsp;&nbsp;<input type="file" name="file" id="file" onchange="preview()"></p>
        </div>
        <div class="right">
            <p><img alt="请上传" id="image" src="/img/1.jpg"></p>
        </div>
        <div class="clear"></div>
        <div class="bottom">
            <p><input type="submit" value="添加房间"></p>
        </div>
</form>
</div>
<script>
    function preview() {
        var reader = new FileReader();
        reader.onload = function () {
            //处理结果
            document.getElementById("image").src = this.result;
        };

        //获取文件
        var file = document.getElementById("file").files[0];
        //读取文件内容
        reader.readAsDataURL(file);
    }
</script>
</body>
</html>
