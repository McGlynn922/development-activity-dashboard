package com.titania.demo.development.activity.dashboard.impl.counter;

import com.titania.demo.development.activity.dashboard.contract.models.github.Commit;
import com.titania.demo.development.activity.dashboard.contract.models.github.PullRequest;
import com.titania.demo.development.activity.dashboard.contract.models.github.PullRequestComment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Counter {

    public Map<String, Integer> calculateCommitCount(final List<Commit> commits) {
        return commits.stream()
                .filter(this::hasUsername)
                .collect(Collectors.groupingBy(
                        commit -> commit.user().login(),
                        Collectors.summingInt(e -> 1)
                ));
    }

    public Map<String, Integer> calculatePullRequestCount(final List<PullRequest> pullRequests) {
        return pullRequests.stream()
                .filter(this::hasUsername)
                .collect(Collectors.groupingBy(
                        pullRequest -> pullRequest.user().login(),
                        Collectors.summingInt(e -> 1)
                ));
    }

    public Map<String, Integer> calculatePullRequestCommentCount(final List<PullRequestComment> pullRequestComments) {
        return pullRequestComments.stream()
                .filter(this::hasUsername)
                .collect(Collectors.groupingBy(
                        pullRequestComment -> pullRequestComment.user().login(),
                        Collectors.summingInt(e -> 1)
                ));
    }

    private boolean hasUsername(final Commit commit) {
        return commit.user() != null && commit.user().login() != null;
    }

    private boolean hasUsername(final PullRequest pullRequest) {
        return pullRequest.user() != null && pullRequest.user().login() != null;
    }

    private boolean hasUsername(final PullRequestComment pullRequestComment) {
        return pullRequestComment.user() != null && pullRequestComment.user().login() != null;
    }
}
