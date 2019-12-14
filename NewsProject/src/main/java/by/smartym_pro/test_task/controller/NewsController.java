package by.smartym_pro.test_task.controller;

import by.smartym_pro.test_task.entity.News;
import by.smartym_pro.test_task.entity.UserType;
import by.smartym_pro.test_task.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RequestMapping("/news")
@RestController
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping(value = "/allTopics")
    public ResponseEntity<List<News>> getAllNews(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpSession session = request.getSession();

        if (session.getAttribute("ROLE") == null) {
            request.setAttribute("ERROR_MESSAGE",
                    "Sorry, but you should be logged in to view the news.");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        Optional<List<News>> newsList
                = Optional.of(this.newsRepository.findAll());

        return newsList.map(
                news -> new ResponseEntity<>(news, headers, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping(value = "/topics")
    public ResponseEntity<List<News>> getNewsByTopics(
            @RequestParam("topic") List<String> topics,
            HttpServletRequest request) {

        HttpHeaders headers = new HttpHeaders();
        HttpSession session = request.getSession();

        if (session.getAttribute("ROLE") == null) {
            request.setAttribute("ERROR_MESSAGE",
                    "Sorry, but you should be logged in to view the news.");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        if (topics.isEmpty()) {
            request.setAttribute("ERROR_MESSAGE",
                    "Incorrect data for this operation.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<List<News>> newsList = this.newsRepository.findByTopic(topics);

        return newsList.map(
                news -> new ResponseEntity<>(news, headers, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping(value = "/addNews")
    public ResponseEntity<News> addNews(@RequestBody News news,
                                        HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpSession session = request.getSession();

        if (session.getAttribute("ROLE").equals(UserType.ADMIN)) {
            request.setAttribute("ERROR_MESSAGE",
                    "Sorry, but you don't have access to this operation");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        if (news == null) {
            request.setAttribute("ERROR_MESSAGE",
                    "Incorrect data for this operation.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.newsRepository.save(news);

        return new ResponseEntity<>(news, headers, HttpStatus.OK);
    }

}
