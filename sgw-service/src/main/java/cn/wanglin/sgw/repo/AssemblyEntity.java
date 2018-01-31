package cn.wanglin.sgw.repo;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sgw_assembly")
@Data
public class AssemblyEntity implements Serializable {
    @Id
    String name;

    @Type(type="text")
    String content;
}
