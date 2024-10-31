package com.titania.demo.development.activity.dashboard.impl.counter;

import com.titania.demo.development.activity.dashboard.contract.models.github.Commit;
import com.titania.demo.development.activity.dashboard.contract.models.github.PullRequest;
import com.titania.demo.development.activity.dashboard.contract.models.github.PullRequestComment;
import com.titania.demo.development.activity.dashboard.contract.models.github.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class CounterTest {

    private final Counter counter = new Counter();

    @Test
    void calculateCommitCount_shouldReturnCorrectCounts() {
        // given
        final User userOne = new User("user.one");
        final User userTwo = new User("user.two");
        final Commit commitOne = new Commit(userOne);
        final Commit commitTwo = new Commit(userOne);
        final Commit commitThree = new Commit(userTwo);

        final List<Commit> commits = List.of(commitOne, commitTwo, commitThree);

        // when
        final Map<String, Integer> commitCounts = this.counter.calculateCommitCount(commits);

        // then
        assertEquals(2, commitCounts.get("user.one"));
        assertEquals(1, commitCounts.get("user.two"));
    }

    @Test
    void calculateCommitCount_shouldIgnoreNullUsernames() {
        // given
        final User userOne = new User("user.one");
        final User userTwo = new User(null);
        final Commit commitOne = new Commit(userOne);
        final Commit commitTwo = new Commit(userOne);
        final Commit commitThree = new Commit(userTwo);

        final List<Commit> commits = List.of(commitOne, commitTwo, commitThree);

        // when
        final Map<String, Integer> commitCounts = this.counter.calculateCommitCount(commits);

        // then
        assertEquals(2, commitCounts.get("user.one"));
        assertNull(commitCounts.get("user.two"));
    }

    @Test
    void calculatePullRequestCount_shouldReturnCorrectCounts() {
        // given
        final User userOne = new User("user.one");
        final User userTwo = new User("user.two");
        final PullRequest pullRequestOne = new PullRequest(userOne);
        final PullRequest pullRequestTwo = new PullRequest(userOne);
        final PullRequest pullRequestThree = new PullRequest(userTwo);

        final List<PullRequest> pullRequests = List.of(pullRequestOne, pullRequestTwo, pullRequestThree);

        // when
        final Map<String, Integer> pullRequestCounts = this.counter.calculatePullRequestCount(pullRequests);

        // then
        assertEquals(2, pullRequestCounts.get("user.one"));
        assertEquals(1, pullRequestCounts.get("user.two"));
    }

    @Test
    void calculatePullRequestCount_shouldIgnoreNullUsernames() {
        // given
        final User userOne = new User("user.one");
        final User userTwo = new User(null);
        final PullRequest pullRequestOne = new PullRequest(userOne);
        final PullRequest pullRequestTwo = new PullRequest(userOne);
        final PullRequest pullRequestThree = new PullRequest(userTwo);

        final List<PullRequest> pullRequests = List.of(pullRequestOne, pullRequestTwo, pullRequestThree);

        // when
        final Map<String, Integer> pullRequestCounts = this.counter.calculatePullRequestCount(pullRequests);

        // then
        assertEquals(2, pullRequestCounts.get("user.one"));
        assertNull(pullRequestCounts.get("user.two"));
    }

    @Test
    void calculatePullRequestCommentCount_shouldReturnCorrectCounts() {
        // given
        final User userOne = new User("user.one");
        final User userTwo = new User("user.two");
        final PullRequestComment pullRequestCommentOne = new PullRequestComment(userOne);
        final PullRequestComment pullRequestCommentTwo = new PullRequestComment(userOne);
        final PullRequestComment pullRequestCommentThree = new PullRequestComment(userTwo);

        final List<PullRequestComment> pullRequestComments = List.of(pullRequestCommentOne, pullRequestCommentTwo, pullRequestCommentThree);

        // when
        final Map<String, Integer> pullRequestCommentCounts = this.counter.calculatePullRequestCommentCount(pullRequestComments);

        // then
        assertEquals(2, pullRequestCommentCounts.get("user.one"));
        assertEquals(1, pullRequestCommentCounts.get("user.two"));
    }

    @Test
    void calculatePullRequestCommentCount_shouldIgnoreNullUsernames() {
        // given
        final User userOne = new User("user.one");
        final User userTwo = new User(null);
        final PullRequestComment pullRequestCommentOne = new PullRequestComment(userOne);
        final PullRequestComment pullRequestCommentTwo = new PullRequestComment(userOne);
        final PullRequestComment pullRequestCommentThree = new PullRequestComment(userTwo);

        final List<PullRequestComment> pullRequestComments = List.of(pullRequestCommentOne, pullRequestCommentTwo, pullRequestCommentThree);

        // when
        final Map<String, Integer> pullRequestCommentCounts = this.counter.calculatePullRequestCommentCount(pullRequestComments);

        // then
        assertEquals(2, pullRequestCommentCounts.get("user.one"));
        assertNull(pullRequestCommentCounts.get("user.two"));
    }

}