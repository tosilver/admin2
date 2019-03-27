//初始化下拉框的值 默认存储到data属性
function initSelectValue(flag) {
    $("select").each(function () {
        var data = $(this).attr("data");
        if (data != "") {
            data = data.split(",");
            $(this).val(data);
        }
    });
    if (flag) {
        $('select').selected();
    }
}

/**
 * browseButton：    上传选择的点选按钮id，默认为pickfiles，**必需**
 * uploaded：        上传成功时，回调方法
 * options：        参数选项
 */
function uploader(options, uploaded) {
    if (!options.domain || !options.uptoken) {
        throw "domain和uptoken是必须的参数,在对应控制器的方法前加注解@QiniuApiConfig!";
    }

    var settings = {
        runtimes: 'html5,flash,html4',      // 上传模式,依次退化
        get_new_uptoken: false,             // 设置上传文件的时候是否每次都重新获取新的 uptoken
        // downtoken_url: '/downtoken',   // Ajax请求downToken的Url，私有空间时使用,JS-SDK 将向该地址POST文件的key和domain,服务端返回的JSON必须包含`url`字段，`url`值为该文件的下载地址
        unique_names: true,              // 默认 false，key 为文件名。若开启该选项，JS-SDK 会为每个文件自动生成key（文件名）
        save_key: true,                  // 默认 false。若在服务端生成 uptoken 的上传策略中指定了 `sava_key`，则开启，SDK在前端将不对key进行任何处理
        max_file_size: '100mb',             // 最大文件体积限制
        flash_swf_url: 'http://cdn.staticfile.org/Plupload/2.1.1/Moxie.swf',  //引入 flash,相对路径 ../assets/plupload/Moxie.swf
        max_retries: 3,                     // 上传失败最大重试次数
        chunk_size: '4mb',                  // 分块上传时，每块的体积
        auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
        multi_selection: false,
        init: {
            'FilesAdded': function (up, files) {
                plupload.each(files, function (file) {
                    // 文件添加进队列后,处理相关的事情
                });
            },
            'BeforeUpload': function (up, file) {
                // 每个文件上传前,处理相关的事情
            },
            'UploadProgress': function (up, file) {
                // 每个文件上传时,处理相关的事情
            },
            'Error': function (up, err, errTip) {
                //上传出错时,处理相关的事情
            },
            'UploadComplete': function () {
                //队列文件处理完毕后,处理相关的事情
            },
            'Key': function (up, file) {
                // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
                // 该配置必须要在 unique_names: false , save_key: false 时才生效

                var key = "";
                // do something with key here
                return key
            }
        }
    };
    settings.init.FileUploaded = uploaded;
    settings.browse_button = options.browse_button || 'pickfiles';
    for (var key in options) {
        settings[key] = options[key];
    }
    var uploader = Qiniu.uploader(settings);
    // console.log(uploader);
}