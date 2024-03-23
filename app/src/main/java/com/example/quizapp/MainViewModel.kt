
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.example.quizapp.TopicIdentifier

class MainViewModel(private val resources: Resources) : ViewModel() {

    private val topics: MutableList<TopicIdentifier> = mutableListOf()

    init {
        loadTopics()
    }

    private fun loadTopics() {
        val assetManager = resources.assets
        val files = assetManager.list("questions")
        if (files != null) {
            for (filename in files) {
                val name = filename.substringBeforeLast(".")
                val filePath = "questions/$filename"
                topics.add(TopicIdentifier(name, filePath))
            }
        }
    }

    fun getTopicNames(): List<String> {
        return topics.map { it.name }
    }

    fun getTopicFileIdentifier(topicName: String): String {
        return topics.find { it.name == topicName }!!.filePath
    }
}