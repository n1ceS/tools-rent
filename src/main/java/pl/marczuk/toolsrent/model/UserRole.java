package pl.marczuk.toolsrent.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userroles")
@Getter
@Setter
public class UserRole {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;
}
