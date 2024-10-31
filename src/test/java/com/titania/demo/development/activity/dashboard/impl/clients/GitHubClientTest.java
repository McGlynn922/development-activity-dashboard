package com.titania.demo.development.activity.dashboard.impl.clients;

import com.titania.demo.development.activity.dashboard.contract.models.github.Commit;
import com.titania.demo.development.activity.dashboard.contract.models.github.PullRequest;
import com.titania.demo.development.activity.dashboard.contract.models.github.PullRequestComment;
import com.titania.demo.development.activity.dashboard.contract.models.github.User;
import com.titania.demo.development.activity.dashboard.impl.exceptions.GitHubRetrievalException;
import com.titania.demo.development.activity.dashboard.impl.utils.UriUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GitHubClientTest {

    private static final String REPOSITORY_URL = "https://my-github-repository.com";
    private static final String DATE_FROM = "2023-01-01";
    private static final String DATE_TO = "2024-12-31";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitHubClient gitHubClient;

    @Test
    void fetchCommits_shouldReturnCommits_whenCommitsPresent() throws URISyntaxException {
        // given
        final User user = new User("user.one");
        final Commit commit = new Commit(user);
        final ResponseEntity<List<Commit>> response = new ResponseEntity<>(List.of(commit), HttpStatus.OK);

        given(restTemplate.exchange(UriUtils.buildQueryParameters(REPOSITORY_URL + "/commits", Map.of("since", DATE_FROM, "until", DATE_TO)),
                                   HttpMethod.GET,
                                   new HttpEntity<>(createHttpHeaders()),
                                   new ParameterizedTypeReference<List<Commit>>() {})).willReturn(response);

        // when
        final List<Commit> commits = this.gitHubClient.fetchCommits(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        assertEquals(1, commits.size());
    }

    @Test
    void fetchCommits_shouldThrowGitHubRetrievalException_whenNoCommits() throws URISyntaxException {
        // given
        given(restTemplate.exchange(UriUtils.buildQueryParameters(REPOSITORY_URL + "/commits", Map.of("since", DATE_FROM, "until", DATE_TO)),
                HttpMethod.GET,
                new HttpEntity<>(createHttpHeaders()),
                new ParameterizedTypeReference<List<Commit>>() {})).willThrow(RestClientException.class);


        // when
        // then
        assertThrows(GitHubRetrievalException.class, () -> this.gitHubClient.fetchCommits(REPOSITORY_URL, DATE_FROM, DATE_TO));
    }

    @Test
    void fetchPullRequests_shouldReturnPullRequests_whenPullRequestsPresent() throws URISyntaxException {
        // given
        final User user = new User("user.one");
        final PullRequest pullRequest = new PullRequest(user);
        final ResponseEntity<List<PullRequest>> response = new ResponseEntity<>(List.of(pullRequest), HttpStatus.OK);

        given(restTemplate.exchange(UriUtils.buildQueryParameters(REPOSITORY_URL + "/pulls", Map.of("since", DATE_FROM, "until", DATE_TO)),
                                    HttpMethod.GET,
                                    new HttpEntity<>(createHttpHeaders()),
                                    new ParameterizedTypeReference<List<PullRequest>>() {})).willReturn(response);

        // when
        final List<PullRequest> pullRequests = this.gitHubClient.fetchPullRequests(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        assertEquals(1, pullRequests.size());
    }

    @Test
    void fetchPullRequests_shouldThrowGitHubRetrievalException_whenNoPullRequests() throws URISyntaxException {
        // given
        given(restTemplate.exchange(UriUtils.buildQueryParameters(REPOSITORY_URL + "/pulls", Map.of("since", DATE_FROM, "until", DATE_TO)),
                HttpMethod.GET,
                new HttpEntity<>(createHttpHeaders()),
                String.class)).willThrow(RestClientException.class);

        // when
        // then
        assertThrows(GitHubRetrievalException.class, () -> this.gitHubClient.fetchPullRequests(REPOSITORY_URL, DATE_FROM, DATE_TO));
    }

    @Test
    void fetchPullRequestComments_shouldReturnPullRequestComments_whenPullRequestCommentsPresent() throws URISyntaxException {
        // given
        final User user = new User("user.one");
        final PullRequestComment pullRequestComment = new PullRequestComment(user);
        final ResponseEntity<List<PullRequestComment>> response = new ResponseEntity<>(List.of(pullRequestComment), HttpStatus.OK);

        given(restTemplate.exchange(UriUtils.buildQueryParameters(REPOSITORY_URL + "/pulls/comments", Map.of("since", DATE_FROM, "until", DATE_TO)),
                                    HttpMethod.GET,
                                    new HttpEntity<>(createHttpHeaders()),
                                    new ParameterizedTypeReference<List<PullRequestComment>>() {})).willReturn(response);

        // when
        final List<PullRequestComment> pullRequestComments = this.gitHubClient.fetchPullRequestComments(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        assertEquals(1, pullRequestComments.size());
    }

    @Test
    void fetchPullRequestComments_shouldThrowGitHubRetrievalException_whenNoPullRequestsComments() throws URISyntaxException {
        // given
        given(restTemplate.exchange(UriUtils.buildQueryParameters(REPOSITORY_URL + "/pulls/comments", Map.of("since", DATE_FROM, "until", DATE_TO)),
                HttpMethod.GET,
                new HttpEntity<>(createHttpHeaders()),
                String.class)).willThrow(RestClientException.class);

        // when
        // then
        assertThrows(GitHubRetrievalException.class, () -> this.gitHubClient.fetchPullRequestComments(REPOSITORY_URL, DATE_FROM, DATE_TO));
    }

    private HttpHeaders createHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(null);
        headers.add("Accept", "application/vnd.github.v3+json");

        return headers;
    }
}