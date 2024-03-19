import androidx.lifecycle.ViewModel
import com.example.quizapp.QuestionBank
import com.example.quizapp.Topic
import com.google.gson.Gson

class MainViewModel : ViewModel() {

    lateinit var questionBank: QuestionBank

    fun loadQuestions(jsonString: String) {
        questionBank = Gson().fromJson(jsonString, QuestionBank::class.java)
    }

    fun getTopics(): List<Topic> {
        return questionBank.topics
    }
}