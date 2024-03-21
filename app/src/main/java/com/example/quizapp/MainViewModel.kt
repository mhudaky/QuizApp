
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.example.quizapp.R
import com.example.quizapp.TopicIdentifier
import java.util.Locale

class MainViewModel(private val resources: Resources) : ViewModel() {

    private val topics: MutableList<TopicIdentifier> = mutableListOf()

    init {
        loadAllQuestions()
    }

    private fun loadAllQuestions() {
        val fields = R.raw::class.java.fields
        for (field in fields) {
            val name = field.name
            val fileId = resources.getIdentifier(name, "raw", resources.getResourcePackageName(R.raw::class.java.fields[0].getInt(R.raw::class.java.fields[0])))
            topics.add(TopicIdentifier(name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }, fileId))
        }
    }

    fun getTopicNames(): List<String> {
        return topics.map { it.name }
    }

    fun getTopicFileIdentifier(topicName: String): Int {
        return topics.find { it.name == topicName }!!.fileId
    }
}