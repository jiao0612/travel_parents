package com.atguigu.qinuiserver;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

public class QiNuiUtils {

    //构造一个带指定 Region 对象的配置类
    private static Configuration cfg = new Configuration(Zone.zone1());//华北
    //...生成上传凭证，然后准备上传
    private static String accessKey = "Z4iiNEKeBY8x7_kJqnzExn-UHcVAfkizWBdb92cl";
    private static String secretKey = "CPe__6a7LqrIByNoR2RL3O4JMUvkgmaMtotdlvVO";
    private static String bucket = "warehouse-jzm";
    private static Auth auth = Auth.create(accessKey, secretKey);

    public static void uploadToQiNui(String filePath,String fileName){
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(filePath, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (
                QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    public static void deleteFileFromQiNui(String fileName){
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, fileName);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
