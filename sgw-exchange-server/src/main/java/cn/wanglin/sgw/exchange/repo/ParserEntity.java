package cn.wanglin.sgw.exchange.repo;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "sgw_parser")
@Data
public class ParserEntity implements Serializable {
    @Id
    String name;
    @Type(type="text")
    String content;

}
