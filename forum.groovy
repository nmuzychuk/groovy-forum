import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

class Topic {
    String name

    Topic() {}

    Topic(String name) {
        this.name = name
    }
}

@Service
class TopicRepository {
    static List<Topic> topics = new ArrayList<>()

    static {
        topics.push(new Topic("First Topic"))
        topics.push(new Topic("Second Topic"))
        topics.push(new Topic("Third Topic"))
    }

    Collection<Topic> findAll() {
        topics
    }

    Topic findById(int id) {
        topics.get(id - 1)
    }

    void save(Topic topic) {
        topics.push(topic)
    }
}

@Controller
class HomepageController {
    @RequestMapping("/")
    String getHomepage() {
        "redirect:/topics"
    }
}

@RestController
class TopicController {
    @Autowired
    private TopicRepository topicRepository

    @RequestMapping(value = "/topics", method = RequestMethod.GET)
    Collection<Topic> readTopics() {
        topicRepository.findAll()
    }

    @RequestMapping(value = "/topics/{topicId}", method = RequestMethod.GET)
    Topic readTopic(@PathVariable int topicId) {
        topicRepository.findById(topicId)
    }
}
