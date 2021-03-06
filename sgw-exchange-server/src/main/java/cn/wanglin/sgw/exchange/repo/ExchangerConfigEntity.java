package cn.wanglin.sgw.exchange.repo;

import cn.wanglin.sgw.exchange.*;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.Charset;

@Entity
@Table(name = "sgw_exchanger_config")
@Data
public class ExchangerConfigEntity implements Serializable {
    @Id
    public String       exchangerCode;
    public String       url;
    public String       certFile;
    public String       certPassword;
    @Enumerated(EnumType.STRING)
    public ProtocolType protocol;
    @Enumerated(EnumType.STRING)
    public Endpoint     endpoint;
    @Enumerated(EnumType.STRING)
    public EntityType   reqEntityType;
    @Enumerated(EnumType.STRING)
    public EntityType   rspEntityType;
    public Integer      coreSize;
    public Integer      maxSize;
    @Enumerated(EnumType.STRING)
    public HttpMethod   method;
    public String       charset;
    public Boolean      sync;
    public Integer      timeout;
    @Column(length = 1024)
    public String       attributes;

    @OneToOne
    AssemblyEntity assembly;
    @OneToOne
    ParserEntity   parser;

    public ExchangerConfig toDto() {
        ExchangerConfig dto = new ExchangerConfig();
        dto.exchangerCode = exchangerCode;
        dto.url = url;
        dto.certFile = certFile;
        dto.certPassword = certPassword;
        dto.protocol = null == protocol?ProtocolType.HTTP:protocol;
        dto.endpoint = null == endpoint?Endpoint.CLIENT:endpoint;
        dto.reqEntityType = null == reqEntityType?EntityType.TEXT:reqEntityType;
        dto.rspEntityType = null == rspEntityType?EntityType.TEXT:rspEntityType;
        dto.method = null == method?HttpMethod.POST:method;
        dto.coreSize = null == coreSize?1:coreSize;
        dto.maxSize = null == maxSize?2:maxSize;
        dto.charset = null == charset?Charset.forName("UTF-8"):Charset.forName(charset);
        dto.sync = null == sync?true:sync;
        dto.timeout = null == timeout?6000:timeout;
        return dto;
    }
}
