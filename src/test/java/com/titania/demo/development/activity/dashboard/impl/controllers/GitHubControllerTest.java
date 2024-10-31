package com.titania.demo.development.activity.dashboard.impl.controllers;

import com.titania.demo.development.activity.dashboard.impl.services.GitHubService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GitHubControllerTest {

    private static final String REPOSITORY_URL = "https://my-github-repository.com";
    private static final String DATE_FROM = "2023-01-01";
    private static final String DATE_TO = "2024-12-31";

    @Mock
    private GitHubService gitHubService;

    @InjectMocks
    private GitHubController gitHubController;

    @Test
    void commits_shouldReturnCommitCount_whenCommitDetailsPresent() {
        // given
        final Map<String, Integer> commitCounts = Map.of("user1", 5, "user2", 10, "user3", 15);
        given(gitHubService.calculateCommitCount(REPOSITORY_URL, DATE_FROM, DATE_TO)).willReturn(commitCounts);

        // when
        final ResponseEntity<Map<String, Integer>> response = gitHubController.commits(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        verify(gitHubService, times(1)).calculateCommitCount(REPOSITORY_URL, DATE_FROM, DATE_TO);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }

    @Test
    void commits_shouldReturnEmptyMap_whenNoCommitDetails() {
        // given
        given(gitHubService.calculateCommitCount(REPOSITORY_URL, DATE_FROM, DATE_TO)).willReturn(new HashMap<>());

        // when
        final ResponseEntity<Map<String, Integer>> response = gitHubController.commits(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        verify(gitHubService, times(1)).calculateCommitCount(REPOSITORY_URL, DATE_FROM, DATE_TO);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void pullRequests_shouldReturnPullRequestsCount_whenPullRequestDetailsPresent() {
        // given
        final Map<String, Integer> pullRequestCounts = Map.of("user1", 5, "user2", 10, "user3", 15);
        given(gitHubService.calculatePullRequestCount(REPOSITORY_URL, DATE_FROM, DATE_TO)).willReturn(pullRequestCounts);

        // when
        final ResponseEntity<Map<String, Integer>> response = gitHubController.pullRequests(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        verify(gitHubService, times(1)).calculatePullRequestCount(REPOSITORY_URL, DATE_FROM, DATE_TO);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }

    @Test
    void pullRequests_shouldReturnEmptyMap_whenNoPullRequestDetails() {
        // given
        given(gitHubService.calculatePullRequestCount(REPOSITORY_URL, DATE_FROM, DATE_TO)).willReturn(new HashMap<>());

        // when
        final ResponseEntity<Map<String, Integer>> response = gitHubController.pullRequests(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        verify(gitHubService, times(1)).calculatePullRequestCount(REPOSITORY_URL, DATE_FROM, DATE_TO);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void pullRequestComments_shouldReturnPullRequestCommentsCount_whenPullRequestCommentDetailsPresent() {
        // given
        final Map<String, Integer> pullRequestCommentCounts = Map.of("user1", 5, "user2", 10, "user3", 15);
        given(gitHubService.calculatePullRequestCommentCount(REPOSITORY_URL, DATE_FROM, DATE_TO)).willReturn(pullRequestCommentCounts);

        // when
        final ResponseEntity<Map<String, Integer>> response = gitHubController.pullRequestComments(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        verify(gitHubService, times(1)).calculatePullRequestCommentCount(REPOSITORY_URL, DATE_FROM, DATE_TO);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }

    @Test
    void pullRequestComments_shouldReturnEmptyMap_whenNoPullRequestCommentDetails() {
        // given
        given(gitHubService.calculatePullRequestCommentCount(REPOSITORY_URL, DATE_FROM, DATE_TO)).willReturn(new HashMap<>());

        // when
        final ResponseEntity<Map<String, Integer>> response = gitHubController.pullRequestComments(REPOSITORY_URL, DATE_FROM, DATE_TO);

        // then
        verify(gitHubService, times(1)).calculatePullRequestCommentCount(REPOSITORY_URL, DATE_FROM, DATE_TO);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }
}