package by.smartym_pro.test_task.controller;

import by.smartym_pro.test_task.entity.News;
import by.smartym_pro.test_task.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/news")
@RestController
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping
    public @ResponseBody News getAllNews() {
        return new News();
    }
}
