package by.smartym_pro.test_task.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", length = 6, nullable = false)
    private long id;

    @Column(name = "topic")
    private String topic;

    @OneToMany(mappedBy = "newsContent")
    private Set<NewsContent> newsContentSet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Set<NewsContent> getNewsContentSet() {
        return newsContentSet;
    }

    public void setNewsContentSet(Set<NewsContent> newsContentSet) {
        this.newsContentSet = newsContentSet;
    }
}
