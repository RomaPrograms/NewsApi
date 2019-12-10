package by.smartym_pro.test_task.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id", length = 6, nullable = false)
    private long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name="newsContent_id", nullable=false)
    private NewsContent newsContent;
}
