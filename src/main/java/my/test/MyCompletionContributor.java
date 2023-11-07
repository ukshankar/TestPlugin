package my.test;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

public class MyCompletionContributor extends CompletionContributor {
    public static final int LENGTH = 12;
    Semaphore lock = new Semaphore(1);

    @Override
    public void fillCompletionVariants(CompletionParameters parameters, CompletionResultSet result) {
        if (parameters.getCompletionType() == CompletionType.SMART) {
            int pos = parameters.getOffset();
            String txt = parameters.getEditor().getDocument().getText(new TextRange(pos - LENGTH, pos));
            if ("@Demo_Tibco1".equals(txt)) {
                if (lock.tryAcquire()) {
                    ApplicationManager.getApplication().invokeLater(() -> {
                        WriteCommandAction.runWriteCommandAction(parameters.getEditor().getProject(), () -> {
                            Document document = parameters.getEditor().getDocument();
                            document.replaceString(pos - LENGTH, pos, TEMPLATE_TIBCO);
                            lock.release();
                        });
                    });
                }
            }
            if ("@Demo_Solace".equals(txt)) {
                if (lock.tryAcquire()) {
                    ApplicationManager.getApplication().invokeLater(() -> {
                        WriteCommandAction.runWriteCommandAction(parameters.getEditor().getProject(), () -> {
                            Document document = parameters.getEditor().getDocument();
                            document.replaceString(pos - LENGTH, pos, TEMPLATE_SOLACE);
                            lock.release();
                        });
                    });
                }
            }
            if ("@Demo_Oracle".equals(txt)) {
                if (lock.tryAcquire()) {
                    ApplicationManager.getApplication().invokeLater(() -> {
                        WriteCommandAction.runWriteCommandAction(parameters.getEditor().getProject(), () -> {
                            Document document = parameters.getEditor().getDocument();
                            document.replaceString(pos - LENGTH, pos, TEMPLATE_ORACLE);
                            lock.release();
                        });
                    });
                }
            }
        }
    }

    private static final String TEMPLATE_TIBCO = "Scenario Outline: Sending a Message via TIBCO\n" +
            "\n" +
            "Given I am a user with access to the TIBCO messaging system\n" +
            "When I send a message with the following details:\n" +
            "| Message Type | Message Content | Destination |\n" +
            "| Text | \"Hello, TIBCO!\" | Queue |\n" +
            "Then the message should be successfully delivered\n" +
            "And the message should be received by the intended recipient\n" +
            "\n" +
            "Examples:\n" +
            "| Message Type | Message Content | Destination |\n" +
            "| Text | \"Hello, TIBCO!\" | Queue |\n" +
            "| JSON | {\"key\": \"value\"} | Topic |\n" +
            "\n";

    private static final String TEMPLATE_SOLACE = "Scenario: Sending and Validating a Message via Solace\n" +
            "\n" +
            "Feature: Message Integration with Solace\n" +
            "\n" +
            "Background:\n" +
            "\n" +
            "Given that the Solace message broker is up and running.\n" +
            "And I have a valid connection to the Solace messaging system.\n" +
            "And I have the necessary permissions and credentials to send and receive messages.\n" +
            "Scenario Outline: Sending and Validating a Message via Solace\n" +
            "\n" +
            "Given I am a user with access to the Solace messaging system\n" +
            "When I send a message with the following details:\n" +
            "| Message Content | Destination Type | Destination Name |\n" +
            "| \"Hello, Solace!\" | Topic | /my/topic |\n" +
            "Then the message should be successfully delivered\n" +
            "And the message should be received by a subscriber on the same topic\n" +
            "\n" +
            "Examples:\n" +
            "| Message Content | Destination Type | Destination Name |\n" +
            "| \"Hello, Solace!\" | Topic | /my/topic |\n" +
            "| {\"key\": \"value\"} | Queue | myQueue |";
    private static final String TEMPLATE_ORACLE = "Scenario: Querying Data from Oracle Database\n" +
            "\n" +
            "Feature: Data Retrieval from Oracle\n" +
            "\n" +
            "Background:\n" +
            "\n" +
            "Given that the Oracle database is up and running.\n" +
            "And I have a valid connection to the Oracle database.\n" +
            "And I have the necessary permissions and credentials to execute SQL queries.\n" +
            "Scenario Outline: Querying Data from Oracle Database\n" +
            "\n" +
            "Given I am a user with access to the Oracle database\n" +
            "When I execute the following SQL query:" +
            "Examples:\n" +
            "| department_value |\n" +
            "| HR |\n" +
            "| Sales |";


    public static String callAIModel(String text) {
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .model("ada")
//                .prompt("Somebody once told me the world is gonna roll me")
//                .echo(true)
//                .user("testing")
//                .n(3)
//                .build();
//        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        return "HelloTest";
    }
}
