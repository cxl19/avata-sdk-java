package ai.bianjie.avatasdk.model.tx;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上链交易结果查询返回值
 *
 * 交易状态说明：
 * status 为 3（未处理），上链请求还在等待处理，请稍等；
 * status 为 0（处理中），上链请求正在处理，请等待处理完成；
 * status 为 1（成功），交易已上链并执行成功；
 * status 为 2（失败），说明该交易执行失败。请在业务侧做容错处理。可以参考接口返回的 message（交易失败的错误描述信息） 对 NFT / MT / 业务接口的请求参数做适当调整后，使用「新的 Operation ID 」重新发起 NFT / MT / 业务接口请求。
 */
@NoArgsConstructor
@Data
public class QueryTxRes {

    @JSONField(name = "data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JSONField(name = "module")
        private Integer module;// 交易模块；Enum: 1:nft; 2:ns; 3:record; 4 合约调用；

        @JSONField(name = "operation")
        private Integer operation;// 用户操作类型；Enum: 用户操作类型。 module = 1 时， 1：issue_class； 2：transfer_class； 3：mint_nft； 4：edit_nft； 5：transfer_nft； 6：burn_nft

        @JSONField(name = "tx_hash")
        private String txHash;// 交易哈希

        @JSONField(name = "status")
        private Integer status;// 交易状态,Enum: 0:处理中; 1:成功; 2:失败; 3:未处理;

        @JSONField(name = "message")
        private String message;// 交易失败的错误描述信息

        @JSONField(name = "block_height")
        private Integer blockHeight;// 交易上链的区块高度

        @JSONField(name = "timestamp")
        private String timestamp;// 交易上链时间（UTC 时间）

        @JSONField(name = "nft")
        private Nft nft;// 具体参考接口文档

        @JSONField(name = "ns")
        private Ns ns;// 具体参考接口文档

        @JSONField(name = "record")
        private Record record;// 具体参考接口文档

        @NoArgsConstructor
        @Data
        public static class Nft {

            @JSONField(name = "class_id")
            private String classId;// NFT 合约地址

            @JSONField(name = "id")
            private Integer id;// NFT ID
        }

        @NoArgsConstructor
        @Data
        public static class Ns {

            @JSONField(name = "name")
            private String name;// 一级域名

            @JSONField(name = "owner")
            private String owner;// 当前域名拥有者的链账户地址

            @JSONField(name = "node")
            private String node;// 域名 node key

            @JSONField(name = "expires")
            private Integer expires;// 过期状态：Enum: 0:未过期; 1:已过期
        }

        @NoArgsConstructor
        @Data
        public static class Record {

            @JSONField(name = "record_id")
            private Integer recordId;// 区块链存证 ID

            @JSONField(name = "certificate_url")
            private String certificateUrl;// 区块链存证证书的下载链接；证书下载链接并非长期有效，请您尽快将证书文件下载至本地并妥善保管。
        }
    }
}
