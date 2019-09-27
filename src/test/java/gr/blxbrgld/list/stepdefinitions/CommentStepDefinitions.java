package gr.blxbrgld.list.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import gr.blxbrgld.list.ListTestBase;
import gr.blxbrgld.list.model.Comment;
import gr.blxbrgld.list.service.CommentService;
import gr.blxbrgld.list.service.FixtureService;
import gr.blxbrgld.list.steps.CommonSteps;
import gr.blxbrgld.list.utils.HttpMethod;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Comment step definitions
 * @author npapadopoulos
 */
public class CommentStepDefinitions extends ListTestBase {

    @Autowired
    private FixtureService fixtureService;

    @Autowired
    private CommentService commentService;

    @Steps
    CommonSteps commonSteps;

    private static final String COMMENTS_PATH = "/comments/";

    @Given("^comment with title (.*) exists$")
    public void commentExist(String title) {
        commentService.persistOrMergeComment( // Persist both as comment and fixtures
            fixtureService.commentFixture(title)
        );
    }

    @When("^request comment (.*) by id$")
    public void getCommentById(String title) {
        Optional<Comment> comment = commentService.getComment(title);
        Integer id = comment.map(Comment::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(HttpMethod.GET, COMMENTS_PATH + id);
    }

    @When("^comment list is requested$")
    public void getComments() {
        commonSteps.request(HttpMethod.GET, COMMENTS_PATH);
    }

    @When("^request to create comment with title (.*)$")
    public void createComment(String title) {
        commonSteps.request(HttpMethod.POST, COMMENTS_PATH, fixtureService.commentFixture(title));
    }

    @When("^request to update comment with title (.*) to (.*)$")
    public void updateComment(String existing, String updated) {
        Optional<Comment> comment = commentService.getComment(existing);
        Integer id = comment.map(Comment::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(
            HttpMethod.PUT,
                COMMENTS_PATH + id,
            fixtureService.commentFixture(updated)
        );
    }

    @When("^request to delete comment with title (.*)$")
    public void deleteComment(String title) {
        Optional<Comment> comment = commentService.getComment(title);
        Integer id = comment.map(Comment::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(HttpMethod.DELETE, COMMENTS_PATH + id);
    }
}
