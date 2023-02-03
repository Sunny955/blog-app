package com.blogapp.blogappiapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Integer categoryId;
    @Column(name="title",length = 50,nullable = false)
    private String categoryTitle;
    @Column(name="description",length = 100,nullable = false)
    private String categoryDescription;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
}