package fycsb.gky.tb_autosign.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

import fycsb.gky.tb_autosign.api.TieBaApi;
import fycsb.gky.tb_autosign.encrypt.BASE64Util;
import fycsb.gky.tb_autosign.encrypt.MD5Util;

/**
 * Created by codefu on 2014/12/27.
 */
public class PostUrlUtil {
    public static Map<String, String> getParamsHashMap(String username, String password, long time, String sign ,String...vcodes) throws IOException {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put(TieBaApi.CLIENT_ID_KEY, TieBaApi.CLIENT_ID_VALUE);
        map.put(TieBaApi.CLIENT_TYPE_KEY, TieBaApi.CLIENT_TYPE_VALUE);
        map.put(TieBaApi.CLIENT_VERSION_KEY, TieBaApi.CLIENT_VERSION_VALUE);
        map.put(TieBaApi.PHONE_IMEI_KEY, TieBaApi.PHONE_IMEI_VALUE);
        map.put(TieBaApi.CHANNEL_ID_KEY, TieBaApi.CHANNEL_ID_VALUE);
        map.put(TieBaApi.CHANNEL_UID_KEY, TieBaApi.CHANNEL_UID_VALUE);
        map.put(TieBaApi.CUID_KEY, TieBaApi.CUID_VALUE);
        map.put(TieBaApi.FROM_KEY, TieBaApi.FROM_VALUE);
        map.put(TieBaApi.ISPHONE_KEY, TieBaApi.ISPHONE_VALUE);
        map.put(TieBaApi.MODEL_KEY, TieBaApi.MODEL_VALUE);
        map.put(TieBaApi.NET_TYPE_KEY, TieBaApi.NET_TYPE_VALUE);
        map.put(TieBaApi.PASSWD_KEY, URLEncoder.encode(BASE64Util.enBASE64(password.getBytes()).trim(), "UTF-8"));
        map.put(TieBaApi.STERRORNUMS_KEY, TieBaApi.STERRORNUMS_VALUE);
        map.put(TieBaApi.STMETHOD_KEY, TieBaApi.STMETHOD_VALUE);
        map.put(TieBaApi.STMODE_KEY, TieBaApi.STMODE_VALUE);
        map.put(TieBaApi.STSIZE_KEY, TieBaApi.STSIZE_VALUE);
        map.put(TieBaApi.STTIME_KEY, TieBaApi.STTIME_VALUE);
        map.put(TieBaApi.STTIMESNUM_KEY, TieBaApi.STTIMESNUM_VALUE);
        map.put(TieBaApi.TIMESTAMP_KEY, Long.toString(time));
        map.put(TieBaApi.UN_KEY,username);
        if (vcodes[0]!=null && vcodes[1]!=null) {
            System.out.println(">>>>>>>>>>>>>>vcodes[0]" + vcodes[0]);
            System.out.println(">>>>>>>>>>>>>>vcodes[1]" + vcodes[1]);
            map.put(TieBaApi.VCODE,vcodes[0]);
            map.put(TieBaApi.VCODE_MD5,vcodes[1]);
        }
        map.put(TieBaApi.SIGN_KEY, sign);
        return map;
    }

    public static String getPostParams(String username, String password, long time,String...vcodes) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(TieBaApi.CLIENT_ID);
        sb.append(TieBaApi.CLIENT_TYPE);
        sb.append(TieBaApi.CLIENT_VERSION);
        sb.append(TieBaApi.PHONE_IMEI);
        sb.append(TieBaApi.CHANNEL_ID);
        sb.append(TieBaApi.CHANNEL_UID);
        sb.append(TieBaApi.CUID);
        sb.append(TieBaApi.FROM);
        sb.append(TieBaApi.ISPHONE);
        sb.append(TieBaApi.MODEL);
        sb.append(TieBaApi.NET_TYPE);
        sb.append(TieBaApi.PASSWD).append(BASE64Util.enBASE64(password.getBytes("UTF-8")).trim());
        sb.append(TieBaApi.STERRORNUMS);
        sb.append(TieBaApi.STMETHOD);
        sb.append(TieBaApi.STMODE);
        sb.append(TieBaApi.STSIZE);
        sb.append(TieBaApi.STTIME);
        sb.append(TieBaApi.STTIMESNUM);
        sb.append(TieBaApi.TIMESTAMP).append(time);
        sb.append(TieBaApi.UN).append(username);
        if (vcodes.length== 2 && vcodes[0]!=null && vcodes[1]!=null) {
            sb.append(TieBaApi.VCODE).append("=").append(vcodes[0]);
            sb.append(TieBaApi.VCODE_MD5).append("=").append(vcodes[1]);
        }
        sb.append(TieBaApi.FLAG);
        return sb.toString();
    }

    public static String getSign(String username, String password, long time)  {
        String encodeStr = null;
        String sign = null;
        try {
            encodeStr = URLDecoder.decode(PostUrlUtil.getPostParams(username, password, time), "UTF-8");
            sign = MD5Util.getMD5(encodeStr).toUpperCase();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return sign;
    }

    public static String getVcodeSign(String username, String password, long time,String...vcodes) {
        String encodeStr = null;
        try {
            encodeStr = URLDecoder.decode(PostUrlUtil.getPostParams(username, password, time, vcodes), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sign = null;
        try {
            sign = MD5Util.getMD5(encodeStr).toUpperCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sign;
    }
//    public static String getSign(String params) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        String sign = MD5Util.getMD5(params).toUpperCase();
//        return sign;
//    }


}