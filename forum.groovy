import org.springframework.stereotype.Repository
import java.util.concurrent.atomic.AtomicLong

class Topic {
    Long id
    String name

    Topic() {}

    Topic(String name) {
        this.name = name
    }

    Topic(Long id, String name) {
        this.id = id
        this.name = name
    }
}

@Repository
class TopicRepository {
    static TreeMap<Long, Topic> topics = new TreeMap<>()
    static AtomicLong topicSequence = new AtomicLong()

    static {
        String[] topicNames = ["First Topic", "Second Topic", "Third Topic"]
        for (String topicName : topicNames) {
            Topic topic = new Topic(topicSequence.incrementAndGet(), topicName)
            topics.put(topic.getId(), topic)
        }
    }

    TreeMap<Long, Topic> findAll() {
        topics
    }

    Topic findById(Long id) {
        topics.get(id)
    }

    Topic save(Topic topic) {
        if (topic.getId() == null) {
            topic.setId(topicSequence.incrementAndGet())
        }
        topics.put(topic.getId(), topic)
        topic
    }

    Topic remove(Long id) {
        topics.remove(id)
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
@RequestMapping("/topics")
class TopicController {
    @Autowired
    private TopicRepository topicRepository

    @RequestMapping(method = RequestMethod.GET)
    TreeMap<Long, Topic> readTopics() {
        topicRepository.findAll()
    }

    @RequestMapping(value = "{topicId}", method = RequestMethod.GET)
    Topic readTopic(@PathVariable Long id) {
        topicRepository.findById(id)
    }

    @RequestMapping(method = RequestMethod.POST)
    Topic createTopic(@RequestBody Topic topic) {
        Topic savedTopic = topicRepository.save(topic)
        savedTopic
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    Topic updateTopic(@PathVariable Long id, @RequestBody Topic topic) {
        topic.setId(id)
        topicRepository.save(topic)
        topic
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    Topic deleteTopic(@PathVariable Long id) {
        Topic topic = topicRepository.remove(id)
        topic
    }
}
