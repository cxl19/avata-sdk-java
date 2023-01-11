package proxy.tx.impl;

import com.alibaba.fastjson.JSONObject;
import config.ConfigCache;
import constant.ErrorMessage;
import exception.SdkException;
import model.ErrorResponse;
import model.tx.QueryQueueResponse;
import model.tx.QueryTxResponse;
import okhttp3.Response;
import proxy.tx.TxProxy;
import util.HttpClient;

public class TxClient implements TxProxy {
    private static final String QUERY_TX = "/v1beta1/tx/";
    private static final String QUERY_QUEUE_INFO = "/v1beta1/tx/queue/info";

    /**
     * Query transaction
     *
     * @param operationId Transaction operationId
     * @return TxRes, Transaction Result
     */
    public QueryTxResponse queryTx(String operationId){
        StringBuffer sb = new StringBuffer();
        sb.append(QUERY_TX);
        sb.append(operationId);
        String result;
        Response res;
        try {
            res = HttpClient.Get(sb.toString(), "");
            result = res.body().string();
        } catch (Exception e) {
            throw new SdkException(ErrorMessage.UNKNOWN_ERROR);
        }
        if (res.code() != 200) {
            throw new SdkException(res.code(), res.message(), null);
        }
        QueryTxResponse txRes = JSONObject.parseObject(result, QueryTxResponse.class);
        txRes.setCode(res.code());
        txRes.setMessage(res.message());
        return txRes;
    }

    /**
     * Query queue info
     *
     * @return TxRes, Transaction Result
     */
    public QueryQueueResponse queryQueueInfo(){
        String result;
        Response res;
        try {
            res = HttpClient.Get(QUERY_QUEUE_INFO, "");
            result = res.body().string();
        } catch (Exception e) {
            throw new SdkException(ErrorMessage.INTERNAL_ERROR);
        }
        if (res.code() != 200) {
            ErrorResponse errorResponse = JSONObject.parseObject(result, ErrorResponse.class);
            throw new SdkException(res.code(), res.message(), errorResponse.getError());
        }
        QueryQueueResponse queryRes = JSONObject.parseObject(result, QueryQueueResponse.class);
        queryRes.setCode(res.code());
        queryRes.setMessage(res.message());
        return queryRes;
    }
}
