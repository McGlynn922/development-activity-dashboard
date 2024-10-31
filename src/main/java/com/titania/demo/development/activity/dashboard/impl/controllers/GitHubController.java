package com.titania.demo.development.activity.dashboard.impl.controllers;

import com.titania.demo.development.activity.dashboard.impl.services.GitHubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GitHubController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubController.class);

    private final GitHubService gitHubService;

    public GitHubController(final GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping(value = "/commits", produces = "application/json")
    public ResponseEntity<Map<String, Integer>> commits(@RequestHeader final String repositoryUrl,
                                                        @RequestHeader final String dateFrom,
                                                        @RequestHeader final String dateTo) {
        LOGGER.info("Received request to calculate the commits per user for repository [{}] from [{}] until [{}]", repositoryUrl, dateFrom, dateTo);
        final Map<String, Integer> commitCounts = this.gitHubService.calculateCommitCount(repositoryUrl, dateFrom, dateTo);

        return ResponseEntity.ok(commitCounts);
    }

    @GetMapping(value = "/pull-requests", produces = "application/json")
    public ResponseEntity<Map<String, Integer>> pullRequests(@RequestHeader final String repositoryUrl,
                                                             @RequestHeader final String dateFrom,
                                                             @RequestHeader final String dateTo) {
        LOGGER.info("Received request to calculate the pull requests per user for repository [{}] from [{}] until [{}]", repositoryUrl, dateFrom, dateTo);
        final Map<String, Integer> pullRequestCounts = this.gitHubService.calculatePullRequestCount(repositoryUrl, dateFrom, dateTo);

        return ResponseEntity.ok(pullRequestCounts);
    }

    @GetMapping(value = "/pull-request-comments", produces = "application/json")
    public ResponseEntity<Map<String, Integer>> pullRequestComments(@RequestHeader final String repositoryUrl,
                                                                    @RequestHeader final String dateFrom,
                                                                    @RequestHeader final String dateTo) {
        LOGGER.info("Received request to calculate the pull request comments per user for repository [{}] from [{}] until [{}]", repositoryUrl, dateFrom, dateTo);
        final Map<String, Integer> commentCounts = this.gitHubService.calculatePullRequestCommentCount(repositoryUrl, dateFrom, dateTo);

        return ResponseEntity.ok(commentCounts);
    }
}
