package com.blogapp.blogappiapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="posts")
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    @Column(name="post_title",nullable = false)
    private String title;
    @Column(name="post_content",length = 100000)
    private String content;
    @Column(name="image_name")
    private String imageName;
    @Column(name="added_date",nullable = false)
    private Date addedDate;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
}