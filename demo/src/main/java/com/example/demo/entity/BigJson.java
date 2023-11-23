package com.example.demo.entity;

import com.example.demo.tools.FileTool;
import com.example.demo.tools.IdTool;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lxh
 */
@Table(name = "big_json")
public class BigJson {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "source_id")
    private String sourceId;

    @Column(name = "vc_param")
    private String vcParam;

    @Column(name = "vc_result")
    private String vcResult;

    public String getId() {
        return id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getVcParam() {
        return vcParam;
    }

    public String getVcResult() {
        return vcResult;
    }

    public static final class Builder {
        private String id;
        private String sourceId;
        private String vcParam;
        private String vcResult;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder sourceId(String sourceId) {
            this.sourceId = sourceId;
            return this;
        }

        public Builder vcParam(String vcParam) {
            this.vcParam = vcParam;
            return this;
        }

        public Builder vcResult(String vcResult) {
            this.vcResult = vcResult;
            return this;
        }

        public BigJson build() {
            BigJson bigJson = new BigJson();
            bigJson.vcResult = this.vcResult;
            bigJson.vcParam = this.vcParam;
            bigJson.sourceId = this.sourceId;
            bigJson.id = this.id;
            return bigJson;
        }
    }

    static final AtomicInteger cursor = new AtomicInteger(1);

    static String nextSourceId() {
        return "FT" + String.format("%6s", cursor.getAndIncrement()).replaceAll(" ", "0");
    }

    public static BigJson example() {
        return BigJson.Builder.builder()
                .id(String.valueOf(IdTool.singleSnowFlake()))
                .sourceId(nextSourceId())
                .vcParam(FileTool.loadParamJson())
                .vcResult(FileTool.loadResultJson())
                .build();
    }
}
