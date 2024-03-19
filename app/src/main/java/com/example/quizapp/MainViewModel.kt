
import androidx.lifecycle.ViewModel
import com.example.quizapp.QuestionBank
import com.example.quizapp.Topic
import com.google.gson.Gson
import java.io.InputStream

class MainViewModel : ViewModel() {

    lateinit var questionBank: QuestionBank

    fun loadQuestions(inputStream: InputStream) {
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        questionBank = Gson().fromJson(jsonString, QuestionBank::class.java)
    }

    fun getTopics(): List<Topic> {
        return questionBank.topics
    }
}