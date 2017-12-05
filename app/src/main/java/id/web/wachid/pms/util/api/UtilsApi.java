package id.web.wachid.pms.util.api;

/**
 * Created by wachid.sst
 * website : www.wachid.web.id
 * github : https://github.com/wachid-sst
 */
public class UtilsApi {

    // 10.0.2.2 ini adalah localhost.
    public static final String BASE_URL_API = "http://192.168.1.15/pms-server/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
