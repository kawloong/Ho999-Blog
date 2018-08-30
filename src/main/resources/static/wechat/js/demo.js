
wx.ready(function () {

    // 5 图片接口
    // 5.1 拍照、本地选图
    var images = {
        localId: [],
        serverId: []
    };
    document.querySelector('#chooseImage').onclick = function () {
        wx.chooseImage({
            sourceType: ['album',  'camera' ],
            success: function (res) {
                images.localId = res.localIds;
                var previewImgHtml = ""
                var size = " width=20 height=20 "
                for (var imgid in images.localId) {
                    previewImgHtml += '<img ' + size + ' src="' + images.localId[imgid] + '" / >';
                }

                document.querySelector('#prev_img').innerHTML = previewImgHtml;
                alert('已选择 ' + res.localIds.length + ' 张图片');
            }
        });
    };


    // 5.3 上传图片
    document.querySelector('#uploadImage').onclick = function () {0

        if (images.localId.length == 0) {
            alert('请先使用 chooseImage 接口选择图片');
            return;
        }
        var i = 0, length = images.localId.length;
        images.serverId = [];
        function upload() {
            wx.uploadImage({
                isShowProgressTips: 1,
                localId: images.localId[i],
                success: function (res) {
                    i++;
                    alert('已上传：' + i + '/' + res.serverId);
                    images.serverId.push(res.serverId);

                    if (i < length) {
                        upload();
                    } else { // 所有图上传到wechat完成后，通知webapp去下载进自己server
                        okCallBack(images.serverId.toString());
                    }
                },
                fail: function (res) {
                    alert(JSON.stringify(res));
                }
            });
        }

        function okCallBack(serverIds){
            var request = new XMLHttpRequest();
            request.onreadystatechange = function () { // 状态发生变化时，函数被回调
                if (request.readyState === 4) { // 成功完成
                    // 判断响应结果:
                    if (request.status === 200) {
                        // 成功，通过responseText拿到响应的文本:
                        return alert(request.responseText);
                    } else {
                        // 失败，根据响应码判断失败原因:
                        return alert(request.status);
                    }
                } else {
                    // HTTP请求还在继续...
                }
            }

            request.open("POST", window.location.href, true);
            request.send(serverIds);
            console.log("ajax send "+serverIds);
        }

        upload();
    };

    document.querySelector('#closeWindow').onclick = function () {
        wx.closeWindow();
    };

});

wx.error(function (res) {
    alert(res.errMsg);
});

