$(document).ready(function() {
    // 删除操作
    $('.table .d-delete').each(function() {
        $(this).click(function() {
            var dataConfirm = $(this).attr('data-confirm');
            if (confirm(dataConfirm)) {
                var dataMethod = $(this).attr('data-method');
                var url = $(this).attr('data-href');

                if (dataMethod == "post") {
                    var token = $("meta[name='_csrf']").attr("content");

                    var form = document.createElement('form');
                    form.method = 'post';
                    form.action = url;
                    form.style.display = 'none';
                    // csrf
                    var inputToken = document.createElement('input');
                    inputToken.name = "_csrf";
                    inputToken.value = token;
                    form.appendChild(inputToken);
                    document.body.appendChild(form);
                    form.submit();
                } else {
                    window.location.href = url;
                }
            }
        })
    })


    // 上传图片
    $(".media-picker .media-picker-btn").each(function(){
        var pickerBtn = $(this);
        var ossDir = pickerBtn.attr("data-oss-dir");
        var valueField = pickerBtn.find("input[type=hidden]");
        var fileName = valueField.val();
        var ossParam = '';
        var imageShow = pickerBtn.next();

        var uploader = new plupload.Uploader({
            runtimes : 'html5,flash,silverlight,html4',
            browse_button : 'media-picker-btn',
            multi_selection: false,
            flash_swf_url : '/plugins/plupload/Moxie.swf',
            silverlight_xap_url : '/plugins/pluploa/Moxie.xap',

            filters: {
                mime_types : [ //只允许上传图片
                    { title : "Image files", extensions : "jpg,gif,png,bmp" },
                ],
                max_file_size : '3mb', //最大只能上传3mb的文件
                prevent_duplicates : true //不允许选取重复文件
            },

            init: {
                PostInit: function() {
                    $.ajax({
                        type: "POST",
                        url: "/backend/upload",
                        data: "dir=" + ossDir,
                        success: function(msg){
                            if (msg.code == 0) {
                                ossParam = msg.data
                            } else {
                                alert(msg.message);
                                return;
                            }
                        }
                    });
                },

                FilesAdded: function(up, files) {
                    up.start();
                },

                BeforeUpload: function(up, file) {
                    var filename = file.name;
                    var pos = filename.lastIndexOf('.');
                    if (pos == -1) {
                        alert("文件后缀不对，只支持jpg,gif,png,bmp");
                        return false;
                    }
                    var suffix = filename.substring(pos);
                    if (ossParam != '') {
                        var ossKey = ossDir + '/' + ossParam.fileName + suffix;
                        fileName = ossParam.host + '/' + ossKey;
                        up.setOption({
                            'url': ossParam.host,
                            'multipart_params': {
                                'key' : ossKey,
                                'policy': ossParam.policy,
                                'OSSAccessKeyId': ossParam.accessid,
                                'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
                                'callback' : ossParam.callback,
                                'signature': ossParam.signature,
                            }
                        });
                    } else {
                        alert("参数错误");
                        return;
                    }
                },

                UploadProgress: function(up, file) {
                    var progressBao = "<div class='progress'><div class='progress-bar' role='progressbar' style='width: "+file.percent+"%' aria-valuenow='"+file.percent+"' aria-valuemin='0' aria-valuemax='100'></div></div>";
                    imageShow.html(progressBao);
                },

                FileUploaded: function(up, file, info) {
                    if (info.status == 200)
                    {
                        valueField.val(fileName);
                        var imgHtml = "<img src='"+ fileName +"' /><button type='button' class='btn btn-danger'>删除</button>"
                        imageShow.html(imgHtml);
                    } else {
                        alert("上传失败");
                        return;
                    }
                },

                Error: function(up, err) {
                    if (err.code == -600) {
                        alert("文件太大了");
                    }
                    else if (err.code == -601) {
                        alert("文件后缀不对，只支持jpg,gif,png,bmp");
                    }
                    else if (err.code == -602) {
                        alert("这个文件已经上传过一遍了");
                    }
                    else
                    {
                        console.log(err.response)
                        alert("上传文件错误");
                    }
                }
            }
        });
        uploader.init();
    })
    $(".media-picker").on("click", ".img-show .btn-danger", function() {
        $(this).parent().prev().find("input[type=hidden]").val("");
        $(this).parent().html("");
    })

    if($("#editer_content") != undefined) {
        tinymce.init({
            selector: '#editer_content',
            height: 500,
            importcss_append: true,
            plugins: 'fullscreen image link media table lists imagetools code codesample',
            toolbar1: 'bold italic underline strikethrough | ' +
                'alignleft aligncenter alignright alignjustify | ' +
                'undo redo | link image codesample',
            toolbar2:  'styleselect formatselect fontselect fontsizeselect  forecolor backcolor | ' +
                'cut copy paste | bullist numlist table  outdent indent blockquote | ' +
                'media code fullscreen',
            menubar: false,
            images_upload_handler: function (blobInfo, succFun, failFun) {
                var xhr, formData;
                xhr = new XMLHttpRequest();
                xhr.withCredentials = false;
                xhr.open('POST', '/backend/detail-upload');
                xhr.onload = function() {
                    var json;
                    if (xhr.status != 200) {
                        alert("网络错误！");
                        return;
                    }
                    json = JSON.parse(xhr.responseText);
                    console.log(json)

                    if (!json || json.code != 0) {
                        alert(json.data);
                        return;
                    }
                    succFun(json.data);
                };
                formData = new FormData();
                formData.append('file', blobInfo.blob(), blobInfo.filename());
                xhr.send(formData);
            }
        });
    }
})