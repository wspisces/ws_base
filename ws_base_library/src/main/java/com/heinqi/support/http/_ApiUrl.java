package com.heinqi.support.http;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface _ApiUrl {
    //RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
    //JsonUtil.toJson(param));
    //登陆
    @POST("equipment/v1/equipment/login")
    Observable<ResultTO> login(@QueryMap Map<String, Object> options);

    //初始化-保存设备
    @POST("equipment/v1/c/equipment/save")
    Observable<ResultTO> save(@Body RequestBody map);

    //商家信息
    @POST("equipment/v1/c/equipment/merchant")
    Observable<ResultTO> title(@QueryMap Map<String, Object> options);

    //设备报警 equipmentId
    @POST("equipment/v1/c/batch/equipment/warning")
    Observable<ResultTO> machineAlert(@QueryMap Map<String, Object> options);


    //设备状态
    @POST("equipment/v1/c/equipment/status")
    Observable<ResultTO> status(@QueryMap Map<String, Object> options);

    //配送员-上药商品列表
    @POST("equipment/v1/c/equipment/type/slot/product/paging")
    Observable<ResultTO> productList(@Body RequestBody map);

    //省市区
    @POST("equipment/v1/area/list")
    Observable<ResultTO> areaList();

    //设备类型
    @POST("equipment/v1/dict/type")
    Observable<ResultTO> equipmentType(@QueryMap Map<String, Object> options);

    //配送员-清零
    @POST("equipment/v1/c/equipment/type/slot/delete")
    Observable<ResultTO> slotDelete(@QueryMap Map<String, Object> options);

    //配送员-上药商品扫码
    @POST("equipment/v1/c/equipment/type/slot/product/item")
    Observable<ResultTO> slotProductScan(@QueryMap Map<String, Object> options);

    //配送员-设备药槽商品库存详情
//    @POST("equipment/v1/c/equipment/type/slot/product/stock/item")
//    Observable<ResultTO> slotProductDetail(@QueryMap Map<String, Object> options);

    //配送员-设备药槽商品库存录入
    @POST("equipment/v1/c/equipment/type/slot/product/stock/save")
    Observable<ResultTO> productStockSave(@Body RequestBody map);

    //配送员-上药商品清空
    @POST("equipment/v1/c/equipment/type/slot/product/delete")
    Observable<ResultTO> productDelete(@QueryMap Map<String, Object> options);

    //配送员-上药商品清空扫码
    @POST("equipment/v1/c/equipment/type/slot/product/info")
    Observable<ResultTO> productInfo(@QueryMap Map<String, Object> options);

    //配送员-设备下架商品列表
    @POST("equipment/v1/c/equipment/type/slot/product/undercarriage/paging")
    Observable<ResultTO> undercarriage(@Body RequestBody map);

    //配送员-下架
    @POST("equipment/v1/c/equipment/type/slot/product/undercarriage/delete")
    Observable<ResultTO> undercarriageDelete(@QueryMap Map<String, Object> options);

    //配送员-同批次商品录入信息
    @POST("equipment/v1/c/equipment/type/slot/product/info/item")
    Observable<ResultTO> productInfoItem(@QueryMap Map<String, Object> options);

    //配送员-设备商品模板列表
    @POST("equipment/v1/c/equipment/type/slot/product/model/paging")
    Observable<ResultTO> modelList(@Body RequestBody map);

    //配送员-设备商品模板制作详情
    @POST("equipment/v1/c/equipment/type/slot/product/model/item")
    Observable<ResultTO> modelDetail(@QueryMap Map<String, Object> options);

    //配送员-设备商品模板制作保存
    @POST("equipment/v1/c/equipment/type/slot/product/model/save")
    Observable<ResultTO> modelSave(@Body RequestBody map);

    //上传图片
    @Multipart
    @POST("equipment/v1/upload/image")
    Observable<ResultTO> uploadFile(@PartMap Map<String, RequestBody> headPortrait);

    //备货完成
    @POST("equipment/v1/c/equipment/type/slot/product/stock/finish")
    Observable<ResultTO> stockFinish(@QueryMap Map<String, Object> options);

    //备货清单
    @POST("equipment/v1/c/equipment/type/slot/product/stock/list")
    Observable<ResultTO> stockList(@QueryMap Map<String, Object> options);

//    //备货清单
//    @POST("equipment/v1/c/equipment/type/slot/product/stock/list")
//    Observable<ResultTO> stockList(@Body RequestBody map);

    //whoami
    @POST("equipment/v1/whoami")
    Observable<ResultTO> whoami();

    //强制更新 废弃 ws
    @Deprecated
    @GET("equipment/v1/dict/type")
    Observable<ResultTO> update(@QueryMap Map<String, Object> options);

    //版本检查
    @POST("equipment/v1/dict/version")
    Observable<ResultTO> version(@QueryMap Map<String, Object> options);

    //版本检查
    @POST("equipment/v1/dict/version")
    Observable<ResultTO> version(@Body RequestBody map);

    //获取温湿度预警阈值
    @POST("equipment/v1/c/batch/temperature/warning/threshold")
    Observable<ResultTO> thresholdForTempAndHumi(@QueryMap Map<String, Object> options);

    //温湿度报警
    @POST("equipment/v1/c/batch/temperature/warning")
    Observable<ResultTO> alarmForTempAndHumi(@QueryMap Map<String, Object> options);

    @POST("equipment/v1/c/batch/temperature/warning")
    Observable<ResultTO> alarmForTempAndHumi(@Body RequestBody map);

    //上传温湿度 {“equipmentId”-设备主键编号；“temperature”-温度；“humidity”-湿度}
    @POST("equipment/v1/c/batch/temperature/record")
    Observable<ResultTO> updateTempAndHumi(@Body RequestBody map);

    //图片接口
    @POST("equipment/v1/c/equipment/product/templates")
    Observable<ResultTO> dragImages(@QueryMap Map<String, Object> options);


    //判断商品和槽位是否可以绑定
    @POST("equipment/v1/c/equipment/type/slot/check")
    Observable<ResultTO> checkSlot(@Body RequestBody map);


    //url 下载链接
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
