package by.smartym_pro.test_task.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(name = "topic", unique = true, nullable = false)
    private String topic;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "newsContent_id")
    private Set<NewsContent> newsContentSet;

    public News() {}

    public News(@JsonProperty("topic") String topic) {
        System.out.println("Lukky");
        this.topic = topic;
    }

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
//
//    public Set<NewsContent> getNewsContentSet() {
//        return newsContentSet;
//    }
//
//    public void setNewsContentSet(Set<NewsContent> newsContentSet) {
//        this.newsContentSet = newsContentSet;
//    }
}
