<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改房间</title>
    <link href="/css/style.css" type="text/css" rel="stylesheet">
</head>
<body>

<div class="container">
    <form action="/room/update" method="post" enctype="multipart/form-data">
        <div class="left">
            <p>房间编号:&nbsp;&nbsp;<input type="text" name="id" readonly></p>
            <p>房间名称:&nbsp;&nbsp;<input type="text" name="name"></p>
            <p>房间图片:&nbsp;&nbsp;<input type="file" name="file" id="file" onchange="preview()"></p>
            <p>上次图片:&nbsp;&nbsp;<input type="text" name="image"></p>
            <p>上次图片:&nbsp;&nbsp;<img src="/img/1.jpg"></p>
        </div>
        <div class="right">
            <p><img alt="请上传" id="preview" src="/img/2.jpg"></p>
        </div>
        <div class="clear"></div>
        <div class="bottom">
            <p><input type="submit" value="修改房间"></p>
        </div>
    </form>
</div>
<script>
    function preview() {
    }
</script>
</body>
</html>
