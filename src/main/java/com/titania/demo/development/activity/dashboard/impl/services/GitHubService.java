package com.titania.demo.development.activity.dashboard.impl.services;

import com.titania.demo.development.activity.dashboard.contract.models.github.Commit;
import com.titania.demo.development.activity.dashboard.contract.models.github.PullRequest;
import com.titania.demo.development.activity.dashboard.contract.models.github.PullRequestComment;
import com.titania.demo.development.activity.dashboard.impl.clients.GitHubClient;
import com.titania.demo.development.activity.dashboard.impl.counter.Counter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GitHubService {

    private final GitHubClient gitHubClient;
    private final Counter counter;

    public GitHubService(final GitHubClient gitHubClient, final Counter counter) {
        this.gitHubClient = gitHubClient;
        this.counter = counter;
    }

    public Map<String, Integer> calculateCommitCount(final String repositoryUrl, final String dateFrom, final String dateTo) {
        final List<Commit> commits = gitHubClient.fetchCommits(repositoryUrl, dateFrom, dateTo);

        return this.counter.calculateCommitCount(commits);
    }

    public Map<String, Integer> calculatePullRequestCount(final String repositoryUrl, final String dateFrom, final String dateTo) {
        final List<PullRequest> pullRequests = gitHubClient.fetchPullRequests(repositoryUrl, dateFrom, dateTo);

        return this.counter.calculatePullRequestCount(pullRequests);
    }

    public Map<String, Integer> calculatePullRequestCommentCount(final String repositoryUrl, final String dateFrom, final String dateTo) {
        final List<PullRequestComment> pullRequestComments = this.gitHubClient.fetchPullRequestComments(repositoryUrl, dateFrom, dateTo);

        return this.counter.calculatePullRequestCommentCount(pullRequestComments);
    }
}
