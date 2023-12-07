package com.dh.DpsdkCore;

public class Ptz_Sit_Info_t {
    public byte[] szCameraId = new byte[dpsdk_constant_value.DPSDK_CORE_CHL_ID_LEN];        // 通道ID
    public int pointX;                                                // 坐标值X	快速模式下表示水平坐标[-8192,8192]  查询三维信息返回值中表示水平角度(0,3600)，精准模式下可使用该水平角度值
    public int pointY;                                                // 坐标值Y	快速模式下表示垂直坐标[-8192,8192]  查询三维信息返回值中表示垂直角度(0,900)，精准模式下可使用该垂直角度值
    public int pointZ;                                                // 坐标值Z	快速模式下表示变倍数[-4,4]			查询三维信息返回值中表示变倍档位并非实际变倍数(1,128)，精准模式下可使用该变倍档位值
    public int type;                                                // 定位模式(查询三维信息时该字段无效，三维定位时该字段有效) 1 快速定位，2 精准定位
};
