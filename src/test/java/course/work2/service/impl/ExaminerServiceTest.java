package course.work2.service.impl;

import course.work2.exception.NotEnoughQuestionException;
import course.work2.model.Question;
import course.work2.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class ExaminerServiceTest {
    @Mock
    private QuestionService questionService;
    @InjectMocks
    private ExaminerServiceImpl examinerService;

    private final List<Question> questions = List.of(
            new Question("Вопрос 1", "Ответ 1"),
            new Question("Вопрос 2", "Ответ 2"),
            new Question("Вопрос 3", "Ответ 3"),
            new Question("Вопрос 4", "Ответ 4"),
            new Question("Вопрос 5", "Ответ 5")
    );

    @BeforeEach
    public void beforeEach() {
        when(questionService.getAll()).thenReturn(questions);
    }

    @Test
    public void getQuestionNegativeTest() {
        assertThatExceptionOfType(NotEnoughQuestionException.class)
                .isThrownBy(() -> examinerService.getQuestions(questions.size() + 1));
    }

    @Test
    public void getQuestionPositiveTest() {
        when(questionService.getRandomQuestion()).thenReturn(
                new Question("Вопрос 5", "Ответ 5"),
                new Question("Вопрос 5", "Ответ 5"),
                new Question("Вопрос 2", "Ответ 2"),
                new Question("Вопрос 2", "Ответ 2"),
                new Question("Вопрос 1", "Ответ 1"),
                new Question("Вопрос 4", "Ответ 4"),
                new Question("Вопрос 4", "Ответ 4"),
                new Question("Вопрос 1", "Ответ 1")
        );

        assertThat(examinerService.getQuestions(4))
                .containsExactlyInAnyOrder(
                        new Question("Вопрос 1", "Ответ 1"),
                        new Question("Вопрос 2", "Ответ 2"),
                        new Question("Вопрос 4", "Ответ 4"),
                        new Question("Вопрос 5", "Ответ 5")
                );
    }
}
