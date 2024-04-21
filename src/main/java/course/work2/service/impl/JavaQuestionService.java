package course.work2.service.impl;

import course.work2.exception.AlreadyExistsException;
import course.work2.exception.EmptyException;
import course.work2.exception.NotFoundException;
import course.work2.model.Question;
import course.work2.service.QuestionService;

import java.util.*;


public class JavaQuestionService implements QuestionService {

    private final Set<Question> questions = new HashSet<>();
    private final Random random = new Random();

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        if (!questions.add(question)) {
            throw new AlreadyExistsException();
        }
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (!questions.remove(question)) {
            throw new NotFoundException();
        }
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return Collections.unmodifiableCollection(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new EmptyException();
        }
        int count = random.nextInt(questions.size());
        int counter = 0;
        for (Question question : questions) {
            if (counter == count) {
                return question;
            }
            counter++;
        }
        return null;
    }
}
