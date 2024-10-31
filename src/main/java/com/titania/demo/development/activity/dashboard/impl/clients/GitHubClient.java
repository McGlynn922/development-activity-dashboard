package com.titania.demo.development.activity.dashboard.impl.clients;

import com.titania.demo.development.activity.dashboard.contract.models.github.Commit;
import com.titania.demo.development.activity.dashboard.contract.models.github.PullRequest;
import com.titania.demo.development.activity.dashboard.contract.models.github.PullRequestComment;
import com.titania.demo.development.activity.dashboard.impl.exceptions.GitHubRetrievalException;
import com.titania.demo.development.activity.dashboard.impl.utils.UriUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class GitHubClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubClient.class);

    private static final String SINCE = "since";
    private static final String UNTIL = "until";

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    public GitHubClient(final RestTemplate restTemplate,
                        @Value("${github.bearer.token}") final String bearerToken) {
        this.restTemplate = restTemplate;
        this.headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        headers.add("Accept", "application/vnd.github.v3+json");
    }

    public List<Commit> fetchCommits(final String repositoryUrl, final String dateFrom, final String dateTo) {
        LOGGER.info("Connecting to the GitHub API to retrieve commits for repository [{}]", repositoryUrl);

        try {
            final ResponseEntity<List<Commit>> response = restTemplate.exchange(UriUtils.buildQueryParameters(repositoryUrl + "/commits", Map.of(SINCE, dateFrom, UNTIL, dateTo)),
                    HttpMethod.GET,
                    new HttpEntity<>(this.headers()),
                    new ParameterizedTypeReference<>() {});

            return response.getBody();
        } catch (final Exception e) {
            throw new GitHubRetrievalException("Unable to retrieve commits for repository [" + repositoryUrl + "]. Cause: ", e);
        }
    }

    public List<PullRequest> fetchPullRequests(final String repositoryUrl, final String dateFrom, final String dateTo) {
        LOGGER.info("Connecting to the GitHub API to retrieve pull requests for repository [{}]", repositoryUrl);
        try {
            final ResponseEntity<List<PullRequest>> response = restTemplate.exchange(UriUtils.buildQueryParameters(repositoryUrl + "/pulls", Map.of(SINCE, dateFrom, UNTIL, dateTo)),
                    HttpMethod.GET,
                    new HttpEntity<>(this.headers()),
                    new ParameterizedTypeReference<>() {});

            return response.getBody();
        } catch (final Exception e) {
            throw new GitHubRetrievalException("Unable to retrieve pull requests for repository [" + repositoryUrl + "]. Cause: ", e);
        }
    }

    public List<PullRequestComment> fetchPullRequestComments(final String repositoryUrl, final String dateFrom, final String dateTo) {
        LOGGER.info("Connecting to the GitHub API to retrieve comments for repository [{}]", repositoryUrl);
        try {
            final ResponseEntity<List<PullRequestComment>> response = restTemplate.exchange(UriUtils.buildQueryParameters(repositoryUrl + "/pulls/comments", Map.of(SINCE, dateFrom, UNTIL, dateTo)),
                    HttpMethod.GET,
                    new HttpEntity<>(this.headers()),
                    new ParameterizedTypeReference<>() {});

            return response.getBody();
        } catch (final Exception e) {
            throw new GitHubRetrievalException("Unable to retrieve pull request comments for repository [" + repositoryUrl + "]. Cause: ", e);
        }
    }

    private HttpHeaders headers() {
        return headers;
    }
}
