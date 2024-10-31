package com.titania.demo.development.activity.dashboard.impl.services;

import com.titania.demo.development.activity.dashboard.contract.models.github.Commit;
import com.titania.demo.development.activity.dashboard.contract.models.github.PullRequest;
import com.titania.demo.development.activity.dashboard.contract.models.github.PullRequestComment;
import com.titania.demo.development.activity.dashboard.contract.models.github.User;
import com.titania.demo.development.activity.dashboard.impl.clients.GitHubClient;
import com.titania.demo.development.activity.dashboard.impl.counter.Counter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GitHubServiceTest {

    private static final String REPOSITORY_URL = "https://my-github-repository.com";
    private static final String DATE_FROM = "2023-01-01";
    private static final String DATE_TO = "2024-12-31";

    @Mock
    private GitHubClient gitHubClient;

    @Mock
    private Counter counter;

    @InjectMocks
    private GitHubService gitHubService;

    @Test
    void calculateCommitCount_shouldReturnCommitCounts_whenCommitDetailsPresent() {
        // given
        final User userOne = new User("user.one");
        final User userTwo = new User("user.two");
        final User userThree = new User("user.three");
        final Commit commitOne = new Commit(userOne);
        final Commit commitTwo = new Commit(userTwo);
        final Commit commitThree = new Commit(userThree);
        final List<Commit> commits = List.of(commitOne, commitTwo, commitThree);

        given(gitHubClient.fetchCommits(REPOSITORY_URL, DATE_FROM, DATE_TO)).willReturn(commits);
        given(counter.calculateCommitCount(commits)).willReturn(Map.of("user.one", 1, "user.two", 1, "user.three", 1));

        // when
        final Map<String, Integer> commitCount = gitHubService.calculateCommitCount(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        assertEquals(1, commitCount.get("user.one"));
        assertEquals(1, commitCount.get("user.two"));
        assertEquals(1, commitCount.get("user.three"));
    }

    @Test
    void calculateCommitCount_shouldReturnEmptyList_whenNoCommitDetails() {
        // given
        given(gitHubClient.fetchCommits(REPOSITORY_URL, DATE_FROM, DATE_TO)).willReturn(new ArrayList<>());
        given(counter.calculateCommitCount(new ArrayList<>())).willReturn(new HashMap<>());

        // when
        final Map<String, Integer> commitCount = gitHubService.calculateCommitCount(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        assertEquals(0, commitCount.size());
    }

    @Test
    void calculatePullRequestCount_shouldReturnPullRequestCounts_whenPullRequestDetailsPresent() {
        // given
        final User userOne = new User("user.one");
        final User userTwo = new User("user.two");
        final User userThree = new User("user.three");
        final PullRequest pullRequestOne = new PullRequest(userOne);
        final PullRequest pullRequestTwo = new PullRequest(userTwo);
        final PullRequest pullRequestThree = new PullRequest(userThree);

        final List<PullRequest> pullRequests = List.of(pullRequestOne, pullRequestTwo, pullRequestThree);

        given(gitHubClient.fetchPullRequests(REPOSITORY_URL, DATE_FROM, DATE_TO)).willReturn(pullRequests);
        given(counter.calculatePullRequestCount(pullRequests)).willReturn(Map.of("user.one", 1, "user.two", 1, "user.three", 1));

        // when
        final Map<String, Integer> pullRequestCount = gitHubService.calculatePullRequestCount(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        assertEquals(1, pullRequestCount.get("user.one"));
        assertEquals(1, pullRequestCount.get("user.two"));
        assertEquals(1, pullRequestCount.get("user.three"));
    }

    @Test
    void calculatePullRequestCount_shouldReturnPullRequestCounts_whenNoPullRequestDetails() {
        // given
        given(gitHubClient.fetchPullRequests(REPOSITORY_URL, DATE_FROM, DATE_TO)).willReturn(new ArrayList<>());
        given(counter.calculatePullRequestCount(new ArrayList<>())).willReturn(new HashMap<>());

        // when
        final Map<String, Integer> commitCount = gitHubService.calculatePullRequestCount(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        assertEquals(0, commitCount.size());
    }

    @Test
    void calculatePullRequestCommentCount_shouldReturnPullRequestCommentCounts_whenPullRequestCommentDetailsPresent() {
        // given
        final User userOne = new User("user.one");
        final User userTwo = new User("user.two");
        final User userThree = new User("user.three");
        final PullRequestComment pullRequestCommentOne = new PullRequestComment(userOne);
        final PullRequestComment pullRequestCommentTwo = new PullRequestComment(userTwo);
        final PullRequestComment pullRequestCommentThree = new PullRequestComment(userThree);

        final List<PullRequestComment> pullRequestComments = List.of(pullRequestCommentOne, pullRequestCommentTwo, pullRequestCommentThree);

        given(gitHubClient.fetchPullRequestComments(REPOSITORY_URL, DATE_FROM, DATE_TO)).willReturn(pullRequestComments);
        given(counter.calculatePullRequestCommentCount(pullRequestComments)).willReturn(Map.of("user.one", 1, "user.two", 1, "user.three", 1));

        // when
        final Map<String, Integer> pullRequestCount = gitHubService.calculatePullRequestCommentCount(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        assertEquals(1, pullRequestCount.get("user.one"));
        assertEquals(1, pullRequestCount.get("user.two"));
        assertEquals(1, pullRequestCount.get("user.three"));
    }

    @Test
    void calculatePullRequestCommentCount_shouldReturnPullRequestCommentCounts_whenNoPullRequestCommentDetails() {
        // given
        given(gitHubClient.fetchPullRequestComments(REPOSITORY_URL, DATE_FROM, DATE_TO)).willReturn(new ArrayList<>());
        given(counter.calculatePullRequestCommentCount(new ArrayList<>())).willReturn(new HashMap<>());

        // when
        final Map<String, Integer> commitCount = gitHubService.calculatePullRequestCommentCount(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        assertEquals(0, commitCount.size());
    }
}