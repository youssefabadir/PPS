package com.pps.controller.domain.picture;

import com.pps.controller.domain.category.CategoryEntity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "\"picture\"")
public class PictureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity categoryEntity;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "description = " + description + ", " +
                "status = " + status + ", " +
                "category = " + categoryEntity.getName() + ", " +
                "path = " + path + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        PictureEntity pictureEntity = (PictureEntity) o;
        return id != null && Objects.equals(id, pictureEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
