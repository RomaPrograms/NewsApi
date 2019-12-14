package by.smartym_pro.test_task.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "newsContent")
public class NewsContent {
    @Id
    @Column(name = "id", length = 6, nullable = false)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    private News news;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comments_id")
    private Set<Comment> newsCommentSet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
